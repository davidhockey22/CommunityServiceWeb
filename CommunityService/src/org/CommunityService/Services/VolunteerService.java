package org.CommunityService.Services;

import java.util.ArrayList;

import org.CommunityService.EntitiesMapped.Volunteer;

public class VolunteerService {

    public static Volunteer getVolunteerByName(String name) {
	String hql = "from Volunteer as v where v.volunteerName=?";
	ArrayList<String> params = new ArrayList<String>();
	params.add(name);
	Volunteer v = (Volunteer) DBConnection.query(hql, params);
	return v;
    }

}
