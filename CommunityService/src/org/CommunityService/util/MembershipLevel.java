package org.CommunityService.util;

import java.util.List;

public class MembershipLevel <T>{
	private String name;
	private List<T> members;
	
	public MembershipLevel(String name, List<T> members) {
		this.name = name;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public List<T> getMembers() {
		return members;
	}
}

