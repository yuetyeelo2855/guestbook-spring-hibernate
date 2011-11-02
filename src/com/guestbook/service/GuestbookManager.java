package com.guestbook.service;

import java.util.HashMap;


import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.guestbook.model.Entry;
import com.guestbook.model.Guest;
import com.guestbook.service.impl._GenericDaoHibernateImpl;


/**
 * This is where the Java generics approach for the DAO layer pays off! There are two
 * complicated pieces here:
 * 
 * 1. Singleton and abstraction techniques that aren't strictly necessary within this
 * class.
 * 
 * 2. The necessary definitions to make the generic DAO approach work.
 * 
 * Normally this would be a GuestbookManagerHibernateImpl implementation class for an 
 * IGuestbookManager interface that would be injected by Spring. But for simplicity 
 * I'm skipping the interface separation and allowing my code to directly instantiate
 * this class.
 * 
 * You'll also notice that I've combined the usual Manager vs DAO distinctions here.
 * I just got sick of all those layers of mechanism just for the goal of code separation
 * that ended up being more philosophically-driven then practical. For straightforward
 * projects and/or small teams I don't think it's necessary.
 * 
 * Note: If you need some entity-specific functionality, you can extend the GenericDAO
 * interface and implementation class. Replace the declarations here with your
 * entity-specific version and your client code will never know if it's using the
 * normal GenericDAO or your custom entity-specific implementation class. And obviously
 * you only have to implement the new or overrided methods if you extend the GenericDAO.
 * 
 * Pretty damn convenient, methinks!
 * 
 * 
 * @author kdmukai
 *
 */
public class GuestbookManager {
	
	// Java magic to prevent instantiation for a static-only singleton pattern.
	//	By making it a singleton, we don't have to call Spring's getBean() method
	//	to get a properly-injected instance. Spring instantiates the singleton
	//	and injects it on startup and then we internally control that instance
	//	with the singleton pattern. Pretty cool!
	private static GuestbookManager instance;
	protected GuestbookManager() {}
	@SuppressWarnings("rawtypes")
	protected static GuestbookManager getInstance() { 
		if (instance == null) {
			instance = new GuestbookManager();
			instance.managerMap = new HashMap<Class, HibernateDaoSupport>();
		}
		return instance; 
	}

	
	// Stores the instantiated DAOs
	@SuppressWarnings("rawtypes")
	private HashMap<Class, HibernateDaoSupport> managerMap;
	
	
	// Necessary for hibernate integration. Injected by Spring at startup.
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		getInstance().sessionFactory = sessionFactory;
	}
	
	
	// More Java magic to instantiate the DAO without having to manually code
	//	each individual instantiation call. Also initializes the DAO so that
	//	it's ready to access the Hibernate session.
	@SuppressWarnings("rawtypes")
	private HibernateDaoSupport get(Class clazz) {
		// Retrieve the specified manager/dao
		HibernateDaoSupport manager = managerMap.get(clazz);
		
		if (manager == null) {
			try {
				manager = (HibernateDaoSupport)Class.forName(clazz.getName()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Pass the Hibernate SesssionFactory in. This is all the "wiring up" we need!
			manager.setSessionFactory(sessionFactory);
			
			// Store it so we can reuse it on subsequent calls.
			managerMap.put(clazz, manager);
		}
		return manager;
	}
	
	
	
	// Here's the Java generics magic!!!
	
	// Step 1: Declare the entity-specific versions of the Generic interface
	public interface IEntryManager extends IGenericDAO<Entry> {}
	public interface IGuestManager extends IGenericDAO<Guest> {}	

	
	// Step 2: Then use those interfaces as we declare entity-specific DAOs
	//	Note: Declarations need to be static for the abstract instantiation above to work!!
	protected static class EntryManagerImpl extends _GenericDaoHibernateImpl<Entry> implements IEntryManager {}
	protected static class GuestManagerImpl extends _GenericDaoHibernateImpl<Guest> implements IGuestManager {}
	
	
	// Step 3: Expose the entity-specific DAOs to the client code.
	//	Note: Access is static because the singleton pattern renders it senseless to instantiate a
	//	"new" object.
	public static IEntryManager forEntry() {
		return (IEntryManager) getInstance().get(EntryManagerImpl.class);
	}
	public static IGuestManager forGuest() {
		return (IGuestManager) getInstance().get(GuestManagerImpl.class);
	}
	
}
