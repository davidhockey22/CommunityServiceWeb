package org.CommunityService.EntitesUnmapped;

// Generated Feb 23, 2014 5:44:45 PM by Hibernate Tools 3.4.0.CR1

/**
 * EventSkill generated by hbm2java
 */
public class EventSkill implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4732036106788457278L;
	private Integer eventSkillId;
	private Event event;
	private Skill skill;
	private Boolean required;

	public EventSkill() {
	}

	public EventSkill(Event event, Skill skill) {
		this.event = event;
		this.skill = skill;
	}

	public EventSkill(Event event, Skill skill, Boolean required) {
		this.event = event;
		this.skill = skill;
		this.required = required;
	}

	public Integer getEventSkillId() {
		return this.eventSkillId;
	}

	public void setEventSkillId(Integer eventSkillId) {
		this.eventSkillId = eventSkillId;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

}