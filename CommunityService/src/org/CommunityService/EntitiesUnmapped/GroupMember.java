package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * GroupMember generated by hbm2java
 */
public class GroupMember implements java.io.Serializable {

	private GroupMemberId id;
	private Group group;
	private Volunteer volunteer;
	private Date joinedDate;

	public GroupMember() {
	}

	public GroupMember(GroupMemberId id, Group group, Volunteer volunteer, Date joinedDate) {
		this.id = id;
		this.group = group;
		this.volunteer = volunteer;
		this.joinedDate = joinedDate;
	}

	public GroupMemberId getId() {
		return this.id;
	}

	public void setId(GroupMemberId id) {
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

	public Date getJoinedDate() {
		return this.joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

}