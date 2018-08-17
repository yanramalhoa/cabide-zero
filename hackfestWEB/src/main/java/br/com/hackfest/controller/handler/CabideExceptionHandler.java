package br.com.hackfest.controller.handler;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.controller.utilitarios.FacesUtil;



@SuppressWarnings("unused")
public class CabideExceptionHandler extends ExceptionHandlerWrapper {

        private final Logger LOG = LogManager.getLogger("br.com.agro.controller.PMJPExceptionHandler");
        private final ExceptionHandler wrapper;
       
        public CabideExceptionHandler(ExceptionHandler wrapper) {
                this.wrapper = wrapper;
        }

        @Override
        public ExceptionHandler getWrapped() {
                return this.wrapper;
        }

        @Override
        public void handle() throws FacesException {
                final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();

                while (i.hasNext()) {
                        ExceptionQueuedEvent event = i.next();
                        ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
                        // obtem a exceção do contexto
                        Throwable t = context.getException();
                        final FacesContext fc = FacesContext.getCurrentInstance();
                        final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
                        final ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                        // a partir daqui vocÃª decide o que fazer com a exceção
                        try {
                                FacesUtil.obterFacesContext().validationFailed();
                                // tratando se a causa da exceção eh uma PMJPException
                                CabideException PMJPException = getBusinessException(t);
                                if (PMJPException != null) {
                                        // exibe uma mensagem de erro na propria pÃ¡gina onde o erro ocorreu
                                        if (PMJPException.getMensagem() != null
                                                        && !PMJPException.getMensagem().getCodigo().isEmpty()) {
                                                FacesUtil.registrarErro(PMJPException.getMensagem().getCodigo(), PMJPException.getParametros().toArray(new String[PMJPException.getParametros().size()]));
                                                LOG.error(new StringBuilder("ERRO DE NEGÓCIO: ").append(PMJPException.getMessage()).toString(), PMJPException);
                                        }
                                        return;
                                }
                                LOG.error(new StringBuilder("ERRO SEVERO: ").append(t.getMessage()), t);
                                //envia mensagens do erro aos administradores do sistema
                                enviarEmailParaAdministrador(t);

                                // redirecionar para a pagina de erro.
                                requestMap.put("exceptionMessage", t.getMessage());
                                nav.performNavigation("/error500.xhtml");
                                fc.renderResponse();
                        } finally {
                                // remove a exceção da fila
                                i.remove();
                        }
                }
                // identifica ao pai
                getWrapped().handle();
        }
       
        /**
         * Descobre se a causa do erro foi uma exceção de negócio, pois ela pode vir
         * encapsulada em um FacesException.
         *
         * @param e
         *              A exceção a ser analisada.
         * @return Uma exceção de negócio caso ela seja a causa do erro.
         */
        private CabideException getBusinessException(Throwable throwable) {
                CabideException retorno = null;

                if (throwable != null)
                        if (throwable instanceof CabideException)
                                retorno = (CabideException) throwable;
                        else
                                retorno = getBusinessException(throwable.getCause());
                return retorno;
        }

		public void enviarEmailParaAdministrador(Throwable throwable) {
                HttpServletRequest request = (HttpServletRequest) FacesUtil.obterRequest();
                String dataHoraErro = "Data/Hora do Erro: " + new Date();
                String ip = "IP: "+request.getRemoteAddr();
                String msgErro = "Mensagem de Erro: "+ throwable.getLocalizedMessage();
                String detalhesLog = CabideException.getPilhaErro(throwable);
                StringBuilder msg = new StringBuilder("\n").append(dataHoraErro).append("\n").append(ip).append("\n").append(msgErro).append("\n").append(detalhesLog);
                if (!ip.contains("127.0.0.1") && !ip.contains("0:0:0:0:0:0:0:1")){
                	//TODO implementar envio de email
                	LOG.debug("#### implementar envio de email");
                }
        }
}