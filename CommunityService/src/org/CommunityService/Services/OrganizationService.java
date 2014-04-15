package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.hibernate.HibernateException;

public class OrganizationService {

	public static void addFollower(Volunteer v, Organization o) {
		OrganizationFollower OF = new OrganizationFollower();
		OF.setVolunteer(v);
		OF.setOrganization(o);
		OF.setAdmin(false);
		OF.setMod(false);
		DBConnection.persist(OF);
	}

	public static void removeOrgFollower(Integer orgId, Integer vId) {
		List<Integer> params = new ArrayList<Integer>();
		params.add(orgId);
		params.add(vId);
		DBConnection.queryDelete(
				"delete OrganizationFollower o where o.organization.orgId=? and o.volunteer.volunteerId=?", params);

	}

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

	public static final String GROUPS = "Groups";
	public static final String ORGANIZATION_FOLLOWERS = "OrganizationFollowers";
	public static final String EVENTS = "Events";
	public static final String PICTURES = "Pictures";
	private static final Map<String, String> entitiesMap;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put(GROUPS, " left join fetch o.groups ");
		map.put(ORGANIZATION_FOLLOWERS,
				" left join fetch o.organizationFollowers as orgf left join fetch orgf.volunteer ");
		map.put(EVENTS,
				" left join fetch o.events as ev left join fetch ev.eventVolunteers left join fetch ev.volunteer");
		map.put(PICTURES, " left join fetch o.pictures ");
		entitiesMap = Collections.unmodifiableMap(map);
	}

	/**
	 * Returns an Organization object that is either the organization with the given id with the requested entities
	 * eagerly fetched or null if the organization is not found. Valid Strings to pass are the public string fields of
	 * this class
	 * 
	 * @param orgId
	 * @param attachedEntities
	 * @return
	 * @throws HibernateException
	 */
	public static Organization getOrganizationByIdWithAttachedEntities(int orgId, String... attachedEntities)
			throws HibernateException {
		String hql = "from Organization as o ";
		if (attachedEntities != null) {
			for (int i = 0; i < attachedEntities.length; i++) {
				String next = entitiesMap.get(attachedEntities[i]);
				hql += next == null ? "" : next;
			}
		}
		hql += " where o.orgId=?";
		List<Integer> params = new ArrayList<Integer>();
		params.add(orgId);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		return organizations.isEmpty() ? null : organizations.get(0);
	}

	/**
	 * Replaces the attached events associated with an organization with the current events associated with the
	 * organization and between the given dates. Given dates can be in any order. Sets the organization to null if the
	 * organization no longer exists.
	 * 
	 * @param org
	 * @param date1
	 * @param date2
	 */
	public static void refreshOrganizationEventsBetweenDates(Organization org, Date date1, Date date2) {
		Date before, after;
		if (date1.after(date2)) {
			after = date1;
			before = date2;
		} else {
			after = date2;
			before = date1;
		}
		String hql = "from Organization as o left join fetch o.events as ev left join fetch ev.volunteer where o.orgId = ? and ev.endTime > ? and ev.beginTime < ?";
		List<Object> params = new ArrayList<Object>();
		params.add(org.getOrgId());
		params.add(before);
		params.add(after);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		Organization o = organizations.isEmpty() ? null : organizations.get(0);
		if (o == null) {
			org = null;
		} else {
			org.setEvents(o.getEvents());
		}
	}

	/**
	 * Replaces the attached events associated with an organization with the current events associated with the
	 * organization before the given date if before is set to true or after the given date if before is set to false.
	 * Sets the organization to null if the organization no longer exists.
	 * 
	 * @param org
	 * @param date
	 * @param before
	 */
	public static void refreshOrganizationEventsBeforeOrAfterDate(Organization org, Date date, boolean before) {
		String hql = "from Organization as o left join fetch o.events as e left join fetch e.eventVolunteers where o.orgId = ? and ";
		hql += before ? "e.beginTime < ?" : "e.endTime > ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(org.getOrgId());
		params.add(date);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		Organization o = organizations.isEmpty() ? null : organizations.get(0);
		if (o == null) {
			org = null;
		} else {
			org.setEvents(o.getEvents());
		}
	}

	public static void refreshOrganizationEventsForFollower(Organization org, Volunteer volunteer) {
		// went at this backwards on purpose: going the other way traverses more one-to-many joins
		String hql = "select v from Volunteer as v left join fetch v.eventVolunteers as ev left join fetch ev.event as e left join fetch e.eventVolunteers where v.volunteerId=? and e.organization.orgId=?";
		List<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		params.add(org.getOrgId());
		@SuppressWarnings("unchecked")
		List<Volunteer> volunteers = (List<Volunteer>) DBConnection.query(hql, params);
		Volunteer v = volunteers.isEmpty() ? null : volunteers.get(0);
		if (v == null) {
			org.setEvents(Collections.<Event> emptySet());
		} else {
			Set<Event> events = new HashSet<Event>();
			for (EventVolunteer eventVolunteer : v.getEventVolunteers()) {
				events.add(eventVolunteer.getEvent());
			}
			org.setEvents(events);
		}
	}

	public static void refreshOrganizationEvents(Organization org) throws HibernateException {
		Organization o = getOrganizationByIdWithAttachedEntities(org.getOrgId(), EVENTS);
		if (o != null) {
			org.setEvents(o.getEvents());
		} else {
			org = null;
		}
	}

	public static void detachOrganizationEvents(Organization org) {
		org.setEvents(Collections.<Event> emptySet());
	}

	public static void refreshOrganizationGroups(Organization org) throws HibernateException {
		Organization o = getOrganizationByIdWithAttachedEntities(org.getOrgId(), GROUPS);
		if (o != null) {
			org.setGroups(o.getGroups());
		} else {
			org = null;
		}
	}

	public static void refreshOrganizationMembers(Organization org) throws HibernateException {
		Organization o = getOrganizationByIdWithAttachedEntities(org.getOrgId(), ORGANIZATION_FOLLOWERS);
		if (o != null) {
			org.setOrganizationFollowers(o.getOrganizationFollowers());
		} else {
			org = null;
		}
	}

	public static Organization getOrganizationByName(String name) {
		String hql = "from Organization as o where o.orgName=?";
		List<String> params = new ArrayList<String>();
		params.add(name);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		return organizations.isEmpty() ? null : (Organization) organizations.get(0);
	}

	public static Organization getOrganizationByAddress(String address) throws HibernateException {
		String hql = "from Organization as o where o.address=?";
		List<String> params = new ArrayList<String>();
		params.add(address);
		@SuppressWarnings("unchecked")
		List<Organization> organizations = (List<Organization>) DBConnection.query(hql, params);
		return organizations.isEmpty() ? null : (Organization) organizations.get(0);
	}

	public static OrganizationFollower getOrganizationFollowerBy(Integer orgId, Integer volunteerId)
			throws HibernateException {
		if (orgId == null || volunteerId == null) {
			return null;
		}
		String hql = "from OrganizationFollower as orgF where orgF.organization.orgId = ? and orgF.volunteer.volunteerId = ?";
		List<Integer> params = new ArrayList<Integer>();
		params.add(orgId);
		params.add(volunteerId);
		@SuppressWarnings("unchecked")
		List<OrganizationFollower> orgFollowers = (List<OrganizationFollower>) DBConnection.query(hql, params);
		return orgFollowers.isEmpty() ? null : (OrganizationFollower) orgFollowers.get(0);
	}
}
