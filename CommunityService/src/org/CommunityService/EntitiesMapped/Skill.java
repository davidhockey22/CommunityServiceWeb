package org.CommunityService.EntitiesMapped;

// Generated Feb 10, 2014 11:13:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Skill generated by hbm2java
 */
@Entity
@Table(name = "Skill", catalog = "dbAppData", uniqueConstraints = @UniqueConstraint(columnNames = "SkillName"))
public class Skill implements java.io.Serializable {

	private Integer skillId;
	private String skillName;
	private String description;
	private Set<EventSkill> eventSkills = new HashSet<EventSkill>(0);
	private Set<VolunteerSkill> volunteerSkills = new HashSet<VolunteerSkill>(0);

	public Skill() {
	}

	public Skill(String skillName) {
		this.skillName = skillName;
	}

	public Skill(String skillName, String description, Set<EventSkill> eventSkills, Set<VolunteerSkill> volunteerSkills) {
		this.skillName = skillName;
		this.description = description;
		this.eventSkills = eventSkills;
		this.volunteerSkills = volunteerSkills;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SkillID", unique = true, nullable = false)
	public Integer getSkillId() {
		return this.skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	@Column(name = "SkillName", unique = true, nullable = false, length = 20)
	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Column(name = "Description", length = 16777215)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "skill")
	public Set<EventSkill> getEventSkills() {
		return this.eventSkills;
	}

	public void setEventSkills(Set<EventSkill> eventSkills) {
		this.eventSkills = eventSkills;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "skill")
	public Set<VolunteerSkill> getVolunteerSkills() {
		return this.volunteerSkills;
	}

	public void setVolunteerSkills(Set<VolunteerSkill> volunteerSkills) {
		this.volunteerSkills = volunteerSkills;
	}

}
