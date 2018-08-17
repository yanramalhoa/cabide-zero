package br.com.hackfest.controller.utilitarios;


import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIColumn;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.hackfest.controller.exception.CabideException;


@SuppressWarnings({ "all" })
public class FacesUtil {

	private FacesUtil() { }
    
    private static final String CHAVE_BEAN_ACIONADOR = "beanAcionador";

    /**
     * Adiciona um atributo no escopo de request.
     *
     * @param chave
     *            a chave que identifica o atributo
     * @param valor
     *            o valor do atributo
     */
    public static void adicionarAtributoRequest(String chave, Object valor) {

            obterRequest().setAttribute(chave, valor);

    }

    /**
     * Adiciona um atributo na sessão.
     *
     * @param chave
     *            a chave que identifica o atributo
     * @param valor
     *            o valor do atributo
     */
    public static void adicionarAtributoSessao(String chave, Object valor) {

            obterSessao().setAttribute(chave, valor);

    }
   
    /**
     * Recupera o ip
     *
     * @return o ip
     */
    public static String obterIp() {
            return ((HttpServletRequest) obterFacesContext().getExternalContext().getRequest()).getRemoteAddr();
    }

    /**
     * Invalida a sessão corrente.
     */
    public static void invalidarSessao() {
//          SecurityContextHolder.clearContext();
            obterSessao().invalidate();
    }

    /**
     * Obtem um atributo do request.
     *
     * @param chave
     *            a chave do atributo.
     * @return o atributo.
     */
    public static Object obterAtributoRequest(String chave) {

            return obterRequest().getAttribute(chave);
    }

    /**
     * Obtem um atributo da sessão.
     *
     * @param chave
     *            a chave do atributo.
     * @return o atributo.
     */
    public static Object obterAtributoSessao(String chave) {

            return obterSessao().getAttribute(chave);
    }
   
    public static Object obterAtributoSessao(FacesContext fc, String chave) {
            if(fc != null){
                    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                    if(session != null)
                            return session.getAttribute(chave);
            }

            return null; //obterSessao().getAttribute(chave);
    }

    /**
     * Obtem o caminho completo de um determinado caminho relativo da aplicação.
     *
     * @param caminho
     *            o caminho
     * @return o caminho completo
     */
    public static String obterCaminhoReal(String caminho) {

            ServletContext sc = (ServletContext) FacesContext.getCurrentInstance()
                            .getExternalContext().getContext();
            return sc.getRealPath(caminho);
    }

    public static String obterCaminhoContexto() {
            ServletContext sc = (ServletContext) obterFacesContext().getExternalContext().getContext();
            return sc.getContextPath();
    }

    /**
     * Obtem um componente.
     *
     * @param idComponente
     *            o id do componente
     * @return o componente
     */
    public static UIComponent obterComponente(String idComponente) {

            return obterFacesContext().getViewRoot().findComponent(idComponente);
    }

    /**
     * Obtem o faces context do bean em execução.
     *
     * @return FacesContext.
     */
    public static FacesContext obterFacesContext() {

            return FacesContext.getCurrentInstance();
    }

    /**
     * Obtem o managed bean solicitado.
     *
     * @param nomeManagedBean
     *            o nome do managed bean
     * @return o managed bean de interesse
     */
    public static Object obterManagedBean(String nomeManagedBean) {
            return obterFacesContext().getApplication().createValueBinding(new StringBuilder().append("#{").append(nomeManagedBean).append("}").toString()).getValue(obterFacesContext());
    }
   
    /**
     * Obtem o valor de um atributo solicitado.
     *
     * @param idAtributo o identificador do atributo
     * @return
     */
    public static String obterAtributoComoString (String idAtributo){
                           
            return obterFacesContext().getExternalContext().
                            getRequestParameterMap().get(idAtributo);
    }

    /**
     * Obtem o nome do contexto.
     *
     * @return o nome do contexto.
     */
    public static String obterNomeContexto() {

            return obterFacesContext().getExternalContext().getRequestContextPath();
    }

    /**
     * Obtem o arquivo de recursos configurado no faces.
     *
     * @return arquivo de recursos.
     */
    private static ResourceBundle obterResourceBundle() {

//          String bundleName = obterFacesContext().getApplication()
//                          .getMessageBundle();
//
//          ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
            ResourceBundle bundle = obterFacesContext().getApplication().getResourceBundle(obterFacesContext(), "msg");

            return bundle;
    }

    /**
     * Retorna um HttpServletRequest contendo a requisição http atual.
     *
     * @return um HttpServletRequest contendo a requisição http atual
     */
    public static HttpServletRequest obterRequest() {

            return (HttpServletRequest) obterFacesContext().getExternalContext()
                            .getRequest();
    }

    /**
     * Obtem a sessão do usuário.
     *
     * @return a sessão.
     */
    public static HttpSession obterSessao() {
            return (HttpSession) obterFacesContext().getExternalContext().getSession(true);
    }
   
   
public static HttpServletResponse obterResponse() {
    return (HttpServletResponse) obterFacesContext().getExternalContext().getResponse();

}  

    /**
     * Obtem o conteúdo da chave no arquivo de mensagens. Caso não encontrar,
     * retorna apenas o valor da key.
     *
     * @param key
     *            a chave a ser pesquisada.
     * @return texto contido na key.
     */
    public static String obterTexto(String key) {

            String texto = null;

            try {
                    texto = obterResourceBundle().getString(key);
            } catch (MissingResourceException e) {
                    texto = key;
            }

            return texto;
    }

    /**
     * Obtem o conteúdo da chave no arquivo de mensagens parametizada. Caso não
     * encontrar, retorna apenas o valor da key.
     *
     * @param key
     *            a chave a ser pesquisada.
     * @param params
     *            parametros a serem incluidos no texto.
     *
     * @return texto da chave parametizado.
     */
    public static String obterTexto(String key, Object... params) {

            String texto = obterTexto(key);

            if (params != null) {
                    MessageFormat mf = new MessageFormat(texto);
                    texto = mf.format(params, new StringBuffer(), null).toString();
            }

            return texto;
    }
   
   
/**
     * Obtem as colunas de um HTMLDataTable passado como parametro
     *
     * @param table
     *            HTMLdataTable passado como parametro.
     * @return um List contendo UIColumns.
     */
    public static List obterColunas(HtmlDataTable table) {
            List columns = new ArrayList();

            for (int i = 0; i < table.getChildCount(); i++) {
                    UIComponent child = table.getChildren().get(i);
                    if (child instanceof UIColumn
                            && !(child.getChildren().size() == 0 || child.getChildren()
                                    .get(0) instanceof EditableValueHolder)) {
                            columns.add(child);
                    }
            }
            return columns;
    }

    /**
     * Registra mensagem de aviso no contexto.
     *
     * @param key
     *            a chave da mensagem.
     */
    public static void registrarAviso(String key) {

            String texto = obterTexto(key);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_WARN);
    }

    /**
     * Registra mensagem de aviso no contexto.
     *
     * @param key
     *            a chave da mensagem.
     * @param param
     *            o(s) parametro(s) da mensagem.
     */
    public static void registrarAviso(String key, String... param) {

            String texto = obterTexto(key, param);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_WARN);
    }

    /**
     * Retorna o nome do managed bean que executou a ação atual do sistema.
     *
     * @return o nome do managed bean que executou a ação atual do sistema
     */
    public static String obterBeanAcionador() {

            return obterAtributoRequest(CHAVE_BEAN_ACIONADOR).toString();
    }

    /**
     * Registra o nome do managed bean que executou a ação atual do sistema.
     *
     * @param evento
     *            o evento executado no sistema
     */
    public static void registrarBeanAcionador(ActionEvent evento) {

            UICommand acionador = (UICommand) evento.getSource();
            MethodBinding acao = acionador.getAction();
            String nomeManagedBeanAcionador;
            if (acao == null) {
                    nomeManagedBeanAcionador = null;
            } else {
                    String expressao = acionador.getAction().getExpressionString();
                    nomeManagedBeanAcionador = obterNomeManagedBean(expressao);
                    if (expressao.equals(nomeManagedBeanAcionador)) {
                            nomeManagedBeanAcionador = (String) acionador.getAttributes()
                                            .get("nomeBean");
                    }
            }
            adicionarAtributoRequest(CHAVE_BEAN_ACIONADOR, nomeManagedBeanAcionador);
    }

    /**
     * Obtem o nome do managed bean a partir de uma determinada JSF-EL. Ex:
     * obtem manterReceitaBean a partir de #{manterReceitaBean.carregar}
     *
     * @param expressao
     *            a JSF-EL a ser analisada
     * @return o nome do managed bean
     */
    public static String obterNomeManagedBean(String expressao) {

            String patternStr = "#\\{(.*)\\.";

            // Compile and use regular expression
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(expressao);
            boolean matchFound = matcher.find();

            if (matchFound) {
                    return matcher.group(1);
            }
            return expressao;
    }

    /**
     * Registra o erro de negócio no contexto.
     *
     * @param PMJPException
     *            a exceção a ser tratada.
     */
    public static void registrarErroFatal(CabideException PMJPException) {

            registrarErro(PMJPException, FacesMessage.SEVERITY_FATAL);
    }

    /**
     * Registra o erro de negócio no contexto.
     *
     * @param PMJPException
     *            a exceção a ser tratada.
     */
    public static void registrarErro(CabideException PMJPException) {

            registrarErro(PMJPException, FacesMessage.SEVERITY_ERROR);
    }

    /**
     * Registra o erro de negócio no contexto.
     *
     * @param PMJPException
     *            a exceção a ser tratada
     * @param severidade
     *            o nivel de severidade
     */
    private static void registrarErro(CabideException PMJPException, Severity severidade) {

            String chave = PMJPException.getMensagem().getCodigo();

            List<String> paramsList = PMJPException.getParametros();
            String[] params = new String[paramsList.size()];

            int i = 0;
            for (String param : paramsList) {
                    params[i] = param;
                    i = i + 1;
            }
            if (severidade.equals(FacesMessage.SEVERITY_ERROR)) {
                    registrarErro(chave, params);
            } else {
                    registrarErroFatal(chave, params);
            }
    }

    /**
     * Registra mensagem de erro no contexto.
     *
     * @param key
     *            a chave da mensagem.
     */
    public static void registrarErro(String key) {

            String texto = obterTexto(key);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR);
    }

    /**
     * Registra mensagem de erro no contexto.
     *
     * @param key
     *            a chave da mensagem.
     * @param param
     *            o(s) parametro(s) da mensagem.
     */
    public static void registrarErro(String key, String... param) {

            String texto = obterTexto(key, param);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR);
    }

    /**
     * Registra mensagem de erro fatal no contexto.
     *
     * @param key
     *            a chave da mensagem.
     * @param param
     *            o(s) parametro(s) da mensagem.
     */
    public static void registrarErroFatal(String key, String... param) {

            String texto = obterTexto(key, param);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_FATAL);
    }

    /**
     * Registra a mensagem no contexto pelo tipo de severidade.
     *
     * @param texto
     *            da mensagem.
     * @param severidade
     *            da mensagem.
     */
    public static void registrarFacesMessage(String texto, FacesMessage.Severity severidade) {
            FacesMessage mensagem = new FacesMessage();

            mensagem.setSummary(texto);
            mensagem.setSeverity(severidade);

            obterFacesContext().addMessage(null, mensagem);
    }

    /**
     * Registra a mensagem no contexto pelo tipo de severidade e pelo ID do componente.
     *
     * @param texto
     *            da mensagem.
     * @param severidade
     *            da mensagem.
     * @param idComponente
     *            da mensagem.
     */
    public static void registrarFacesMessage(String texto, FacesMessage.Severity severidade, String idComponente) {
            FacesMessage mensagem = new FacesMessage();

            mensagem.setSummary(texto);
            mensagem.setSeverity(severidade);

            obterFacesContext().addMessage(idComponente, mensagem);
    }

    /**
     * Registra mensagem no contexto.
     *
     * @param key
     *            a chave da mensagem.
     */
    public static void registrarMensagem(String key) {

            String texto = obterTexto(key);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_INFO);
    }

    public static void registrarMensagemErro(String key) {

        String texto = obterTexto(key);
        registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR);
    }

    public static void registrarMensagemErro(String key, String idComponente) {

        String texto = obterTexto(key);
        registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR, idComponente);
    }

    /**
     * Registra mensagem no contexto por componente.
     *
     * @param key
     *            a chave da mensagem.
     * @param idComponente
     *            a chave da mensagem.
     */
    public static void registrarMensagem(String key, String idComponente) {

            String texto = obterTexto(key);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_INFO, idComponente);
    }

    /**
     * Registra mensagem no contexto.
     *
     * @param key
     *            a chave da mensagem.
     * @param param
     *            o(s) parametro(s) da mensagem.
     */
    public static void registrarMensagem(String key, String... param) {

            String texto = obterTexto(key, param);
            registrarFacesMessage(texto, FacesMessage.SEVERITY_INFO);
    }

    /**
     * Adiciona um atributo na sessão.
     *
     * @param chave
     *            a chave que identifica o atributo
     */
    public static void removerAtributoSessao(String chave) {

            obterSessao().removeAttribute(chave);

    }
   
   
    /**
     * Obter valores de parametros passados pela URL
     * Exemplo: http://localhost:8080/xxxxx.jsf?param1=valorParam2&param2=valorParam2
     * @param nomeParametro
     * @return String
     */
    public static String obterParametro(String nomeParametro){
            String passedParameter = (String) obterFacesContext().getExternalContext()
                            .getRequestParameterMap().get(nomeParametro);
            if (passedParameter!=null && !passedParameter.isEmpty()){
                    return passedParameter;
            }
            return null;
    }
    
    /**
     * Obtem o request context.
     * @return RequestContext.
     */
	public static RequestContext getRequestContext(){
		RequestContext requestContext = RequestContext.getCurrentInstance();
		return requestContext;
	}
	
    /**
     * Executa JavaScript através do request context.
     *
     * @param String
     *            script.
     */
	public static void executeRequestContext(String script){
		getRequestContext().execute(script);
	}
	
    /**
     * Executa UPDATE no componente através do request context.
     *
     * @param String
     *            idComponente.
     */
	public static void updateRequestContext(String idComponente){
		getRequestContext().update(idComponente);
	}

	/**
	 * Redirecionar Página
	 */
	public static void redirectTo(String page) {
		try {
			obterResponse().sendRedirect(page.concat(".jsf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}