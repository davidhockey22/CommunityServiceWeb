package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.Services.GroupService;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.util.MemberLevel;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/group/{groupId}", to = "/Web/ViewGroup.xhtml")
public class ViewGroupBean {

	private String groupId;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;	

	private Group group = null;
	private GroupMember volGroupMember = null;

	private List<MemberLevel<GroupMember>> levels = null;

	boolean renderJoin;
	boolean renderPending;
	boolean renderLeave;

	public void fetchGroup() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		
		//test
		//groupId = "42";
		
		List<MemberLevel<GroupMember>> levels = new ArrayList<MemberLevel<GroupMember>>();
		
		renderJoin = true;
		renderPending = false;
		renderLeave = false;		
		
		try {
			this.group = GroupService.getGroupByIdWithAttachedEntities(Integer.parseInt(groupId), "GroupMembers");

			if (this.group != null) {

				List<GroupMember> admins = new ArrayList<GroupMember>();
				List<GroupMember> moderators = new ArrayList<GroupMember>();
				List<GroupMember> members = new ArrayList<GroupMember>();

				for (Iterator<GroupMember> iterator = this.group.getGroupMembers().iterator(); iterator.hasNext();) {
					GroupMember groupMember = (GroupMember) iterator.next();
					
					if(groupMember.getVolunteer().getVolunteerId() == currentVolunteer.getVolunteer().getVolunteerId()) {
						
						volGroupMember = groupMember;

						renderJoin = false;
						
						if( volGroupMember.getApproved() == false )
							renderPending = true;
						
						if(volGroupMember.getAdmin()) //the admin can not leave the group
							renderLeave = false;
						else
							renderLeave = true;						
					}
					
					if(groupMember.getApproved() != null && groupMember.getApproved() == false) {
						
						//do nothing if group member is pending approval
					}
					else if (groupMember.getAdmin()) {
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
	
	public String add() {

		volGroupMember = new GroupMember(group,
				currentVolunteer.getVolunteer(), new Date(), false, false);
		volGroupMember.setApproved(false);
		group.getGroupMembers().add(volGroupMember);
		GroupService.updateGroup(group);
		
		try {
			fetchGroup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	public String leave() {
		
		if(volGroupMember == null)
			return null;
		
		group.getGroupMembers().remove(volGroupMember);
		GroupService.updateGroup(group);
		
		try {
			fetchGroup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public List<MemberLevel<GroupMember>> getLevels() {
		return levels;
	}

	public void setLevels(List<MemberLevel<GroupMember>> levels) {
		this.levels = levels;
	}

	public boolean isRenderJoin() {
		return renderJoin;
	}

	public void setRenderJoin(boolean renderJoin) {
		this.renderJoin = renderJoin;
	}

	public boolean isRenderLeave() {
		return this.renderLeave;
	}

	public void setRenderLeave(boolean renderLeave) {
		this.renderLeave = renderLeave;
	}
	public boolean isRenderPending() {
		return renderPending;
	}

	public void setRenderPending(boolean renderPending) {
		this.renderPending = renderPending;
	}	
}