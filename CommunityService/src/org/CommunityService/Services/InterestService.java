package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.VolunteerInterest;
import org.hibernate.HibernateException;

public class InterestService {
	private static List<Interest> interests;

	public static List<Interest> getInterests() {
		if (interests == null) {
			String hql = "from Interest";
			try {
				interests = (List<Interest>) DBConnection.query(hql, null);
				return interests;
			} catch (HibernateException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return interests;
		}
	}

	public static Interest getInterestsById(Integer interestId) {
		if (interestId == null)
			return null;
		String hql = "from Interest as i where i.interestId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(interestId);
		try {
			@SuppressWarnings("unchecked")
			Interest v = (Interest) (((List<Interest>) DBConnection.query(hql, params)).get(0));
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Set<VolunteerInterest> getVolunteerInterestsByVolunteerId(Integer vId) {
		if (vId == null)
			return null;
		String hql = "from VolunteerInterest as VI left join fetch VI.interest where VI.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(vId);
		try {
			@SuppressWarnings("unchecked")
			Set<VolunteerInterest> v = new HashSet<VolunteerInterest>((((List<VolunteerInterest>) DBConnection.query(hql, params))));
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
}
