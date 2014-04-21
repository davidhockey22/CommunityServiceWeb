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
import org.CommunityService.Services.NotificationService;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.event.CellEditEvent;

@ManagedBean
@ViewScoped
@Join(path = "/managegroup/{groupId}", to = "/Web/EditGroup.xhtml")
public class EditGroupBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String groupId;

	List<Volunteer> admins = null;
	List<Volunteer> mods = null;
	List<Volunteer> members = null;
	List<Volunteer> pending = null;

	private Group group;

	private boolean admin = false;
	private boolean mod = false;
	private boolean adminOrMod = false;

	private String strActive = new String("Active");
	private String strRemove = new String("Remove from group");
	private String strMakeMod = new String("Make a moderator");
	private String strRemoveMod = new String("Remove moderator status and make a regular member");

	private String strApprove = new String("Accept into group");
	private String strReject = new String("Reject");

	private String[] actions;
	private String[] actionsMods;
	private String[] actionsPending;

	// using f:viewAction will call this method only when the view is first
	// generated
	public void fetchGroup() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		// test
		// groupId = "42";

		actions = new String[3];
		actions[0] = strActive;
		actions[1] = strRemove;
		actions[2] = strMakeMod;

		actionsMods = new String[3];
		actionsMods[0] = strActive;
		actionsMods[1] = strRemove;
		actionsMods[2] = strRemoveMod;

		actionsPending = new String[2];
		actionsPending[0] = strApprove;
		actionsPending[1] = strReject;

		try {
			this.group = GroupService.getGroupByIdWithAttachedEntities(Integer.parseInt(groupId), "GroupMembers");
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid
			// groupId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid group id");
			context.responseComplete();
			return;
		}

		// Throw an HTTP Response Error - Page Not Found in case group cannot be
		// found from provided id
		if (this.group == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Group with id " + groupId + " not found.");
			context.responseComplete();
			return;
		}

		// authorize
		boolean authorized = false;
		if (currentVolunteer.getVolunteer() != null) {
			for (Iterator<GroupMember> iterator = currentVolunteer.getVolunteer().getGroupMembers().iterator(); iterator.hasNext();) {
				GroupMember groupMember = (GroupMember) iterator.next();
				if (groupMember.getGroup().getGroupId() == this.group.getGroupId()) {
					authorized = (groupMember.getAdmin() || groupMember.getMod());
					break;
				}
			}
		} else {
			// Throw an HTTP Response Error - Missing Credentials in case user
			// is not logged in
			context.getExternalContext().responseSendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
			context.responseComplete();
			return;
		}
		if (!authorized) {
			// Throw an HTTP Response Error - Forbidden access in case user is
			// not logged in
			context.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to view page");
			context.responseComplete();
			return;
		}

		admins = new ArrayList<Volunteer>();
		members = new ArrayList<Volunteer>();
		mods = new ArrayList<Volunteer>();
		pending = new ArrayList<Volunteer>();

		if (this.group != null) {
			for (GroupMember m : this.group.getGroupMembers()) {

				if (m.getApproved() == false)
					m.getVolunteer().setSalt(strApprove);
				else
					m.getVolunteer().setSalt(strActive);

				if (m.getAdmin() == true) {
					// if current volunteer is the admin
					if (m.getVolunteer().getVolunteerId() == currentVolunteer.getVolunteer().getVolunteerId()) {
						admin = true;
						adminOrMod = true;
					}

					admins.add(m.getVolunteer());
				} else if (m.getMod()) {
					// if current volunteer is the mod
					if (m.getVolunteer().getVolunteerId() == currentVolunteer.getVolunteer().getVolunteerId()) {
						mod = true;
						adminOrMod = true;
					}

					mods.add(m.getVolunteer());
				} else if (m.getApproved() == null || m.getApproved() == true) {
					members.add(m.getVolunteer());
				} else {
					pending.add(m.getVolunteer());
				}
			}
		}
	}

	public String save() {
		String salt;
		boolean updateGroup = true;

		for (Iterator<GroupMember> iterator = group.getGroupMembers().iterator(); iterator.hasNext();) {
			GroupMember g = (GroupMember) iterator.next();

			salt = g.getVolunteer().getSalt();

			if (salt.equals(strActive)) {
			} else if (g.getVolunteer().getSalt().equals(strApprove)) {
				NotificationService.createNotification(g.getVolunteer(), "You have been approved to join the group: " + group.getGroupName(), "");
				g.setApproved(true);

				try {
					GroupService.updateGroupMember(g);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (g.getVolunteer().getSalt().equals(strRemove) || // if
																		// remove
																		// group
																		// member
					g.getVolunteer().getSalt().equals(strReject)) { // if reject
																	// group
																	// member

				updateGroup = true;
				iterator.remove();
			} else if (salt.equals(strMakeMod)) {

				g.setMod(true);

				try {
					GroupService.updateGroupMember(g);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (salt.equals(strRemoveMod)) {

				g.setMod(false);

				try {
					GroupService.updateGroupMember(g);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (updateGroup) {
			try {
				GroupService.updateGroup(group);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// call fetchGroup again so page refreshed
		try {
			fetchGroup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String deleteGroup() {

		GroupService.removeGroup(group.getGroupId());

		return "/Home.xhtml?faces-redirect=true";
	}

	public void onCellEdit(CellEditEvent event) {

		// Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null) {

			int row = event.getRowIndex();
			String action = (String) newValue;

			Volunteer mem = members.get(row);
			mem.setSalt(action);
		}
	}

	public void onCellEditMods(CellEditEvent event) {

		// Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null) {

			int row = event.getRowIndex();
			String action = (String) newValue;

			Volunteer mem = mods.get(row);
			mem.setSalt(action);
		}
	}

	public void onCellEditPending(CellEditEvent event) {

		// Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null) {

			int row = event.getRowIndex();
			String action = (String) newValue;

			Volunteer mem = pending.get(row);
			mem.setSalt(action);
		}
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<Volunteer> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Volunteer> admins) {
		this.admins = admins;
	}

	public List<Volunteer> getPending() {
		return pending;
	}

	public void setPending(List<Volunteer> pending) {
		this.pending = pending;
	}

	public boolean isAdminOrMod() {
		return adminOrMod;
	}

	public void setAdminOrMod(boolean adminOrMod) {
		this.adminOrMod = adminOrMod;
	}

	public boolean isMod() {
		return mod;
	}

	public void setMod(boolean mod) {
		this.mod = mod;
	}

	public String[] getActions() {
		return actions;
	}

	public String[] getActionsMods() {
		return actionsMods;
	}

	public String[] getActionsPending() {
		return actionsPending;
	}
}
