package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * GroupMod generated by hbm2java
 */
public class GroupMod implements java.io.Serializable {

	private GroupModId id;
	private Group group;
	private Volunteer volunteer;
	private Date modSince;

	public GroupMod() {
	}

	public GroupMod(GroupModId id, Group group, Volunteer volunteer, Date modSince) {
		this.id = id;
		this.group = group;
		this.volunteer = volunteer;
		this.modSince = modSince;
	}

	public GroupModId getId() {
		return this.id;
	}

	public void setId(GroupModId id) {
		this.id = id;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public Date getModSince() {
		return this.modSince;
	}

	public void setModSince(Date modSince) {
		this.modSince = modSince;
	}

}
