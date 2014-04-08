package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/searchgroup", to = "/Web/SearchGroup.xhtml")
public class SearchGroups {
	private Group group;
	private String groupName;
	private List<Group> groups;

	public String Search() {
		groups = GroupService.getGroupsLikeName(groupName);
		return "Search?faces-redirect=true";
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}
