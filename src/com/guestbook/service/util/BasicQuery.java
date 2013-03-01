/*********************************************************************

Copyright (c) 2011 EssayTagger.com, LLC.

Created by kdmukai Nov 2, 2011, 10:46:01 AM.

Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*********************************************************************/
package com.guestbook.service.util;

import java.util.ArrayList;
import java.util.List;


/** 
  A simple util class to build basic DB queries with parameter filters and specified sort orders.
  
  Usage:
  <pre>
  	myDao.find(	new BasicQuery()
  				.filter("prop1", value1)
  				.filter("prop2", value2)
  				.sort("prop3", SortDir.DESC)
  				.sort("prop4") );
  </pre>
  This is equivalent to: "WHERE prop1=value1 AND prop2=value2 ORDER BY prop3 DESC, prop4 ASC"
  
  An example of how to then use the BasicQuery in a Hibernate + Spring implementation:
  <pre>
	import org.hibernate.Criteria;
	import org.hibernate.Session;
	import org.hibernate.criterion.Order;
	import org.hibernate.criterion.Property;
	import org.springframework.orm.hibernate3.HibernateCallback;
	import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

	public class MyPojoDao extends HibernateDaoSupport {
		public List<MyPojo> find(BasicQuery query) {
	 		Criteria c = this.getSession().createCriteria(MyPojo.class);
			for (FilterParam pair: queryList.getFilterParams()) {
				c.add( Property.forName(pair.getParamName()).eq(pair.getParamValue()) );
			}
			
			for (SortParam sortParam: queryList.getSortParams()) {
				if (sortParam.getSortDir().equals(SortDir.ASC)) {
					c.addOrder( Order.asc(sortParam.getParamName()) );
				} else {
					c.addOrder( Order.desc(sortParam.getParamName()) );
				}
			}
			return c.list();
		}
	}
	</pre>
 * 
 * @author kdmukai
 *
 */
public class BasicQuery {
	public static enum SortDir { ASC, DESC }

	public class FilterParam {
		private String paramName;
		private Object paramValue;
		
		public FilterParam(String paramName, Object paramValue) {
			this.paramName = paramName;
			this.paramValue = paramValue;
		}
		public String getParamName() {
			return paramName;
		}
		public Object getParamValue() {
			return paramValue;
		}
	}
	
	public class SortParam {		
		private String paramName;
		private SortDir sortDir;
		
		public SortParam(String paramName, SortDir sortDir) {
			this.paramName = paramName;
			this.sortDir = sortDir;
		}
		public String getParamName() {
			return paramName;
		}
		public SortDir getSortDir() {
			return sortDir;
		}		
	}

	
	
	private ArrayList<FilterParam> filterList = new ArrayList<FilterParam>();
	private ArrayList<SortParam> sortList = new ArrayList<SortParam>();
	

	public BasicQuery filter(String paramName, Object paramValue) {
		FilterParam param = new FilterParam(paramName, paramValue);
		filterList.add(param);
		return this;
	}
	public BasicQuery sort(String paramName, SortDir sortDir) {
		SortParam sortParam = new SortParam(paramName, sortDir);
		sortList.add(sortParam);
		return this;
	}
	public BasicQuery sort(String paramName) {
		return sort(paramName, SortDir.ASC);
	}
	public List<SortParam> getSortParams() {
		return sortList;
	}
	public List<FilterParam> getFilterParams() {
		return filterList;
	}
}


