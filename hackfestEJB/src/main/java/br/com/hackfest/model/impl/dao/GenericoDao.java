package br.com.hackfest.model.impl.dao;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
//import org.apache.logging.log4j.core.Logger;
import org.hibernate.Criteria;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.JoinType;

import br.com.hackfest.controller.annotation.SortId;
import br.com.hackfest.controller.annotation.StringKey;
import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.interfaces.repository.GenericoRepository;
import br.com.hackfest.view.Erro;


@Stateless
public class GenericoDao<T> implements GenericoRepository<T> {

	private static final long serialVersionUID = -4418748197624637068L;

	private final Logger log = LogManager.getLogger(getClass());			

	private Class<T> entidade;
	
	@PersistenceContext(unitName=GenericoRepository.PERSISTENCE_UNIT)
	private EntityManager entityManager;

	@Override
	public T criar(T entidade) throws CabideException {
		this.verificaUnicidade(entidade);
		try{
			setNullManytoOne(entidade.getClass(), entidade);
            getEntityManager().persist(entidade);
            return entidade;
            

	    } catch (EntityExistsException ee) {
	    	log.error(ee.getMessage());
            throw new CabideException(Erro.REGISTRO_DUPLICADO, ee, entidade.getClass().getSimpleName());
	    } catch (ConstraintViolationException cv) {
	    	log.error(cv.getMessage());
            throw new CabideException(Erro.REGISTRO_DUPLICADO, cv, entidade.getClass().getSimpleName());
	    } catch (PropertyValueException pv) {
	    	log.error(pv.getMessage());
            throw new CabideException(Erro.PROPRIEDADE_INDEFINIDA, pv);
	    } catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.ERRO_SALVAR, new Exception(t.getCause()));
	    }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> buscarPorExemploWithCriterionAndOrder(T entidade, List<Criterion> criterions, String camposOrdenacao) throws CabideException {
		try{
			
			this.getEntidade();
			
			Example eg = Example.create(entidade).excludeZeroes().ignoreCase().enableLike(MatchMode.ANYWHERE).excludeProperty("serialVersionUID");
	        Session session = (Session) getEntityManager().getDelegate();
	        Criteria criteria = session.createCriteria(this.entidade);
	        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	        this.setCriteriaManytoOne(entidade.getClass(), entidade, criteria, "", "");
	        this.inputCriteriaId(entidade.getClass(), entidade, criteria);
	        
	        this.setCriteriaOrder(entidade.getClass(), entidade, criteria, camposOrdenacao, "", "");
	        
	        for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
	        
	        return criteria.add(eg).list();
	    }catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.BUSCAR_REGISTRO, new Exception(t.getCause()));
	    }
	}

	@Override
	public void criarLista(List<T> lista) throws CabideException {
		try {
			int i = 0;
			for (T entidade : lista) {
				i++;
				
				setNullManytoOne(entidade.getClass(), entidade);
				getEntityManager().persist(entidade);
				
				 if ( i % 20 == 0 ) {
					 getEntityManager().flush();
					 getEntityManager().clear();
					 i = 0;
				 }
			}
			
			if (i > 0){
				getEntityManager().flush();
				getEntityManager().clear();
			}
		} catch (EntityExistsException ee) {
	    	log.error(ee.getMessage());
            throw new CabideException(Erro.REGISTRO_DUPLICADO, ee, entidade.getClass().getSimpleName());
	    } catch (ConstraintViolationException cv) {
	    	log.error(cv.getMessage());
            throw new CabideException(Erro.REGISTRO_DUPLICADO, cv, entidade.getClass().getSimpleName());
	    } catch (PropertyValueException pv) {
	    	log.error(pv.getMessage());
            throw new CabideException(Erro.PROPRIEDADE_INDEFINIDA, pv);
	    } catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.ERRO_SALVAR, new Exception(t.getCause()));
	    }
	}
	
	@Override
	public List<T> criarListaComRetorno(List<T> lista) throws CabideException {
		try {
			int i = 0;
			for (T entidade : lista) {
				i++;
				
				setNullManytoOne(entidade.getClass(), entidade);
				getEntityManager().persist(entidade);
				
				 if ( i % 20 == 0 ) {
					 getEntityManager().flush();
					 getEntityManager().clear();
					 i = 0;
				 }
			}
			
			if (i > 0){
				getEntityManager().flush();
				getEntityManager().clear();
			}
		} catch (EntityExistsException ee) {
	    	log.error(ee.getMessage());
            throw new CabideException(Erro.REGISTRO_DUPLICADO, ee, entidade.getClass().getSimpleName());
	    } catch (ConstraintViolationException cv) {
	    	log.error(cv.getMessage());
            throw new CabideException(Erro.REGISTRO_DUPLICADO, cv, entidade.getClass().getSimpleName());
	    } catch (PropertyValueException pv) {
	    	log.error(pv.getMessage());
            throw new CabideException(Erro.PROPRIEDADE_INDEFINIDA, pv);
	    } catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.ERRO_SALVAR, new Exception(t.getCause()));
	    }
		
		return lista;
	}
	
	
	
	@Override
	public T atualizar(T entidade) throws CabideException {
		this.verificaUnicidade(entidade);
		try{
			setNullManytoOne(entidade.getClass(), entidade);
            return getEntityManager().merge(entidade);

	    } catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.ERRO_ATUALIZAR, new Exception(t.getCause()));
	    }
	}
	
	@Override
	public void atualizarLista(List<T> lista)  throws Exception {
		int i = 0;
		for(T entidade : lista) {
			i++;
			setNullManytoOne(entidade.getClass(), entidade);
			getEntityManager().merge(entidade);
			
			if ( i % 20 == 0 ) {
				getEntityManager().flush();
				getEntityManager().clear();
			}
		}
	}

	@Override
	public void excluir(T entidade) throws CabideException {
		try {
            T entityRemoved = getEntityManager().merge(entidade);
            getEntityManager().remove(entityRemoved);

	    } catch (ConstraintViolationException e) {
            log.error(e.getMessage());
            throw new CabideException(Erro.RESTRICAO_REFERENCIAL, e);
	    } catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.ERRO_EXCLUIR, new Exception(t.getCause()));
	    }
	}

	
	public void verificaUnicidade(T entidade) throws CabideException {
		String namedQueryUnicidade = this.getNamedQuery(this.getEntidade().getSimpleName() + ".unicidade", this.getEntidade());
		if(namedQueryUnicidade!=null && !namedQueryUnicidade.equals("")){
			StringBuilder queryString = new StringBuilder(namedQueryUnicidade);
	        if (queryString != null && !queryString.toString().trim().isEmpty()){
	            if (this.verificaID(this.getEntidade(), entidade) != null) {
	            	queryString = new StringBuilder(queryString.toString().replaceAll("(?i)where ", "where ("));
	                queryString.append(") and id!=:id");
	            }
	
	            Query query = getEntityManager().createQuery(queryString.toString());
	            Pattern pattern = Pattern.compile(":\\w+");
	            Matcher matcher = pattern.matcher(queryString);
	            while (matcher.find()) {
	                String attribute = matcher.group();
	                query.setParameter(attribute.substring(1), this.buscaCampoEntidade(getEntidade(), entidade, attribute.substring(1)));
	            }
	            
	            @SuppressWarnings("unchecked")
				List<T> result = query.getResultList();
	
	            if (result != null && !result.isEmpty()) {
	                Long total = (Long) query.getSingleResult();
	                if (total > 0)
	                    throw new CabideException(Erro.REGISTRO_DUPLICADO, entidade.getClass().getSimpleName());
	            }
	        }
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> consultarPorQuery(T entidade, String queryStr) throws CabideException {
		List<T> result = new ArrayList<T>();
		if(queryStr!=null && !queryStr.equals("")){
			StringBuilder queryString = new StringBuilder(queryStr);
	        if (queryString != null && !queryString.toString().trim().isEmpty()){
	            
	            Session session = (Session) getEntityManager().getDelegate();
	        	org.hibernate.Query query = session.createQuery(queryStr);//getEntityManager().createQuery(queryString.toString());
	            Pattern pattern = Pattern.compile(":\\w+");
	            Matcher matcher = pattern.matcher(queryString);
	            while (matcher.find()) {
	                String attribute = matcher.group();
	                query.setParameter(attribute.substring(1), this.buscaCampoEntidade(getEntidade(), entidade, attribute.substring(1)));
	            }
	            
	            //query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	            //query.setResultTransformer(Transformers.aliasToBean(this.entidade));
	            
	            return query.list();
	
	        }
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> consultarPorQueryRetornaListaObjetos(T entidade, String queryStr) throws CabideException {
		List<Object[]> result = new ArrayList<Object[]>();
		if(queryStr!=null && !queryStr.equals("")){
			StringBuilder queryString = new StringBuilder(queryStr);
	        if (queryString != null && !queryString.toString().trim().isEmpty()){
	            
	            Session session = (Session) getEntityManager().getDelegate();
	        	org.hibernate.Query query = session.createQuery(queryStr);//getEntityManager().createQuery(queryString.toString());
	            Pattern pattern = Pattern.compile(":\\w+");
	            Matcher matcher = pattern.matcher(queryString);
	            while (matcher.find()) {
	                String attribute = matcher.group();
	                query.setParameter(attribute.substring(1), this.buscaCampoEntidade(getEntidade(), entidade, attribute.substring(1)));
	            }
	            
	            //query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	            //query.setResultTransformer(Transformers.aliasToBean(this.entidade));
	            result = (ArrayList<Object[]>)query.list();
	            session.clear();
	
	        }
		}
		return result;
	}

	public void excluirComNamedQuery(T entidade) throws CabideException {
		try{
			String namedQueryUnicidade = this.getNamedQuery(this.getEntidade().getSimpleName() + ".delete", this.getEntidade());
			if(namedQueryUnicidade!=null && !namedQueryUnicidade.equals("")){
				StringBuilder queryString = new StringBuilder(namedQueryUnicidade);
		        if (queryString != null && !queryString.toString().trim().isEmpty()){
		            if (this.verificaID(this.getEntidade(), entidade) != null) {
		            	queryString = new StringBuilder(queryString.toString().replaceAll("(?i)where ", "where ("));
		                queryString.append(") ");
		            }
		
			        Query query = getEntityManager().createQuery(queryString.toString());
		            Pattern pattern = Pattern.compile(":\\w+");
		            Matcher matcher = pattern.matcher(queryString);
		            while (matcher.find()) {
		                String attribute = matcher.group();
		                query.setParameter(attribute.substring(1), this.buscaCampoEntidade(getEntidade(), entidade, attribute.substring(1)));
		            }
		            
			        query.executeUpdate();
		        }
			}
		} catch (Exception e){
			throw new CabideException(Erro.ERRO_EXCLUIR, entidade.getClass().getSimpleName() + " " + e.getMessage());
		}
	}

	@Override
	public T buscarPorID(Long id) throws CabideException {
		return getEntityManager().find(this.getEntidade(), id);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> buscarTodos() throws CabideException {
		this.getEntidade();
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(this.entidade));
		return getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> buscarPorExemplo(T entidade) throws CabideException {
		try{
			
			this.getEntidade();
			
	        Example eg = Example.create(entidade).excludeZeroes().ignoreCase().enableLike(MatchMode.ANYWHERE).excludeProperty("serialVersionUID");
	        Session session = (Session) getEntityManager().getDelegate();
	        Criteria criteria = session.createCriteria(this.entidade);
	        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	        this.setNullString(entidade.getClass(), entidade);
	        this.setCriteriaManytoOne(entidade.getClass(), entidade, criteria, "", "");
	        this.inputCriteriaId(entidade.getClass(), entidade, criteria);
	        return criteria.add(eg).list();
	    }catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.BUSCAR_REGISTRO, new Exception(t.getCause()));
	    }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> buscarPorExemploWithCriterion(T entidade, List<Criterion> criterions) throws CabideException {
		try{
			
			this.getEntidade();
			
	        Example eg = Example.create(entidade).excludeZeroes().ignoreCase().enableLike(MatchMode.ANYWHERE).excludeProperty("serialVersionUID");
	        Session session = (Session) getEntityManager().getDelegate();
	        Criteria criteria = session.createCriteria(this.entidade);
	        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	        this.setCriteriaManytoOne(entidade.getClass(), entidade, criteria, "", "");
	        this.inputCriteriaId(entidade.getClass(), entidade, criteria);
	        
	        for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
	        
	        return criteria.add(eg).list();
	    }catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.BUSCAR_REGISTRO, new Exception(t.getCause()));
	    }
	}
	
	public void setCriteriaManytoOne(Class<?> clazz, Object entidade, Criteria criteria, String property, String nameClassAlias) throws Exception {
		try {
			if(clazz.getSuperclass()!=null){
				setCriteriaManytoOne(clazz.getSuperclass(), entidade, criteria, property, nameClassAlias);
			}
			for (Field field : clazz.getDeclaredFields()) {
				if ((field.getAnnotation(ManyToOne.class)) != null || field.getAnnotation(OneToOne.class)!=null) {
					field.setAccessible(true);
					if (field.get(entidade) != null) {
						String nameProperty = nameClassAlias.concat("".concat(entidade.getClass().getSimpleName().concat("_".concat(field.getName().concat("_1")))));
				        this.setNullString(entidade.getClass(), entidade);
						Example eg = Example.create(field.get(entidade)).excludeZeroes().ignoreCase().enableLike(MatchMode.ANYWHERE).excludeProperty("serialVersionUID");
						criteria.createCriteria(property.concat(field.getName()), nameProperty, JoinType.LEFT_OUTER_JOIN).add(eg);
						//criteria.createCriteria(property.concat(field.getName())).add(eg);
						nameClassAlias += entidade.getClass().getSimpleName(); 
						
						//criteria.add(Restrictions.eq(field.getName(), field.get(entidade)));
						//Contador -- não permite que passe pelo mesmo objeto mais de uma vez quando houver mais de um atributo ManyToOne dentro do ObjetoPrincipal
						int cont = 1;
						for (Field fld : field.get(entidade).getClass().getDeclaredFields()) {
							fld.setAccessible(true);
							if (fld.get(field.get(entidade)) != null) {
								if(fld.getAnnotation(ManyToOne.class)!=null || fld.getAnnotation(OneToOne.class)!=null){
									if(cont==1)
										setCriteriaManytoOne(field.get(entidade).getClass(), field.get(entidade), criteria, nameProperty.concat("."), nameClassAlias);
									cont++;
								}else if(fld.getAnnotation(ManyToMany.class)==null){
									if(!fld.getName().equals("serialVersionUID") && fld.getAnnotation(SortId.class)==null && fld.getAnnotation(Transient.class)==null){
										if(fld.getGenericType().toString().contains("java.lang.String")){
											if(!fld.get(field.get(entidade)).equals("")){
												//criteria.add(Restrictions.ilike(nameProperty.concat("."+fld.getName()), "%"+fld.get(field.get(entidade))+"%"));
											}
										}else{
											if(fld.getAnnotation(Id.class)!=null && !String.valueOf(fld.get(field.get(entidade))).equals("0"))
												criteria.add(Restrictions.eq(nameProperty.concat("."+fld.getName()), fld.get(field.get(entidade))));
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException ia) {
            throw new Exception("Erro ao varrer annotation", ia);
		}
	}
	
	public void setCriteriaOrder(Class<?> clazz, Object entidade, Criteria criteria, String order, String property, String nameClassAlias) throws Exception {
		try {
			if(clazz.getSuperclass()!=null){
				setCriteriaOrder(clazz.getSuperclass(), entidade, criteria, order, property, nameClassAlias);
			}
			if(order!=null && order!=""){
				List<String> arrSort = Arrays.asList(order.split(","));
				
				for(String s: arrSort){
					
					if(!s.contains(".")){
						//Contains order
						if(s.contains(":")){
							String[] typeOrder = new String[s.split(":").length];
							typeOrder = s.split(":");
							if(typeOrder[1].toUpperCase().equals("ASC")){
								criteria.addOrder(Order.asc(typeOrder[0]));
							}else{
								criteria.addOrder(Order.desc(typeOrder[0]));
							}
						}else{
							criteria.addOrder(Order.asc(s));
						}
					}else{
						String[] namePropertyOrder = new String[s.split(Pattern.quote(".")).length];
						namePropertyOrder = s.split(Pattern.quote("."));
						for (Field field : clazz.getDeclaredFields()) {
							if(namePropertyOrder[0].trim().equals(field.getName())){
								if ((field.getAnnotation(ManyToOne.class)) != null || field.getAnnotation(OneToOne.class)!=null) {
									String nameProperty = nameClassAlias.concat("".concat(entidade.getClass().getSimpleName().concat("_".concat(field.getName().concat("_1")))));
									if(namePropertyOrder.length > 2){
										String newOrder = "";										
										for(int i=1; i<namePropertyOrder.length; i++){
											newOrder+= namePropertyOrder[i].trim().concat(".");
										}
										newOrder = newOrder.substring(0, newOrder.length() - 2);
										this.setCriteriaOrder(field.get(entidade).getClass(), field.get(entidade), criteria, newOrder, nameProperty.concat("."), entidade.getClass().getSimpleName());
									}else{
										if(namePropertyOrder[1].contains(":")){
											String[] typeOrder = namePropertyOrder[1].split(":");
											if(typeOrder[1].toUpperCase().trim().equals("ASC")){
												criteria.addOrder(Order.asc(nameProperty.concat("."+typeOrder[0].trim())));
											}else{
												criteria.addOrder(Order.desc(nameProperty.concat("."+typeOrder[0].trim())));
											}
										}else{
											criteria.addOrder(Order.asc(nameProperty.concat("."+namePropertyOrder[1].trim())));
										}
									}
								}
							}
								
						}
						
						
					}
					
				}
			}
		} catch (IllegalArgumentException ia) {
            throw new Exception("Erro ao varrer annotation", ia);
		}
	}
	
	public void setNullString(Class<?> clazz, Object entidade) throws Exception {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if ((field.getAnnotation(ManyToOne.class)) == null || (field.getAnnotation(OneToMany.class)) == null) {
					field.setAccessible(true);
					if(!field.getName().equals("serialVersionUID") && field.getAnnotation(SortId.class)==null && field.getAnnotation(Transient.class)==null){
						if(field.getGenericType().toString().contains("java.lang.String")){
							if(field.get(entidade) != null && field.get(entidade).equals("")){
								field.set(entidade, null);
							}
						}
						
					}
				}
			}
		} catch (IllegalArgumentException ia) {
            throw new Exception("Erro ao varrer annotation", ia);
		}
	}
	
	public void setNullManytoOne(Class<?> clazz, Object entidade) throws Exception {
		try {
			//Varrer os Atributos
			for (Field field : clazz.getDeclaredFields()) {
				//Verificar se o atributo é do tipo ManyToOne/OneToOne
				if ((field.getAnnotation(ManyToOne.class)) != null || field.getAnnotation(OneToOne.class)!=null) {
					//Dar permissão de escrita ao atributo
					field.setAccessible(true);
					//Verificar se o atributo(Que é um Objeto) é diferente de null
					if (field.get(entidade) != null) {
						//Varrer os Atributos do Objeto que é um atributo da entidade principal
						int cont = 1;
						for (Field fld : field.get(entidade).getClass().getDeclaredFields()) {
							//Dar permissão de escrita ao atributo
							fld.setAccessible(true);
							//Verificar se é o ID e se está diferente de null
							if ((fld.getAnnotation(Id.class)) != null ) {
								//Verificar se o ID é igual a null
								if (fld.get(field.get(entidade)) == null) {
									//Atribuir NULL à Entidade
									field.set(entidade, null);
									//Parar o laço dos atributos do Objeto que é um atributo da Entidade Principal
									break;
								}
							}else if(fld.getAnnotation(ManyToOne.class)!=null || fld.getAnnotation(OneToOne.class)!=null){
								if(cont==1)
									this.setNullManytoOne(field.get(entidade).getClass(), field.get(entidade));
								cont++;
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException ia) {
            throw new Exception("Erro ao varrer annotation", ia);
		}
	}
	

	@Override
	public Number contaResultados(T entidade) throws CabideException {
		try{
			this.getEntidade();
			this.removeVoidString(entidade.getClass(), entidade);
	        Example eg = Example.create(entidade).ignoreCase().excludeZeroes().enableLike(MatchMode.ANYWHERE).excludeProperty("serialVersionUID");
	        Session session = (Session) getEntityManager().getDelegate(); 
	        Criteria criteria = session.createCriteria(entidade.getClass());
	        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	        String entityId = this.getEntityId(entidade.getClass(), entidade);
	        
	        if(entityId != null)
	        	criteria.setProjection(Projections.count(entityId));
	        else 
	        	criteria.setProjection(Projections.rowCount());
	        
	        this.setNullString(entidade.getClass(), entidade);
	        this.setCriteriaManytoOne(entidade.getClass(), entidade, criteria, "", "");
	        this.inputCriteriaId(entidade.getClass(), entidade, criteria);
	        return (Number) criteria.add(eg).uniqueResult();
	        //return (Number) this.buscarPorExemplo(entidade).size();

	    }catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.BUSCAR_REGISTRO, new Exception(t.getCause()));
	    }
	}
	
	@Override
	public Number contaResultadosWithCriterions(T entidade, List<Criterion> criterions) throws CabideException {
		try{
			this.getEntidade();
			this.removeVoidString(entidade.getClass(), entidade);
	        Example eg = Example.create(entidade).ignoreCase().excludeZeroes().enableLike(MatchMode.ANYWHERE).excludeProperty("serialVersionUID");
	        Session session = (Session) getEntityManager().getDelegate(); 
	        Criteria criteria = session.createCriteria(entidade.getClass());
	        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	        String entityId = this.getEntityId(entidade.getClass(), entidade);
	        
	        if(entityId != null)
	        	criteria.setProjection(Projections.count(entityId));
	        else 
	        	criteria.setProjection(Projections.rowCount());
	        
	    	for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}        	
	        
	        this.setNullString(entidade.getClass(), entidade);
	        this.setCriteriaManytoOne(entidade.getClass(), entidade, criteria, "", "");
	        this.inputCriteriaId(entidade.getClass(), entidade, criteria);
	        return (Number) criteria.add(eg).uniqueResult();
	        //return (Number) this.buscarPorExemplo(entidade).size();

	    }catch (Throwable t) {
	    	log.error(t.getMessage());
            throw new CabideException(Erro.BUSCAR_REGISTRO, new Exception(t.getCause()));
	    }
	}
	
	private String getEntityId(Class<?> clazz, Object entidade){
		String entityId = null;
		for(Field field : clazz.getDeclaredFields()){
			if ((field.getAnnotation(Id.class)) != null ) {
				entityId = field.getName();
			}
		}
		return entityId;
	}
	
	private void removeVoidString(Class<?>clazz, T entidade) throws IllegalArgumentException, IllegalAccessException{
		for (Field field : clazz.getDeclaredFields()){
			if(field.getType().equals(String.class)){
					field.setAccessible(true);
					if(field.get(entidade) != null && field.get(entidade).equals("")){
						field.set(entidade, null);
					}
			}
		}
	}
	
	private void inputCriteriaId(Class<?> clazz, Object entidade, Criteria criteria) throws Exception {
		for (Field field : clazz.getDeclaredFields()) {
			if ((field.getAnnotation(Id.class)) != null ) {
				field.setAccessible(true);
				if (field.get(entidade) != null) {
					if(!field.get(entidade).equals(0L) && !String.valueOf(field.get(entidade)).equals("0") && !field.get(entidade).equals("")){
						criteria.add(Restrictions.idEq(field.get(entidade)));
					}
				}
			}else if((field.getAnnotation(StringKey.class)) != null){
				field.setAccessible(true);
				if (field.get(entidade) != null) {
					if(!String.valueOf(field.get(entidade)).equals("0") && !field.get(entidade).equals("")){
						criteria.add(Restrictions.eq("this.".concat(field.getName()), field.get(entidade)));
						//criteria.add(Restrictions.idEq(field.get(entidade)));
					}
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> loadPaginated(T entidade, int inicio, int tamanho, String campoOrdenacao, String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		List<T> listaResultados = null;
        try{
        	if(parametros != null && !parametros.isEmpty()){
        		for(Entry<String, Object> entry : parametros.entrySet()){
        			Field[] camposDeclarados = this.getEntidade().getDeclaredFields();
        			for(Field campo : camposDeclarados){
        				if(campo.getName().equals(entry.getKey())){
        					campo.setAccessible(true);
        					try{
        						campo.set(entidade, entry.getValue());
        					}catch (IllegalAccessException e) {
								log.error("");
								throw new CabideException(null);
							}
        				}
        			}
        		}
        	}
        	this.removeVoidString(entidade.getClass(), entidade);
        	Example eg = Example.create(entidade);
        	eg.excludeZeroes();
        	eg.enableLike(MatchMode.ANYWHERE);
        	eg.ignoreCase();
        	Session session = (Session) this.getEntityManager().getDelegate();
        	Criteria criteria = session.createCriteria(this.getEntidade());
        	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        	/*if(tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && campoOrdenacao != null && !campoOrdenacao.isEmpty()){
        		if(tipoOrdenacao.equals("ASC"))
        			criteria.addOrder(Order.asc(campoOrdenacao));
        		if(tipoOrdenacao.equals("DESC"))
        			criteria.addOrder(Order.desc(campoOrdenacao));
        	}
        	*/
        	
	        this.setNullString(entidade.getClass(), entidade);
	        this.setCriteriaManytoOne(entidade.getClass(), entidade, criteria, "", "");
	        this.inputCriteriaId(entidade.getClass(), entidade, criteria);
        
	        
        	this.setCriteriaOrder(entidade.getClass(), entidade, criteria, campoOrdenacao, "", "");
	        
        	criteria.add(eg);
        	criteria.setFirstResult(inicio);
        	criteria.setMaxResults(tamanho);
        	listaResultados = criteria.list();

        } catch (CabideException e) {
            log.error(e.getMessage());
            throw new CabideException(Erro.BUSCAR_REGISTRO, e);
        } catch (Exception e) {
			e.printStackTrace();
		}
        return listaResultados;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> loadPaginatedWithCriterion(T entidade, int inicio, int tamanho, String campoOrdenacao, String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where, List<Criterion> criterions) throws CabideException {
		List<T> listaResultados = null;
        try{

        	this.removeVoidString(entidade.getClass(), entidade);
        	Example eg = Example.create(entidade);
        	eg.excludeZeroes();
        	eg.enableLike(MatchMode.ANYWHERE);
        	eg.ignoreCase();
        	Session session = (Session) this.getEntityManager().getDelegate();
        	Criteria criteria = session.createCriteria(this.getEntidade());
        	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

         	/*if(tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && campoOrdenacao != null && !campoOrdenacao.isEmpty()){
        		if(tipoOrdenacao.equals("ASC"))
        			criteria.addOrder(Order.asc(campoOrdenacao));
        		if(tipoOrdenacao.equals("DESC"))
        			criteria.addOrder(Order.desc(campoOrdenacao));
        	}*/
        	
	        this.setNullString(entidade.getClass(), entidade);
	        this.setCriteriaManytoOne(entidade.getClass(), entidade, criteria, "", "");
	        this.inputCriteriaId(entidade.getClass(), entidade, criteria);
        	
        	this.setCriteriaOrder(entidade.getClass(), entidade, criteria, campoOrdenacao, "", "");
	        
	        criteria.add(eg);
        	
        	for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}        	
        	
        	criteria.setFirstResult(inicio);
        	criteria.setMaxResults(tamanho);
        	listaResultados = criteria.list();

        } catch (CabideException e) {
            log.error(e.getMessage());
            throw new CabideException(Erro.BUSCAR_REGISTRO, e);
        } catch (Exception e) {
			e.printStackTrace();
		}
        return listaResultados;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getNamedQuery(String namedQuery, Class c) throws CabideException {
        try {
			NamedQueries namedQueries = (NamedQueries) c.getAnnotation(NamedQueries.class);
	        NamedQuery[] queriesArr = namedQueries.value();
	        for (NamedQuery n : queriesArr)
                if (n.name().equals(namedQuery))
                    return n.query();
        } catch (Exception e) {
        	log.error(e.getMessage());
        }
        return null;
	}

	protected Object verificaID(Class<? super T> classe, T t) {
        if (classe != Object.class) {
            Field[] campos = classe.getDeclaredFields();
            for (Field field : campos) {
	            if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        return field.get(t);
                    } catch (Exception e) {
                    	log.error(e.getMessage());
                    }
                }
            }
        }
        if (classe.getSuperclass() != Object.class) {
            return verificaID(classe.getSuperclass(), t);
        }
        return null;
	}

	protected Object buscaCampoEntidade(Class<? super T> classe, T t, String nomeCampo) {
        if (classe != Object.class) {
            Field[] campos = classe.getDeclaredFields();
            for (Field field : campos) {
                if (field.getName().equals(nomeCampo)) {
                    field.setAccessible(true);
                    try {
                        return field.get(t);
                    } catch (Exception e) {
                    	log.error(e.getMessage());
                    }
                }
            }
        }

        if (classe.getSuperclass() != Object.class)
            return buscaCampoEntidade(classe.getSuperclass(), t, nomeCampo);
        return null;
	}

	@Override
	public void setEntidade(Class<T> entidade) throws CabideException {
		this.entidade = entidade;
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> getEntidade() {
        if (this.entidade == null){
            this.entidade = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return this.entidade;
	}

	@Override
	public T buscarPorEntidade(T entidade) throws CabideException {
		// TODO Auto-generated method stub
		List<T> result = this.buscarPorExemplo(entidade);  
		if( result != null && result.size() > 0 ){
			return result.iterator().next();
		}
		return null;
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public Number idMaxEntidade() throws CabideException {
		DetachedCriteria maxId = DetachedCriteria.forClass(this.getEntidade())
			    .setProjection( Projections.max("id") );
    	
		Session session = (Session) this.getEntityManager().getDelegate();
    	Criteria criteria = session.createCriteria(this.getEntidade());
    	criteria.add(Property.forName("id").eq(maxId));
		
    	T result = (T) criteria.uniqueResult();
		
		return (Number) buscaCampoEntidade(this.entidade, result, "id");
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public T maxEntidade(T entidade) throws CabideException {
		DetachedCriteria maxId = DetachedCriteria.forClass(entidade.getClass())
			    .setProjection( Projections.max("id") );
    	
		Session session = (Session) this.getEntityManager().getDelegate();
    	Criteria criteria = session.createCriteria(entidade.getClass());
    	
    	Example eg = Example.create(entidade);
    	eg.excludeZeroes();
    	eg.enableLike(MatchMode.ANYWHERE);
    	eg.ignoreCase();
    	
    	criteria.add(eg);
    	
    	criteria.add(Property.forName("id").eq(maxId));
    	
		
    	T result = (T) criteria.uniqueResult();
		
//		return  buscaCampoEntidade(this.entidade, result, "id");
    	return result;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public void delete(T entidade, Long id) throws CabideException{
		try {
//			getEntityManager().getTransaction().begin();
			entidade = (T) getEntityManager().find(entidade.getClass(), id);
			getEntityManager().remove(entidade);
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deletarLista(List<T> lista) throws CabideException, Exception {
			int i = 0;
			for (T entidade : lista) {
				i++;
				
				setNullManytoOne(entidade.getClass(), entidade);
				getEntityManager().remove(entidade);
				
				 if ( i % 20 == 0 ) {
					 getEntityManager().flush();
					 getEntityManager().clear();
					 i = 0;
				 }
			}
			
			if (i > 0){
				getEntityManager().flush();
				getEntityManager().clear();
			}

	}
	
	
	
}