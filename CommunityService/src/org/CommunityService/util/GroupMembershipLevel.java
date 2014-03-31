package org.CommunityService.util;

import java.util.List;

import org.CommunityService.EntitiesMapped.GroupMember;

public class GroupMembershipLevel {
	private String name;
	private List<GroupMember> groupMembers;
	
	public GroupMembershipLevel(String name, List<GroupMember> groupMembers) {
		this.name = name;
		this.groupMembers = groupMembers;
	}

	public String getName() {
		return name;
	}

	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}
}

