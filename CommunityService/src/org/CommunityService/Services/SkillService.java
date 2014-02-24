package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.List;

import org.CommunityService.EntitiesMapped.Skill;

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

}
