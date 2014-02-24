package org.CommunityService.Services;

import java.util.List;

import hibernate.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DBConnection {

	static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public static Object query(String query, List params) throws HibernateException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query q = session.createQuery(query);
		Object v = null;
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				q = q.setParameter(i, params.get(i));
			}
		}
		if (q != null) {
			v = q.list();

		}
		session.getTransaction().commit();
		session.close();

		return v;

	}

	public static Object persist(Object object) throws HibernateException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		if (object != null) {
			session.persist(object);
		}
		session.getTransaction().commit();
		session.close();
		return null;

	}

	public static Object update(Object object) throws HibernateException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		if (object != null) {
			session.merge(object);
		}
		session.getTransaction().commit();
		session.close();
		return null;

	}
}
