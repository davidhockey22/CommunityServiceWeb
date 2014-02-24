package org.CommunityService.EntitesUnmapped;

// Generated Feb 23, 2014 5:44:45 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * GroupMember generated by hbm2java
 */
public class GroupMember implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3137627638279861219L;
	private Integer groupMemberId;
	private Group group;
	private Volunteer volunteer;
	private Date joinedDate;
	private Boolean mod;
	private Boolean admin;

	public GroupMember() {
	}

	public GroupMember(Group group, Volunteer volunteer) {
		this.group = group;
		this.volunteer = volunteer;
	}

	public GroupMember(Group group, Volunteer volunteer, Date joinedDate, Boolean mod, Boolean admin) {
		this.group = group;
		this.volunteer = volunteer;
		this.joinedDate = joinedDate;
		this.mod = mod;
		this.admin = admin;
	}

	public Integer getGroupMemberId() {
		return this.groupMemberId;
	}

	public void setGroupMemberId(Integer groupMemberId) {
		this.groupMemberId = groupMemberId;
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

	public Boolean getMod() {
		return this.mod;
	}

	public void setMod(Boolean mod) {
		this.mod = mod;
	}

	public Boolean getAdmin() {
		return this.admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

}
