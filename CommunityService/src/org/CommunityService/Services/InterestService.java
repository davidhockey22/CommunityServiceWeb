package org.CommunityService.Services;

import java.util.List;

import org.CommunityService.EntitiesMapped.Interest;

public class InterestService {

	
	@SuppressWarnings("unchecked")
	public static List<Interest> getInterests() throws Exception {
		String hql = "from Interest";
		List<Interest> events = (List<Interest>) DBConnection.query(hql, null);
		return events;
	}
	
	
	
	
	
	
}
