package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.List;

import org.CommunityService.EntitiesMapped.Organization;
import org.hibernate.HibernateException;

public class OrganizationService {

	public static Organization getOrganizationById(int orgId) throws HibernateException {
		String hql = "from Organization as o where o.orgId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(orgId);
		try {
			@SuppressWarnings("unchecked")
			Organization o = (Organization) (((List<Organization>) DBConnection.query(hql, params)).get(0));
			return o;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static Organization getOrganizationByVolunteerId(int volId) throws HibernateException {
		String hql = "from Organization as o where o.volunteer.volunteerId = ? ";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volId);
		try {
			@SuppressWarnings("unchecked")
			Organization o = (Organization) (((List<Organization>) DBConnection.query(hql, params)).get(0));
			return o;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static void addOrganization(Organization o) throws HibernateException {
		DBConnection.save(o);
		return;
	}
	
	public static void updateOrganization(Organization o) throws HibernateException {
		DBConnection.update(o);
		return;
	}

	public static Object getOrganizationByName(String name) {
		String hql = "from Organization as o where o.orgName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(name);
		try {
			@SuppressWarnings("unchecked")
			Organization o = (Organization) (((List<Organization>) DBConnection.query(hql, params)).get(0));
			return o;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static Object getOrganizationByAddress(String address) {
		String hql = "from Organization as o where o.address=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(address);
		try {
			@SuppressWarnings("unchecked")
			Organization o = (Organization) (((List<Organization>) DBConnection.query(hql, params)).get(0));
			return o;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}	
}
