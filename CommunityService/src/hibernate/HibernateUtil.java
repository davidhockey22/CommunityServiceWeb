package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory sessionFactory = buildSessionFactory();
	private static ServiceRegistry serviceRegistry;

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw ex;
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Object clean(Object obj) {
		return removePersistenceContext(obj, new ArrayList<Integer>());
	}

	/**
	 * Returns a Collection of all objects in the specified persistentCollection
	 * without binding to any persistence context or session.
	 * 
	 * @param <T>
	 * @param targetCollection
	 * @param persistentCollection
	 * @return
	 */
	public static <T> Collection<T> getCollectionItemsRemovedFromPersistenceContext(
			Collection<T> targetCollection, Collection<T> persistentCollection)
			throws LazyInitializationException {
		// If runtime type of persistentCollection is not PersistentCollection,
		// take no action
		if (!(persistentCollection instanceof PersistentCollection))
			return persistentCollection;

		// Clear existing target
		targetCollection.clear();

		// Place all items in persistent collection into target
		for (T item : persistentCollection) {
			targetCollection.add(item);
		}

		// Return target
		return targetCollection;
	}

	/**
	 * Returns a Map of all objects in the specified persistentCollection Map
	 * without binding to any persistence context or session.
	 * 
	 * @param <T>
	 * @param targetCollection
	 * @param persistentCollection
	 * @return
	 */
	public static <T, U> Map<T, U> getCollectionItemsRemovedFromPersistenceContext(
			Map<T, U> targetMap, Map<T, U> persistentMap)
			throws LazyInitializationException {
		// If runtime type of persistentCollection is not PersistentCollection,
		// take no action
		if (!(persistentMap instanceof PersistentCollection))
			return persistentMap;

		// Clear existing target
		targetMap.clear();

		// Place all items in persistent collection into target
		for (T key : persistentMap.keySet()) {
			targetMap.put(key, persistentMap.get(key));
		}

		// Return target
		return targetMap;
	}

	/**
	 * Checks if the object is an Entity bean by searching for the presence of
	 * the @Entity tag in the objects class. NOTE - If you are not using
	 * annotations to define your entity objects this function will not work and
	 * require an alternative implementation.
	 * 
	 */
	protected static boolean isEntityBean(Object obj) {
		for (Annotation a : obj.getClass().getAnnotations()) {
			if (a.annotationType() == Entity.class) {
				return true;
			}
		}
		return false;
	}

	/**
	 * If the specified object's identity hash code is not in the specified
	 * collection of visited hash codes, removes of all data binding the object
	 * (and its members) to a specific persistence context, leaving intact only
	 * model-centric data
	 * 
	 * @param visitedObjectHashCodes
	 * @param obj
	 * @author ALR
	 */

	@SuppressWarnings("rawtypes")
	protected static Object removePersistenceContext(Object obj,
			Collection<Integer> visitedObjectHashCodes) {
		if (obj == null) {
			return null;
		}

		if (visitedObjectHashCodes.contains(System.identityHashCode(obj))) {
			return obj;
		}

		// Add the object's hash to the Collection of visited hash codes
		visitedObjectHashCodes.add(System.identityHashCode(obj));

		try {
			if (obj instanceof Set) {
				obj = getCollectionItemsRemovedFromPersistenceContext(
						new HashSet(), (Set) obj);
			} else if (obj instanceof SortedSet) {
				obj = getCollectionItemsRemovedFromPersistenceContext(
						new TreeSet(), (SortedSet) obj);
			} else if (obj instanceof List) {
				obj = getCollectionItemsRemovedFromPersistenceContext(
						new ArrayList(), (List) obj);
			} else if (obj instanceof Map) {
				obj = getCollectionItemsRemovedFromPersistenceContext(
						new HashMap(), (Map) obj);
			} else if (obj instanceof SortedMap) {
				obj = getCollectionItemsRemovedFromPersistenceContext(
						new TreeMap(), (SortedMap) obj);
			} else if (obj instanceof PersistentCollection) {
				obj = getCollectionItemsRemovedFromPersistenceContext(
						new ArrayList(), (Collection) obj);
			}
		} catch (LazyInitializationException e) {
			return null;
		}

		if (!isEntityBean(obj)) {
			return obj;
		}

		Map allMembers = getInternalMembers(obj);
		for (Object member : allMembers.entrySet()) {
			Map.Entry m = (Map.Entry) member;
			try {
				try {
					PropertyUtils.setProperty(
							obj,
							m.getKey().toString(),
							removePersistenceContext(m.getValue(),
									visitedObjectHashCodes));
				} catch (LazyInitializationException e) {

					PropertyUtils.setProperty(obj, m.getKey().toString(), null);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
			}
		}

		return obj;
	}

	/**
	 * Returns a Map of all internal members of the specified object
	 * 
	 * @param obj
	 *            The object for which to obtain internal members
	 * 
	 * @author ALR
	 * @see http://jakarta.apache.org/commons/beanutils/
	 */
	@SuppressWarnings("rawtypes")
	protected static Map getInternalMembers(Object obj) {
		try {
			Map map = PropertyUtils.describe(obj);
			return map;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

}