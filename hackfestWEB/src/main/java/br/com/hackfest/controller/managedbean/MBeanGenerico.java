package br.com.hackfest.controller.managedbean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.naming.InitialContext;
import javax.persistence.Id;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.hackfest.controller.annotation.ListDetail;
import br.com.hackfest.controller.annotation.SortId;
import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.controller.utilitarios.FacesUtil;
import br.com.hackfest.model.entities.EntidadeGenerica;
import br.com.hackfest.model.interfaces.business.GenericoBusiness;
import br.com.hackfest.model.interfaces.repository.GenericoRepository;
import br.com.hackfest.model.util.StringUtil;
import br.com.hackfest.pojo.ChartPojo;
import br.com.hackfest.view.Erro;
import br.com.hackfest.view.Mensagem;
import br.com.hackfest.view.datamodel.LazyDataModelPaginado;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class MBeanGenerico<T> implements Serializable {

	private static final long serialVersionUID = -7323724020882911197L;
	//@SuppressWarnings("unused")
	private final Logger log = LogManager.getLogger(getClass());
	
	protected LazyDataModel<T> dataModel;
	protected Class<T> entidade;
	private T objeto;
	private T objetoAnterior;
	private T objetoSelecionado;
	
	@EJB
	private GenericoRepository<T> genericoRepository;
	
	@ManagedProperty("#{administradorBean}")
	private AdministradorBean administradorBean;
	
	protected List<T> objetoListMemorySave;
	protected List<T> objetoListMemorySaveOld;
	protected List<T> objetoListMemoryDelete;
	protected List<T> objetosSelecionados;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean entidadeEspecializacao;
	private List<MBeanGenerico> beansGenericos;
	private Number totalLinhas;
	private Map<String, Object> parametros;
	private boolean manutencaoSemLimpar;
	private boolean salvarSemPermissao;
	private boolean verMensagemDeManutencao;
	private boolean botaoFecharRendered; 
	private boolean dataTableRendered;

	private String nomeDialog;

	abstract public GenericoBusiness<T> getGenericBusiness();

	public MBeanGenerico() {
		this.setDataTableRendered(false);
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
		this.getEntidade();
		this.objetoListMemorySave = new ArrayList<T>();
		this.objetoListMemorySaveOld = new ArrayList<T>();
		this.objetoListMemoryDelete = new ArrayList<T>();
		this.beansGenericos = new ArrayList<MBeanGenerico>();
		this.setVerMensagemDeManutencao(true);
		this.setManutencaoSemLimpar(false);
		this.setSalvarSemPermissao(false);
		this.setEntidadeEspecializacao(false);

		try {
			objeto = entidade.newInstance();
			setObjetoAnterior(entidade.newInstance());
			setObjetoSelecionado(entidade.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void initMaster() {
		//
	}
	
	public void addBeanDetalhe(MBeanGenerico beanGenerico){
		if(!this.beansGenericos.contains(beanGenerico)){
			this.beansGenericos.add(beanGenerico);
		}
	}

	/**
	 * Operação de salvar/alterar passa a ser realizada num mesmo ponto.<br>
	 * Diante do resultado da operação o usuário é informado sobre: <pre>
	 * 	- Registro salvo com sucesso. (ou)
	 * 	- Registro atualizado com sucesso.</pre>
	 * 
	 * @author Igo Coutinho
	 * @throws EmailException 
	 * @since 2014-08-12
	 * @throws CabideException
	 * */
	public void salvar() throws CabideException, EmailException {
		T newObject = null;
		String id = this.getId(this.entidade, this.objeto);
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();		
		try {
			//Setar Usuário
			((EntidadeGenerica)this.objeto).setUsuarioAUD(this.getAdministradorBean().getUsuario());
			((EntidadeGenerica)this.objeto).setDataAUD(new Date(System.currentTimeMillis()));
			((EntidadeGenerica)this.objeto).setAtivo(true);
//			((EntidadeGenerica)this.objeto).setUsuarioSessao(this.getAdministradorBean().getUsuario());
			//se a operação for 'alteração'
			if (id != null && !id.equals("")){
				//se o objeto sofreu alguma alteração - salvar
				//if(!this.object.equals(this.objectOld)){
					newObject = this.getGenericBusiness().salvar(this.objeto);
				//}else{
					//não havendo modificação no objeto 
					//newObject = this.object;
				//}
			}else{//se a operação for 'cadastro'
				newObject = this.getGenericBusiness().salvar(this.objeto);
			}
			if(!isManutencaoSemLimpar()){
				this.limpar();
			}
			this.objetoSelecionado = newObject;
			for (MBeanGenerico genericBean : beansGenericos) {
				genericBean.setManutencaoSemLimpar(this.isManutencaoSemLimpar());
				genericBean.setObjetoDentroDaListaDetalhe(this.objetoSelecionado);
				genericBean.setVerMensagemDeManutencao(false);
				if(genericBean.isEntidadeEspecializacao()){
					genericBean.setObjetoDentroDaEntidadeEspecializacao(this.objetoSelecionado);
					genericBean.salvar();
				}else{
					genericBean.salvarListMemoryObject();
				}
			}
			if(this.nomeDialog!=null && !this.nomeDialog.equals("")){
				FacesUtil.executeRequestContext("PF('"+ this.nomeDialog +"').hide();");
			}
		} catch (CabideException e) {
			e.printStackTrace();
			throw new CabideException(Erro.ERRO_SALVAR, e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new CabideException(Erro.INSTANCIACAO, e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new CabideException(Erro.ACESSO_ILEGAL, e);
		} finally {
			this.registrarMensagem(id, newObject);
		}
	}
	
	public List<T> getObjetoListMemoryDelete() {
		return objetoListMemoryDelete;
	}

	public void setObjetoListMemoryDelete(List<T> objetoListMemoryDelete) {
		this.objetoListMemoryDelete = objetoListMemoryDelete;
	}

	public void registrarMensagem(String id,T newObject){
		if (this.verMensagemDeManutencao){
			if (newObject != null) {
				if (id != null && !id.equals("") && !id.equals("null")) {
					FacesUtil.registrarMensagem(Mensagem.ATUALIZAR_SUCESSO
							.getCodigo());
				} else {
					FacesUtil.registrarMensagem(Mensagem.SALVAR_SUCESSO
							.getCodigo());
				}
			}
		}
	}
	
	public void salvarObjetosSelecionados() throws CabideException, EmailException{
		for (T t : this.objetosSelecionados) {
			this.setObjeto(t);
			this.setVerMensagemDeManutencao(false);
			this.salvar();
			this.setVerMensagemDeManutencao(true);
		}
	}
	
	public void salvarListMemoryObject() throws CabideException, EmailException {
		this.excluirListMemoryObject();
		List<T> list = new ArrayList<T>();
		//se houver modificação no objeto, montar list para salvar
		if(!this.objetoListMemorySave.equals(this.objetoListMemorySaveOld)){
			list = this.objetoListMemorySave;
		}
		if(!isManutencaoSemLimpar()){
			this.objetoListMemorySave = new ArrayList<T>();
			this.objetoListMemorySaveOld = new ArrayList<T>();
		}
		for (T element : list) {
			this.objeto = element;
			this.salvar();
		}
	}
	
	public void addObjetoList() throws CabideException,
			InstantiationException, IllegalAccessException,
			NoSuchFieldException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();		
		
		if (this.objeto != null) {
			Field privateField;
			Field privateFieldList;
			try {
				int sortId = 1;
				int result;
				// Pegar o objeto atual para adicionar o 'sortId'
				privateField = this.objeto.getClass().getDeclaredField(getNameObjectSortId(this.objeto.getClass()));
				privateField.setAccessible(true);
				if (privateField.get(this.entidade.cast(this.objeto)) != null) {
					sortId = privateField.getInt(this.objeto);
					if(sortId==0){
						/*
						 * Varrer a Lista para saber qual maior SortId atual para
						 * adicionar o próximo. Toda essa condição é para adaptação ao
						 * DataTable - propriedade 'rowKey'
						 */
						for (T element : this.objetoListMemorySave) {
							privateFieldList = element.getClass().getDeclaredField(getNameObjectSortId(element.getClass()));
							privateFieldList.setAccessible(true);
							if (privateFieldList.get(element) != null) {
								result = (int) privateFieldList.get(element);
								if (result > sortId) {
									sortId = result;
								}
							}
						}
						sortId++;
						// Adicionar o 'sortId'
						privateField.setInt(this.objeto, sortId);
						// Adicionar ao ListMemory
						this.objetoListMemorySave.add(this.objeto);
						this.limpar();
					}else{
						//T objectFromList;
						for (T element : this.objetoListMemorySave) {
							privateFieldList = element.getClass().getDeclaredField(getNameObjectSortId(element.getClass()));
							privateFieldList.setAccessible(true);
							if (privateFieldList.get(element) != null) {
								result = (int) privateFieldList.get(element);
								if(result==sortId){
									if(this.objetoListMemorySave.contains(element)){
										this.objetoListMemorySave.set(this.objetoListMemorySave.indexOf(element), this.objeto);
									}
								}
							}
						}
					}
				}
				if(this.nomeDialog!=null && !this.nomeDialog.equals("")){
					FacesUtil.executeRequestContext("PF('"+ this.nomeDialog +"').hide();");
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CabideException(Erro.ERRO_SALVAR, e);
			}
		}
	}

	public void excluir() throws CabideException {
		try {
			//Reinicia Contador de Sessão
			this.reiniciarContadorSessao();
			//Setar Usuário
			((EntidadeGenerica)this.objeto).setUsuarioAUD(this.getAdministradorBean().getUsuario());
			((EntidadeGenerica)this.objeto).setDataAUD(new Date(System.currentTimeMillis()));
			this.getGenericBusiness().excluir(this.objetoSelecionado);
			if(!isManutencaoSemLimpar()){
				this.limpar();
			}
		} catch (CabideException e) {
			e.printStackTrace();
			throw new CabideException(Erro.ERRO_EXCLUIR, e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new CabideException(Erro.INSTANCIACAO, e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new CabideException(Erro.ACESSO_ILEGAL, e);
		} finally {
			if (this.verMensagemDeManutencao){
				FacesUtil.registrarMensagem(Mensagem.EXLCUIR_SUCESSO.getCodigo());
			}
		}
	}

	private void excluirListMemoryObject() throws CabideException {
		for (T element : this.objetoListMemoryDelete) {
			this.objeto = element;
			this.excluir();
			if(objetoListMemorySaveOld.contains(element)){
				objetoListMemorySaveOld.remove(element);
			}
		}
		this.objetoListMemoryDelete = new ArrayList<T>();
	}

	public void deleteObjectListInMemory() throws CabideException,
			InstantiationException, IllegalAccessException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		if (this.objetoSelecionado != null) {
			String id;
			try {
				id = this.getId(this.entidade, this.objetoSelecionado);
				if (id != null && !id.equals("")) {
					this.objetoListMemoryDelete.add(this.objetoSelecionado);
				}
				this.objetoListMemorySave.remove(this.objetoListMemorySave.indexOf(this.objetoSelecionado)); //remove(i);
				this.objetoSelecionado = null;
				this.limpar();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CabideException(Erro.ERRO_EXCLUIR, e);
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CabideException(Erro.ERRO_EXCLUIR, e);
			}
		}
	}
	
	public void deleteObjectListInMemory(T t) throws NoSuchFieldException,
	SecurityException, InstantiationException, IllegalAccessException,
	CabideException {
			this.selecionarObjeto(t);
			this.deleteObjectListInMemory();
			}
	
	public Class<T> getEntidade() {
		if (entidade == null)
			this.entidade = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		return entidade;
	}

	public void setEntidade(Class<T> entidade) {
		this.entidade = entidade;
	}

	public String completaZeroAEsquerda(String palavra, int tamanho) {
        return StringUtil.completarZerosEsquerda(palavra, tamanho);
	}

	public String completaZeroAEsquerda(int numero, int tamanho) {
        return StringUtil.completarZerosEsquerda(numero, tamanho);
	}

	public String decimalFormat(String pattern, BigDecimal numero){
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(numero);
	}

	public void pesquisar() throws CabideException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		this.setDataTableRendered(true);
		((EntidadeGenerica)getObjeto()).setAtivo(true);
		//totalLinhas = this.lookupGenericoRepository().contaResultados(getObjeto());
		totalLinhas = this.getGenericBusiness().contaResultados(getObjeto());
		if(totalLinhas != null && Integer.valueOf(totalLinhas.toString()).compareTo(0) <= 0){
			if(this.verMensagemDeManutencao){
				FacesUtil.registrarMensagem(Mensagem.NENHUM_REGISTRO_ENCONTRADO.getCodigo());
			}
			return;
		}
		dataModel = new LazyDataModelPaginado<T>(getObjeto(), Integer.valueOf(totalLinhas.toString()), getGenericBusiness());
	}

	public void pesquisarObjetoLista() throws CabideException {
		this.pesquisar();
		if (totalLinhas.intValue() <= 0) {
			return;
		}
		this.objetoListMemorySave = null;
		this.objetoListMemorySave = dataModel.load(0, totalLinhas.intValue(), this.getNameObjectId(this.objeto.getClass()), org.primefaces.model.SortOrder.ASCENDING, this.getParametros());
	}

	public void pesquisarObjetoLista(String sortField, org.primefaces.model.SortOrder typeSorter) throws CabideException {
		this.pesquisar();
		if (totalLinhas.intValue() <= 0) {
			return;
		}
		this.objetoListMemorySave = null;
		this.objetoListMemorySave = dataModel.load(0, totalLinhas.intValue(), sortField, typeSorter, this.getParametros());
	}

	public void listaDetalhe() throws CabideException{
		//lookupGenericoRespository().setEntidade(entidade);
		this.pesquisarObjetoLista();
		
		Field privateField;
		try {
			int tam = this.objetoListMemorySave.size();
			for(int i = 0; i < tam; i++){
				privateField = this.objetoListMemorySave.get(i).getClass().getDeclaredField(getNameObjectSortId(this.objetoListMemorySave.get(i).getClass()));
				privateField.setAccessible(true);
				privateField.setInt(this.objetoListMemorySave.get(i), i+1);
			}
			
			List<T> listMemory = new ArrayList<T>();
			for (T t : objetoListMemorySave) {
				listMemory.add((T) t);
			}
			this.objetoListMemorySaveOld = listMemory;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public GenericoRepository lookupGenericoRepository() throws CabideException {
		try {
            if(this.genericoRepository == null){
                StringBuilder resource = new StringBuilder("java:comp/env/protocoloEAR/protocoloEJB/")
                    .append(this.getEntidade().getSimpleName())
                    .append("Dao!br.gov.pb.joaopessoa.protocolo.model.interfaces.repository.")
                    .append(this.getEntidade().getSimpleName()).append("Repository");
                this.genericoRepository = (GenericoRepository<T>) new InitialContext().lookup(resource.toString());
            }
            this.genericoRepository.setEntidade((Class<T>) this.getEntidade().getClass());
            return this.genericoRepository;
	     } catch (Exception e) {
	    	log.error(e.getMessage());
            throw new CabideException(Erro.INESPERADO, e);
	     }
	}

	public LazyDataModel<T> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<T> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isCadastroRendered() {
		return cadastroRendered;
	}

	public void setCadastroRendered(boolean cadastroRendered) {
		this.cadastroRendered = cadastroRendered;
	}

	public boolean isPesquisaRendered() {
		return pesquisaRendered;
	}

	public void setPesquisaRendered(boolean pesquisaRendered) {
		this.pesquisaRendered = pesquisaRendered;
	}

	public void alterStatusRendered() throws InstantiationException,
	IllegalAccessException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		if (cadastroRendered) {
			setCadastroRendered(false);
			setPesquisaRendered(true);
		} else if (pesquisaRendered) {
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
		
		this.objetoListMemorySave = new ArrayList<T>();
		this.objetoListMemorySaveOld = new ArrayList<T>();
		this.objetoListMemoryDelete = new ArrayList<T>();
		for (MBeanGenerico genericBean : beansGenericos) {
			genericBean.objetoListMemorySave = new ArrayList<T>();
			genericBean.objetoListMemorySaveOld = new ArrayList<T>();
			genericBean.objetoListMemoryDelete = new ArrayList<T>();
		}
		
		if (isPesquisaRendered()) {
			this.limpar();
		}

	}
	
	public void limpar() throws InstantiationException, IllegalAccessException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		objetoSelecionado = null;
		this.entidade = null;
		this.getEntidade();
		objeto = entidade.newInstance();
		setObjetoAnterior(entidade.newInstance());
//		dataTableRendered = false;
		this.dataModel = null;
	}
	
	public void acaoNovo() throws InstantiationException, IllegalAccessException{
		
		this.limpar();
		for (MBeanGenerico genericoBean : beansGenericos) {
			genericoBean.limpar();
			genericoBean.objetoListMemorySave = new ArrayList<T>();
			genericoBean.objetoListMemorySaveOld = new ArrayList<T>();
			genericoBean.objetoListMemoryDelete = new ArrayList<T>();
		}
	}

	public void acaoCadastrar() throws InstantiationException,
			IllegalAccessException {
		
		this.limpar();
		alterStatusRendered();
	}

	public void acaoAlterar() throws NoSuchFieldException, SecurityException,
			InstantiationException, IllegalAccessException, CabideException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		
		
		this.setObjeto(this.objetoSelecionado);
		
		List<T> listObjectSelected = new ArrayList<T>();
		listObjectSelected.add(objetoSelecionado);
		this.setObjetoAnterior(listObjectSelected.iterator().next());
		
		if (this.getObjeto() != null) {
			setCadastroRendered(true);
			setPesquisaRendered(false);
			setDataTableRendered(false);
			this.listaDetalheMBeanGenerico();
		} else {
			this.limpar();
		}
	}
	
	public void acaoAlterar(T obj) throws NoSuchFieldException, SecurityException, InstantiationException,
			IllegalAccessException, CabideException {
		// Reinicia Contador de Sessão
		this.reiniciarContadorSessao();


		this.setObjeto(obj);

		List<T> listObjectSelected = new ArrayList<T>();
		listObjectSelected.add(objetoSelecionado);
		this.setObjetoAnterior(listObjectSelected.iterator().next());

		if (this.getObjeto() != null) {
			setCadastroRendered(true);
			setPesquisaRendered(false);
			setDataTableRendered(false);
			this.listaDetalheMBeanGenerico();
		} else {
			this.limpar();
		}
	}

	public T getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(T objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public T getObjeto() {
		return objeto;
	}

	public void setObjeto(T objeto) {
		this.objeto = objeto;
	}
	
	public void selecionarObjetoEvent(SelectEvent event)
			throws NoSuchFieldException, SecurityException,
			InstantiationException, IllegalAccessException, CabideException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		this.selecionarObjeto((T) event.getObject());
		if(this.nomeDialog!=null && !this.nomeDialog.equals("")){
			FacesUtil.executeRequestContext("PF('"+ this.nomeDialog +"').hide();");
		}
	}
	
	public void selecionarObjetoAlterar(T t) throws NoSuchFieldException,
			SecurityException, InstantiationException, IllegalAccessException,
			CabideException {
		this.selecionarObjeto(t);
		this.acaoAlterar();
	}

	public void selecionarObjeto(T objeto) throws NoSuchFieldException,
			SecurityException, InstantiationException, IllegalAccessException,
			CabideException {
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		objetoSelecionado = objeto;
	}

	protected String getId(Class<?> clazz, Object obj) throws CabideException {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if ((field.getAnnotation(Id.class)) != null) {
					Field privateField = clazz.getDeclaredField(field.getName());
					privateField.setAccessible(true);
					if (privateField.get(clazz.cast(obj)) != null)
						return (String) field.getType().cast(privateField.get(clazz.cast(obj))).toString();
					else
						return null;
				}
			}

		} catch (SecurityException | IllegalAccessException e) {
			throw new CabideException(Erro.ACESSO_ILEGAL, e);
		} catch (NoSuchFieldException nsf) {
			throw new CabideException(Erro.PROPRIEDADE_INDEFINIDA, nsf);
		} catch (IllegalArgumentException ia) {
			throw new CabideException(Erro.ARGUMENTO_ILEGAL, ia);
		}

		return null;
	}

	private String getNameObjectId(Class<?> clazz) throws CabideException {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if ((field.getAnnotation(Id.class)) != null) {
					return field.getName();
				}
			}
		} catch (IllegalArgumentException ia) {
			throw new CabideException(Erro.ARGUMENTO_ILEGAL, ia);
		}

		return null;
	}
	
	private String getNameObjectSortId(Class<?> clazz) throws CabideException {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if ((field.getAnnotation(SortId.class)) != null) {
					return field.getName();
				}
			}
		} catch (IllegalArgumentException ia) {
			throw new CabideException(Erro.ARGUMENTO_ILEGAL, ia);
		}

		return null;
	}

	private void setObjetoDentroDaEntidadeEspecializacao(Object value) throws CabideException {
		try {
			for (Field field : this.objeto.getClass().getDeclaredFields()) {
				if ((field.getAnnotation(Id.class)) != null) {
					Field privateField = this.objeto.getClass().getDeclaredField(field.getName());
					privateField.setAccessible(true);
					for (Field fieldDetail : value.getClass().getDeclaredFields()) {
						if ((fieldDetail.getAnnotation(Id.class)) != null) {
							Field privateFieldDetail = value.getClass().getDeclaredField(fieldDetail.getName());
							privateFieldDetail.setAccessible(true);
							privateField.set(this.objeto, privateFieldDetail.get(value));
							//privateFieldDetail.set(value, privateField.get(this.objeto));
						}
					}
				}
			}
		} catch (SecurityException | IllegalAccessException e) {
			throw new CabideException(Erro.ACESSO_ILEGAL, e);
		} catch (NoSuchFieldException nsf) {
			throw new CabideException(Erro.PROPRIEDADE_INDEFINIDA, nsf);
		} catch (IllegalArgumentException ia) {
			throw new CabideException(Erro.ARGUMENTO_ILEGAL, ia);
		}
	}
	
	private void setObjetoDentroDaListaDetalhe(Object value) throws CabideException {
		try {
			for (T element : this.objetoListMemorySave) {
				for (Field field : element.getClass().getDeclaredFields()) {
					if ((field.getAnnotation(ListDetail.class)) != null) {
						Field privateField = element.getClass().getDeclaredField(field.getName());
						privateField.setAccessible(true);
						privateField.set(element, value);
					}
				}
			}
		} catch (SecurityException | IllegalAccessException e) {
			throw new CabideException(Erro.ACESSO_ILEGAL, e);
		} catch (NoSuchFieldException nsf) {
			throw new CabideException(Erro.PROPRIEDADE_INDEFINIDA, nsf);
		} catch (IllegalArgumentException ia) {
			throw new CabideException(Erro.ARGUMENTO_ILEGAL, ia);
		}
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void addParametros(String string, Object objeto) {
		this.parametros.put(string, objeto);
	}

	public void listaDetalheMBeanGenerico() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, CabideException, InstantiationException{
		Field privateField;
		for (MBeanGenerico genericBean : beansGenericos) {
			//genericBean.addParametros(this.getNameObjectListDetail(genericBean.entidade), this.objeto);
			genericBean.setVerMensagemDeManutencao(false);
			if(genericBean.isEntidadeEspecializacao()){
				for (Field field : genericBean.objeto.getClass().getDeclaredFields()) {
					if ((field.getAnnotation(Id.class)) != null) {
						genericBean.limpar();
						genericBean.setObjetoDentroDaEntidadeEspecializacao(this.objeto);
						genericBean.setObjeto(genericBean.getGenericBusiness().buscarPorEntidade(genericBean.objeto));
						if(genericBean.objeto==null){
							genericBean.limpar();
						}
					}
				}
			} else {
				for (Field field : genericBean.objeto.getClass().getDeclaredFields()) {
					if ((field.getAnnotation(ListDetail.class)) != null) {
						privateField = genericBean.objeto.getClass().getDeclaredField(field.getName());
						privateField.setAccessible(true);
						privateField.set(genericBean.objeto, this.objeto);
					}
				}
				genericBean.listaDetalhe();
			}
		}
	}

	public T getObjetoAnterior() {
		return objetoAnterior;
	}

	public void setObjetoAnterior(T objetoAnterior) {
		this.objetoAnterior = objetoAnterior;
	}

	public boolean isManutencaoSemLimpar() {
		return manutencaoSemLimpar;
	}

	public void setManutencaoSemLimpar(boolean manutencaoSemLimpar) {
		this.manutencaoSemLimpar = manutencaoSemLimpar;
	}

	public boolean isSalvarSemPermissao() {
		return salvarSemPermissao;
	}

	public void setSalvarSemPermissao(boolean salvarSemPermissao) {
		this.salvarSemPermissao = salvarSemPermissao;
	}

	public boolean isVerMensagemDeManutencao() {
		return verMensagemDeManutencao;
	}

	public void setVerMensagemDeManutencao(boolean verMensagemDeManutencao) {
		this.verMensagemDeManutencao = verMensagemDeManutencao;
	}
	
	public List<T> getObjetoListMemorySave() {
		return objetoListMemorySave;
	}

	public void setObjetoListMemorySave(List<T> objetoListMemorySave) {
		this.objetoListMemorySave = objetoListMemorySave;
	}

	public boolean isBotaoFecharRendered() {
		this.botaoFecharRendered = false;
		if (this.nomeDialog != null && !this.nomeDialog.equals("")) {
			this.botaoFecharRendered = true;
		}
		return botaoFecharRendered;
	}

	public void setBotaoFecharRendered(boolean botaoFecharRendered) {
		this.botaoFecharRendered = botaoFecharRendered;
	}

	public String getNomeDialog() {
		return nomeDialog;
	}

	public void setNomeDialog(String nomeDialog) {
		this.nomeDialog = nomeDialog;
	}
	
	public void nomeDialog(String nomeDialog) {
		this.nomeDialog = nomeDialog;
	}
	
	public void fecharDialog(){
		//Reinicia Contador de Sessão
		this.reiniciarContadorSessao();
		if(this.nomeDialog!=null && !this.nomeDialog.equals("")){
			FacesUtil.executeRequestContext("PF('"+ this.nomeDialog +"').hide();");
		}
	}

	public List<T> getObjetosSelecionados() {
		return objetosSelecionados;
	}

	public void setObjetosSelecionados(List<T> objetosSelecionados) {
		this.objetosSelecionados = objetosSelecionados;
	}

	public AdministradorBean getAdministradorBean() {
		return administradorBean;
	}

	public void setAdministradorBean(AdministradorBean administradorBean) {
		this.administradorBean = administradorBean;
	}
	
//	public boolean verificarPermissao(Object objeto, ActionAuthentication actionAuthentication){
//		if(!Permission.verifyPermission(objeto.getClass().getName(), getAdministradorBean().getListUsuarioPermissao(), actionAuthentication, TipoUsuario.ADMINISTRADOR)){
//			if(actionAuthentication.equals(ActionAuthentication.SALVAR)){
//				FacesUtil.registrarErro(Erro.PERMISSAO_SALVAR.getCodigo());
//			}else if(actionAuthentication.equals(ActionAuthentication.ALTERAR)){
//				FacesUtil.registrarErro(Erro.PERMISSAO_ALTERAR.getCodigo());
//			}else if(actionAuthentication.equals(ActionAuthentication.DELETAR)){
//				FacesUtil.registrarErro(Erro.PERMISSAO_EXCLUIR.getCodigo());
//			}else if(actionAuthentication.equals(ActionAuthentication.PESQUISAR)){
//				FacesUtil.registrarErro(Erro.PERMISSAO_PESQUISAR.getCodigo());
//			}
//			return false;
//		}
//		return true;
//	}

	public boolean isEntidadeEspecializacao() {
		return entidadeEspecializacao;
	}

	public void setEntidadeEspecializacao(boolean entidadeEspecializacao) {
		this.entidadeEspecializacao = entidadeEspecializacao;
	}
	
	public void reiniciarContadorSessao(){
		//Reinicia Contador de Sessão
		FacesUtil.executeRequestContext("startCountdown();");
	}

	public void acaoPesquisar() throws InstantiationException, IllegalAccessException{
		this.setDataTableRendered(false);
		this.alterStatusRendered();
	}
	
	public void dialogUpdate(T obj, String namePanelGroup) throws CabideException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException{
		if(obj!=null){
			String id = this.getId(obj.getClass(), obj);
			//se a operação for 'alteração'
			if (id != null && !id.equals("")){
				if(this.isPesquisaRendered()){
					this.objetoSelecionado = obj;
					this.acaoAlterar();
					FacesUtil.updateRequestContext(namePanelGroup);
				}else{
					this.objetoSelecionado = obj;
				}
			}else{
				if(this.isCadastroRendered()){
					this.acaoPesquisar();
					FacesUtil.updateRequestContext(namePanelGroup);
				}
			}
		}
		
	}

	public boolean isDataTableRendered() {
		return dataTableRendered;
	}

	public void setDataTableRendered(boolean dataTableRendered) {
		this.dataTableRendered = dataTableRendered;
	}
	
	
}