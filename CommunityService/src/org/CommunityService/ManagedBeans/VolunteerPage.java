package org.CommunityService.ManagedBeans;

import java.util.Date;

import hibernate.HibernateUtil;

import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@ManagedBean
public class VolunteerPage {

    String world = "Hello World!";

    public String getWorld() {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	String hql = "from Volunteer as v where v.volunteerName=:name";
	Query q = session.createQuery(hql).setString("name", "David");
	Volunteer v = (Volunteer) q.list().get(0);
	this.world = v.getEmailAddress();
	session.getTransaction().commit();
	session.close();
	return world;
    }
}