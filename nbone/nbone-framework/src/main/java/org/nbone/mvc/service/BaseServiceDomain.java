package org.nbone.mvc.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.nbone.lang.BaseObject;
import org.nbone.lang.MathOperation;
import org.nbone.persistence.BaseSqlBuilder;
import org.nbone.persistence.BatchSqlSession;
import org.nbone.persistence.SqlSession;
import org.nbone.util.reflect.GenericsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

/**
 * 
 * @author thinking
 * @since 2016年3月25日下午3:39:45
 *
 * @param <P>
 * @param <IdType>
 */
public  class BaseServiceDomain<P,IdType extends Serializable> extends BaseObject implements SuperService<P, IdType>{

	protected final  Logger logger = LoggerFactory.getLogger(getClass());


	@Resource
	private BatchSqlSession namedJdbcDao;
	
	private Class<P> targetClass;
	/**
	 * 	
	 * 映射mybatis  namespace
	 */
	private String namespace;
	/**
	 * 	
	 * 映射mybatis 实体  resultMap id
	 */
	private String id;
	
	private  boolean builded =  false;
	
	private static long count = 0;

	
	public BaseServiceDomain() {

		this.targetClass = (Class<P>) GenericsUtils.getSuperClassGenricType(this.getClass(), 0);
		count ++;
	}
    /**
     * 初始化mybatis 映射实体 
     * @param namespace  映射文件的命名空间
     * @param id    resultMap id
     */
	protected void initMybatisOrm(String namespace,String id) {
		this.setNamespace(namespace);
		this.setId(id);
	}
	
	protected void initMybatisOrm(Class<?> namespace,String id) {
		this.initMybatisOrm(namespace.getName(), id);
	}
	
	/*	@Autowired @PostConstruct
	@Override
	protected void initMybatisOrm() {
		super.initMybatisOrm(TsProjectBeanDao.class,"BaseResultMap");
	}*/
	protected  void initMybatisOrm(){
		
	}
	

	protected synchronized void  builded(){
	    Assert.notNull(targetClass, "targetClass is not null.thinking");
	    Assert.notNull(namespace, "namespace is not null.thinking");
	    Assert.notNull(id, "id is not null.thinking");

	    if(!builded){
	    	  try {
	  	    	BaseSqlBuilder.buildTableMapper(targetClass,namespace,id);
	  	    	builded =  true;
	  		  } catch (Exception e) {
	  			builded =  false;
	  			logger.error(e.getMessage(),e);
	  		  }
	  		
	    }
	  
		
	}
	
	protected  void checkBuilded(){
		if(!builded){
			builded();
		}
	}
	
	
    public SqlSession getNamedJdbcDao() {
		return namedJdbcDao;
	}

	public void setNamedJdbcDao(BatchSqlSession namedJdbcDao) {
		this.namedJdbcDao = namedJdbcDao;
	}

	public Class<P> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<P> targetClass) {
		this.targetClass = targetClass;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getCount() {
		return count;
	}

	public static void setCount(long count) {
		BaseServiceDomain.count = count;
	}

	//--------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	@Override
	public IdType save(P object) {
		checkBuilded();
		Serializable id  = namedJdbcDao.save(object);
		return (IdType) id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public P add(P object) {
		checkBuilded();
		object = (P) namedJdbcDao.add(object);
		return object;
	}

	@Override
	public int insert(P object) {
		checkBuilded();
		int row = namedJdbcDao.insert(object);
		return row;
	}

	@Override
	public void update(P object) {
		checkBuilded();
		namedJdbcDao.updateSelective(object);
	}
	
	@Override
	public void updateSelective(P object) {
		checkBuilded();
		namedJdbcDao.updateSelective(object);
	}


	@Override
	public void delete(IdType id) {
		checkBuilded();
		namedJdbcDao.delete(targetClass, id);
	}

	@Override
	public P get(IdType id) {
		checkBuilded();
		 P bean = namedJdbcDao.get(targetClass, id);
		return bean;
	}

	@Override
	public void saveOrUpdate(P object) {
		
	}

	@Override
	public void delete(P object) {
		checkBuilded();
		namedJdbcDao.delete(object);
	}
	@Override
	public void deleteByEntityParams(P object) {
		checkBuilded();
		namedJdbcDao.deleteByEntityParams(object);
	}
	@Override
	public List<P> getAll() {
		checkBuilded();
		List<P> beans = namedJdbcDao.getAll(targetClass);
		return beans;
	}


	@Override
	public List<P> getForList(P object) {
		return getForList(object,null);
	}
	@Override
	public List<P> getForList(P object, String afterWhere) {
		checkBuilded();
		List<P> beans =  namedJdbcDao.getForList(object,afterWhere);
		return beans;
	}
	@Override
	public List<P> queryForList(P object) {
		return queryForList(object,null);
	}
	@Override
	public List<P> queryForList(P object, String afterWhere) {
		checkBuilded();
		List<P> beans  = namedJdbcDao.queryForList(object,afterWhere);	
		return beans;
	}
	

	@Override
	public void delete(IdType[] ids) {
		checkBuilded();
		namedJdbcDao.delete(targetClass, ids);
	}
	
	@Override
	public void delete(Collection<?> ids) {
		checkBuilded();
		namedJdbcDao.delete(targetClass, ids.toArray());
	}


	@Override
	public List<P> getAll(IdType[] ids) {
		checkBuilded();
		List<P> beans = namedJdbcDao.getAll(targetClass, ids);
		return beans;
	}
	@Override
	public List<P> getAll(Collection<?> ids) {
		checkBuilded();
		List<P> beans = namedJdbcDao.getAll(targetClass, ids);
		return beans;
	}
	
	@Override
	public long count() {
		checkBuilded();
		return namedJdbcDao.count(targetClass);
	}
	
	@Override
	public long count(P object) {
		checkBuilded();
		return namedJdbcDao.count(object);
	}

	/*
	 * 批量处理增/删/改
	 */
	@Override
	public void batchInsert(P[] objects) {
		checkBuilded();
		namedJdbcDao.batchInsert(objects);
	}
	@Override
	public void batchInsert(Collection<P> objects) {
		checkBuilded();
		namedJdbcDao.batchInsert(objects);
	}
	
	@Override
	public void batchUpdate(P[] objects) {
		checkBuilded();
		namedJdbcDao.batchUpdate(objects);
	}
	@Override
	public void batchUpdate(Collection<P> objects) {
		checkBuilded();
		namedJdbcDao.batchUpdate(objects);
	}
	
	@Override
	public void batchDelete(Class<P> clazz, Serializable[] ids) {
		checkBuilded();
		namedJdbcDao.batchDelete(clazz, ids);
	}
	@Override
	public void batchDelete(Class<P> clazz, List<Serializable> ids) {
		checkBuilded();
		namedJdbcDao.batchDelete(clazz, ids);
	}

	/*
	 * --------------分页---------------------
	 */
	@Override
	public Page<P> getForPage(Object object, int pageNum, int pageSize,String... afterWhere) {
		checkBuilded();
		return namedJdbcDao.getForPage(object, pageNum, pageSize);
	}
	@Override
	public Page<P> queryForPage(Object object, int pageNum, int pageSize,String... afterWhere) {
		checkBuilded();
		return namedJdbcDao.queryForPage(object, pageNum, pageSize);
	}
	@Override
	public Page<P> findForPage(Object object, int pageNum, int pageSize,String... afterWhere) {
		checkBuilded();
		return namedJdbcDao.findForPage(object, pageNum, pageSize);
	}
	
	/*
	 * ----------------------按需返回字段列----------------------
	 */
	@Override
	public <E> List<E> getForList(Object object, String fieldName,Class<E> requiredType) {
		checkBuilded();
		return namedJdbcDao.getForList(object, fieldName,requiredType);
	}
	@Override
	public List<P> getForListWithFieldNames(Object object, String[] fieldNames) {
		checkBuilded();
		return namedJdbcDao.getForListWithFieldNames(object, fieldNames, false);
	}
	@Override
	public int updateMathOperation(Object object, MathOperation mathOperation) {
		checkBuilded();
		return namedJdbcDao.updateMathOperation(object, mathOperation);
	}
	
	



}
