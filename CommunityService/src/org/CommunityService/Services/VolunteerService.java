package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.List;

import org.CommunityService.EntitiesMapped.Volunteer;

public class VolunteerService {

	@SuppressWarnings("unchecked")
	public static Volunteer getVolunteerByName(String name) throws Exception {
		String hql = "from Volunteer as v where v.volunteerName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(name);
		try{
			Volunteer v = (Volunteer) (((List<Volunteer>) DBConnection.query(hql, params)).get(0));
			return v;
		} catch(Exception e){
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	public static List<Volunteer> getVolunteers() throws Exception {
		String hql = "from Volunteer as v";
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null);
		return v;
	}

	public static void addVolunteer(Volunteer v) throws Exception {
		DBConnection.persist(v);
		return;
	}
}
