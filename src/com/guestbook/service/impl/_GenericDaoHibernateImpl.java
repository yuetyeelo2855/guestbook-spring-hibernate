package com.guestbook.service.impl;


import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.guestbook.service.IGenericDAO;
import com.guestbook.service.util.BasicQuery;
import com.guestbook.service.util.BasicQuery.FilterParam;
import com.guestbook.service.util.BasicQuery.SortDir;
import com.guestbook.service.util.BasicQuery.SortParam;


public abstract class _GenericDaoHibernateImpl<T> extends HibernateDaoSupport implements IGenericDAO<T> {

	protected Class<T> clazz;

	@SuppressWarnings("unchecked")
	public _GenericDaoHibernateImpl() {
		clazz = ((Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	
    /**
     * This is necessary if you're going to extend this implementation class for entity-specific
     * methods. e.g.:
     <pre>
      	public interface IMyPojoDao extends IGenericDAO<MyPojo> {}
      	public class MyPojoDaoHibernateImpl extends _GenericDaoHibernateImpl<MyPojo> implements IMyPojoDao {
      		public MyPojoDaoHibernateImpl() {
      			super(MyPojo.class);
      		}
      }
     </pre>
     *
     * @param clazz
     */
    protected _GenericDaoHibernateImpl(Class<T> clazz) {
		this.clazz = clazz;
    }


	
	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public T get(Integer id) {
		return getHibernateTemplate().get(clazz, id);
	}
	
	@Override
	public T update(T entity) {
		// merge() plays nicely with the OpenSessionInViewFilter when singleSession=false
		//	whereas update() causes session confusion. 
		//	Not sure if there are downsides to using it here.
		return getHibernateTemplate().merge(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void delete(final Integer id) {
		// Delete must take place within a HibernateCallback so that the object's associated
		//	Session can be retrieved; a simple getHibernateTemplate().delete() causes
		//	Session conflicts with the OpenSessionInViewFilter.
	    @SuppressWarnings("rawtypes")
		HibernateCallback callback = new HibernateCallback() {
	        public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            Object entity = session.load(clazz, id);
	            session.delete(entity);
	            return null;
	        }
	    };
	    getHibernateTemplate().execute(callback);	
	}


	@Override
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(String paramName, String paramValue) {
		Criteria c = this.getSession().createCriteria(clazz);
		c.add( Property.forName(paramName).eq(paramValue) );
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(BasicQuery query) {
		Criteria c = this.getSession().createCriteria(clazz);
		
		for (FilterParam filter: query.getFilterParams()) {
			c.add( Property.forName(filter.getParamName()).eq(filter.getParamValue()) );
		}
		
		for (SortParam sortParam: query.getSortParams()) {
			if (sortParam.getSortDir().equals(SortDir.ASC)) {
				c.addOrder( Order.asc(sortParam.getParamName()) );
			} else {
				c.addOrder( Order.desc(sortParam.getParamName()) );
			}
		}
		
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String queryString, String paramNames[], String paramValues[]) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames, paramValues);
	}

	
	
}
