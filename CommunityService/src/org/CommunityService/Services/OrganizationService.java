package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.CommunityService.EntitiesMapped.Organization;
import org.hibernate.HibernateException;

public class OrganizationService {

	public static void addOrganization(Organization o) throws HibernateException {
		DBConnection.save(o);
		return;
	}

	public static void updateOrganization(Organization o) throws HibernateException {
		DBConnection.update(o);
		return;
	}

	public static Organization getOrganizationById(int orgId) throws HibernateException {
		return getOrganizationByIdWithAttachedEntities(orgId, "");
	}

	private static final Map<String, String> entitiesMap;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Groups", " left join fetch o.groups ");
		map.put("OrganizationFollowers",
				" left join fetch o.organizationFollowers as orgf left join fetch orgf.volunteer ");
		map.put("Pictures", " left join fetch o.pictures ");
		entitiesMap = Collections.unmodifiableMap(map);
	}

	public static Organization getOrganizationByIdWithAttachedEntities(int orgId, String... attachedEntities)
			throws HibernateException {
		String hql = "from Organization as o ";
		if (attachedEntities != null) {
			for (int i = 0; i < attachedEntities.length; i++) {
				hql += entitiesMap.get(attachedEntities[i]);
			}
		}
		hql += " where o.orgId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(orgId);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		return organizations.isEmpty() ? null : organizations.get(0);
	}

	public static Object getOrganizationByName(String name) {
		String hql = "from Organization as o where o.orgName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(name);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		return organizations.isEmpty() ? null : organizations.get(0);
	}

	public static Object getOrganizationByAddress(String address) throws HibernateException {
		String hql = "from Organization as o where o.address=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(address);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		return organizations.isEmpty() ? null : organizations.get(0);
	}
}
