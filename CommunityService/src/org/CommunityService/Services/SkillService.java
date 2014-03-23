package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.EntitiesMapped.VolunteerInterest;
import org.CommunityService.EntitiesMapped.VolunteerSkill;
import org.hibernate.HibernateException;

public class SkillService {

	@SuppressWarnings("unchecked")
	public static List<Skill> getSkills() throws Exception {
		String hql = "from Skill";
		List<Skill> Skills = (List<Skill>) DBConnection.query(hql, null);
		return Skills;
	}

	public static Skill getSkillById(Integer SkillID) {
		if (SkillID == null)
			return null;
		String hql = "from Skill as s where s.skillId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(SkillID);
		try {
			@SuppressWarnings("unchecked")
			Skill v = (Skill) (((List<Skill>) DBConnection.query(hql, params)).get(0));
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Set<VolunteerSkill> getVolunteerSkillsByVolunteerId(Integer vId) {
		if (vId == null)
			return null;
		String hql = "from VolunteerSkill as VS join fetch VS.skill where VS.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(vId);
		try {
			@SuppressWarnings("unchecked")
			Set<VolunteerSkill> v = new HashSet((((List<VolunteerSkill>) DBConnection.query(hql, params))));
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

}
