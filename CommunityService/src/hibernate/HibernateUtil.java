package hibernate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.Entity;

import org.hibernate.Hibernate;
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
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
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

	public static Object clean(Object o) throws IllegalArgumentException, IllegalAccessException {
		if (isEntityBean(o)) {
			Field[] fields = o.getClass().getDeclaredFields();

			for (Field f : fields) {
				// System.out.println(f.getGenericType());

				if (Collection.class.isAssignableFrom(f.getType())) {
					// System.out.println("Cleaning field");
					boolean accessible = f.isAccessible();
					f.setAccessible(true);
					f.set(o, null);
					// if (!(Hibernate.isInitialized(f))) {
					// System.out.println("Not Initialized");
					// f.set(o, null);
					// } else {
					// f.set(o, clean(f));
					// }
					f.setAccessible(accessible);
				}
			}
		}
		return o;
	}

	protected static boolean isEntityBean(Object obj) {
		for (Annotation a : obj.getClass().getAnnotations()) {
			if (a.annotationType() == Entity.class) {
				return true;
			}
		}
		return false;
	}

}