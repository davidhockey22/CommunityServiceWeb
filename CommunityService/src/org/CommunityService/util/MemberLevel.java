package org.CommunityService.util;

import java.util.List;

public class MemberLevel <T>{
	private String name;
	private List<T> members;
	
	public MemberLevel(String name, List<T> members) {
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

