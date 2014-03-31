package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/group/{groupId}", to = "/Web/EditGroup.xhtml")
public class EditGroupBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	List<GroupMember> groupMembers = null;
	List<GroupMember> removed = null;
	List<Volunteer> members = null;
	List<Volunteer> mods = null;
	List<Volunteer> selected = null;
	List<Volunteer> selectedMembers = null;

	private Group group;
	private String groupId;
	private boolean admin = false;

	public void fetchGroup() {
		
		if(groupMembers != null)
			return;
		
		groupMembers = new ArrayList<GroupMember>();
		removed = new ArrayList<GroupMember>();
		members = new ArrayList<Volunteer>();
		mods = new ArrayList<Volunteer>();
		selected = new ArrayList<Volunteer>();
		
		try {
			this.group = GroupService.getGroupById(Integer.parseInt(groupId));

			if (this.group != null) {

				GroupMember eagerMember;
				for (GroupMember m : this.group.getGroupMembers()) {

					eagerMember = GroupService.getGroupMemberById(m
							.getGroupMemberId());
					groupMembers.add(eagerMember);

					if (eagerMember.getAdmin() == true) {

						// if current volunteer is the admin
						if (eagerMember.getVolunteer().getVolunteerId() == currentVolunteer
								.getVolunteer().getVolunteerId())
							admin = true;

						continue; // skip admins
					}

					if (eagerMember.getMod())
						mods.add(eagerMember.getVolunteer());
					else
						members.add(eagerMember.getVolunteer());
				}
			}

		} catch (NumberFormatException e) {
			this.group = null;
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
					 * try {
					 * GroupService.removeGroupMember(g.getGroupMemberId()); }
					 * catch (Exception e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */

				}
			}
		}

		selected = new ArrayList<Volunteer>();

		return null;
	}
	public String save() {
		
		/*
		for (GroupMember g : removed) {

			try {
				GroupService.removeGroupMember(g.getGroupMemberId()); }
			catch (Exception e) { // TODO Auto-generated catch block
				e.printStackTrace(); }
		}
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
}
