package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/manageGroup/{groupId}", to = "/Web/EditGroup.xhtml")
public class EditGroupBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String groupId;

	List<GroupMember> groupMembers = null;
	List<GroupMember> removed = null;
	List<Volunteer> members = null;
	List<Volunteer> mods = null;
	List<Volunteer> selected = null;
	List<Volunteer> selectedMembers = null;

	private Group group;

	private boolean admin = false;

	public void fetchGroup() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			this.group = GroupService.getGroupByIdWithAttachedEntities(Integer.parseInt(groupId), "GroupMembers",
					"Organizations");
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid groupId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid group id");
			context.responseComplete();
		}

		// Throw an HTTP Response Error - Page Not Found in case group cannot be found from provided id
		if (this.group == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Invalid group id");
			context.responseComplete();
		}

		boolean authorized = false;
		if (currentVolunteer.getVolunteer() != null) {
			for (Iterator<GroupMember> iterator = currentVolunteer.getVolunteer().getGroupMembers().iterator(); iterator
					.hasNext();) {
				GroupMember groupMember = (GroupMember) iterator.next();
				if (groupMember.getGroup().getGroupId() == this.group.getGroupId()) {
					authorized = (groupMember.getAdmin());
					break;
				}
			}
		} else {
			// Throw an HTTP Response Error - Missing Credentials in case user is not logged in
			context.getExternalContext().responseSendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
			context.responseComplete();
		}
		if (!authorized) {
			// Throw an HTTP Response Error - Forbidden access in case user is not logged in
			context.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN,
					"Not authorized to view page");
			context.responseComplete();
		}

		groupMembers = new ArrayList<GroupMember>();
		removed = new ArrayList<GroupMember>();
		members = new ArrayList<Volunteer>();
		mods = new ArrayList<Volunteer>();
		selected = new ArrayList<Volunteer>();

		if (this.group != null) {
			for (GroupMember m : this.group.getGroupMembers()) {
				if (m.getAdmin() == true) {
					// if current volunteer is the admin
					if (m.getVolunteer().getVolunteerId() == currentVolunteer.getVolunteer().getVolunteerId())
						admin = true;
					continue; // skip admins
				} else if (m.getMod()) {
					mods.add(m.getVolunteer());
				} else {
					members.add(m.getVolunteer());
				}
			}
		}
	}

	public String removeMembers() {

		if (selected.isEmpty())
			return null;

		for (Volunteer v : selected) {
			for (Iterator<Volunteer> iterator = members.iterator(); iterator.hasNext();) {
				Volunteer m = (Volunteer) iterator.next();

				if (m.getVolunteerId() == v.getVolunteerId()) {

					iterator.remove();
				}
			}
		}

		for (Volunteer v : selected) {
			for (GroupMember g : groupMembers) {

				if (v.getVolunteerId() == g.getVolunteer().getVolunteerId()) {

					removed.add(g);
				}
			}
		}

		selected = new ArrayList<Volunteer>();

		return null;
	}

	public String removeMods() {

		if (selected.isEmpty())
			return null;

		for (Volunteer v : selected) {
			for (Iterator<Volunteer> iterator = mods.iterator(); iterator.hasNext();) {
				Volunteer m = (Volunteer) iterator.next();

				if (m.getVolunteerId() == v.getVolunteerId()) {

					iterator.remove();
				}
			}
		}

		for (Volunteer v : selected) {
			for (GroupMember g : groupMembers) {

				if (v.getVolunteerId() == g.getVolunteer().getVolunteerId()) {

					removed.add(g);

					/*
					 * try { GroupService.removeGroupMember(g.getGroupMemberId()); } catch (Exception e) { // TODO
					 * Auto-generated catch block e.printStackTrace(); }
					 */

				}
			}
		}

		selected = new ArrayList<Volunteer>();

		return null;
	}

	public String save() {

		/*
		 * for (GroupMember g : removed) {
		 * 
		 * try { GroupService.removeGroupMember(g.getGroupMemberId()); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } }
		 */

		for (GroupMember g : removed) {
			for (Iterator<GroupMember> iterator = group.getGroupMembers().iterator(); iterator.hasNext();) {
				GroupMember m = (GroupMember) iterator.next();

				if (m.getGroupMemberId() == g.getGroupMemberId()) {

					iterator.remove();
				}
			}
		}

		try {
			GroupService.updateGroup(group);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Volunteer> getMembers() {
		return members;
	}

	public void setMembers(List<Volunteer> members) {
		this.members = members;
	}

	public boolean getAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<Volunteer> getMods() {
		return mods;
	}

	public void setMods(List<Volunteer> mods) {
		this.mods = mods;
	}

	public List<Volunteer> getSelected() {
		return selected;
	}

	public void setSelected(List<Volunteer> selected) {
		this.selected = selected;
	}

	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public List<GroupMember> getRemoved() {
		return removed;
	}

	public void setRemoved(List<GroupMember> removed) {
		this.removed = removed;
	}

	public List<Volunteer> getSelectedMembers() {
		return selectedMembers;
	}

	public void setSelectedMembers(List<Volunteer> selectedMembers) {
		this.selectedMembers = selectedMembers;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
