package com.guestbook.service;


import java.util.List;

import com.guestbook.service.util.BasicQuery;


/**
 * A Java generics-based DAO interface that handles all the repetitive boilerplate DAO methods.
 * See guestbook.service.impl._GenericDaoHibernateImpl to see the generic implementation class.
 * 
 * @author kdmukai
 *
 * @param <T>
 */
public interface IGenericDAO<T> {
	
	/**
	 * If the entity already exists, you must use update() instead. There is not yet 
	 * error handling to warn you if you're attempting to save() an entity that is 
	 * already in the DB.
	 * 
	 * @param entity
	 */
	public void save(T entity);
	public T get(Integer id);
	
	/**
	 * The return object is attached to the DB session and should be used for subsequent object
	 * manipulation and update. The input entity is not guaranteed to be attached and so should
	 * not be used after the update() call.
	 * 
	 * @param entity
	 * @return
	 */
	public T update(T entity);
	public void delete(Integer id);
	
	public List<T> getAll();
	
	/**
	 * Basic one parameter/value SELECT query. Equivalent to: "WHERE paramName=paramValue"
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public List<T> find(String paramName, String paramValue);
	
	
	/**
	 * Uses the BasicQuery util class to build slightly more complex queries with multiple
	 * filterParams and multiple sortParams.
	 * 
	 * @param query
	 * @return
	 */
	public List<T> find(BasicQuery query);
	
	
	/**
	 * Fully customizable search query using HibernateTemplate's findByNamedParam() format.
	 * 
	 * @param queryString
	 * @param paramNames
	 * @param paramValues
	 * @return
	 */
	public List<T> find(String queryString, String paramNames[], String paramValues[]);
}
