package org.CommunityService.Servlets;

import hibernate.HibernateUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@WebServlet(urlPatterns = { "/PlayerStats" })
public class BasicServletTest extends HttpServlet implements DataSource {

    /**
     * 
     */
    private static final long serialVersionUID = -219092789929475544L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	// session.save(new Event("EventTest22", new Date(), new
	// Date(System.currentTimeMillis() + 10000000), (float) 1.25));
	// session.save(new Interest("Running",
	// "For people who enjoy not walking places."));
	session.save(new Group("Group1", "David", 5, (float) 3, (float) 1.27));
	// session.save()
	// session.save()
	// session.save()
	// session.save()
	// session.save()
	// session.save()
	// session.save()
	// session.save()
	// session.save()
	// session.save()
	// session.save()

	session.getTransaction().commit();
	session.close();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setLogWriter(PrintWriter arg0) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void setLoginTimeout(int arg0) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Connection getConnection(String arg0, String arg1) throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

}
