package br.com.hackfest.view.datamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.interfaces.business.GenericoBusiness;
import br.com.hackfest.model.interfaces.repository.GenericoRepository;
import br.com.hackfest.view.Erro;

public class LazyDataModelPaginado<T> extends LazyDataModel<T> {

	private static final long serialVersionUID = -3574501235777816248L;
	private final Logger log = LogManager.getLogger(getClass());

	protected volatile T entidade;
	protected int numeroLinhas;
	protected GenericoRepository<T> genericoRepository;
	protected GenericoBusiness<T> genericoBusiness;

	public LazyDataModelPaginado() {
		super();
	}

	public LazyDataModelPaginado(T entidade, Integer numeroLinhas, GenericoBusiness<T> genericoBusiness) {
		this();
		this.entidade = entidade;
		this.numeroLinhas = numeroLinhas;
		this.genericoBusiness = genericoBusiness;
	}



	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> listaPaginado = new ArrayList<>();
		try {
			String tipoOrdenacao = sortOrder.equals(SortOrder.ASCENDING) ? "ASC" : sortOrder.equals(SortOrder.DESCENDING) ? "DESC" : null;
			listaPaginado = this.genericoBusiness.loadPaginated(this.entidade, first, pageSize, sortField, tipoOrdenacao, filters, null);

			if(listaPaginado == null || listaPaginado.size() == 0) setRowCount(0);
			else setRowCount(this.numeroLinhas);

			return listaPaginado;

		} catch (Exception e) {
			log.error(e.getMessage());
			return listaPaginado;
		}
	}

	
	@Override
	public Object getRowKey(T object) {
	    try {
			return object != null ? Long.valueOf(this.getId(entidade.getClass(), object)) : null;
		} catch (NumberFormatException | CabideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getRowData(String rowKey) {
	    List<T> objects = (List<T>) getWrappedData();

	    for (T object : objects) {
	        try {
				if (this.getId(entidade.getClass(), object).equals(rowKey)) {
				    return object;
				}
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    return null;
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

	
	
//	@SuppressWarnings("unchecked")
//	protected GenericoRepository<T> lookupGenericoRepository() throws PMJPException {
//		try {
//             if(this.genericoRepository == null){
//                 StringBuilder resource = new StringBuilder("java:global/rh-ear/rh-ejb/")
//                 .append(this.entidade.getClass().getSimpleName())
//                 .append("Dao!br.gov.pb.joaopessoa.rh.model.interfaces.repository.")
//                 .append(this.entidade.getClass().getSimpleName()).append("Repository");
//                 this.genericoRepository = (GenericoRepository<T>) new InitialContext().lookup(resource.toString());
//                 //java:global/protocoloEAR/protocoloEJB/TesteProtocoloDao!br.gov.pb.joaopessoa.protocolo.model.dao.TesteProtocoloRepository
//             }
//             this.genericoRepository.setEntidade((Class<T>) entidade.getClass());
//             return this.genericoRepository;
//	     } catch (NamingException e) {
//	    	 log.error(e.getMessage());
//             throw new PMJPException(Erro.INESPERADO, e);
//	     }
//	}
}