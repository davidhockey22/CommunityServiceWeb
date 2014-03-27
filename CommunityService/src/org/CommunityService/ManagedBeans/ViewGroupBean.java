package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path="/group/{groupId}", to="/Web/ViewGroup.xhtml")
public class ViewGroupBean {

	private Group group;
	private String groupId;
	
	public void fetchGroup() {
		try {
			this.group = GroupService.getGroupById(Integer.parseInt(groupId));
		} catch (NumberFormatException e) {
			this.group = null;
		}
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}	
}
