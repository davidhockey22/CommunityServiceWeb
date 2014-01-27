package org.CommunityService.Services;

import java.util.List;

import org.CommunityService.EntitiesMapped.Skill;

public class SkillService {

	@SuppressWarnings("unchecked")
	public static List<Skill> getSkills() throws Exception {
		String hql = "from Skill";
		List<Skill> Skills = (List<Skill>) DBConnection.query(hql, null);
		return Skills;
	}

}
