package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.Services.GroupService;
import org.CommunityService.util.MemberLevel;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path = "/group/{groupId}", to = "/Web/ViewGroup.xhtml")
public class ViewGroupBean {
	@ManagedProperty(value = "#{param.groupId}")
	private String groupId;

	private Group group = null;

	private List<MemberLevel<GroupMember>> levels = new ArrayList<MemberLevel<GroupMember>>();

	public void fetchGroup() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.group = GroupService.getGroupByIdWithAttachedEntities(Integer.parseInt(groupId), "GroupMembers");

			if (this.group != null) {

				List<GroupMember> admins = new ArrayList<GroupMember>();
				List<GroupMember> moderators = new ArrayList<GroupMember>();
				List<GroupMember> members = new ArrayList<GroupMember>();

				for (Iterator<GroupMember> iterator = this.group.getGroupMembers().iterator(); iterator.hasNext();) {
					GroupMember groupMember = (GroupMember) iterator.next();
					if (groupMember.getAdmin()) {
						admins.add(groupMember);
					} else if (groupMember.getMod()) {
						moderators.add(groupMember);
					} else {
						members.add(groupMember);
					}
				}

				// add the collections in the order in which they are to be displayed
				levels.add(new MemberLevel<GroupMember>("Administrators", admins));
				levels.add(new MemberLevel<GroupMember>("Members", moderators));
				levels.add(new MemberLevel<GroupMember>("Followers", members));
			} else {
				// Throw an HTTP Response Error - Page Not Found in case event cannot be found from provided id
				context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
						"Group with id " + groupId + " does not exist");
				context.responseComplete();
			}
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Page Not Found in case event cannot be found from provided id
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid group id");
			context.responseComplete();
		}
	}

	public Group getGroup() {
		return group;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<MemberLevel<GroupMember>> getLevels() {
		return levels;
	}
}
