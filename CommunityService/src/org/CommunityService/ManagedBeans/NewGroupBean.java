package org.CommunityService.ManagedBeans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@SessionScoped
@Join(path = "/createGroup", to = "/Web/NewGroup.xhtml")
public class NewGroupBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String name;

//	private int newGroupId;
	
	public String Register() {

		Group group = null;
		try {
			
			group = new Group(name);
			group.setCreationDate(new Date());
			group.setAvgRatingOfVolunteers(0.0f);
			group.setHoursWorked(0);
			group.setPoints(0.0f);
			Set<GroupMember> members = new HashSet<GroupMember>();
			GroupMember founder = new GroupMember(group, currentVolunteer.getVolunteer(), new Date(), true, true);
			members.add(founder);
			group.setGroupMembers(members);
			
			GroupService.addGroup(group);
			
			/*
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			Group group = new Group(name);
			group.setHoursWorked(0);
			group.setPoints(0.0f);
			group.setAvgRatingOfVolunteers(0.0f);
			group.setCreationDate(new Date());
			
			session.persist(group);
						
			Volunteer v = (Volunteer)session.merge(currentVolunteer.getVolunteer());
			GroupMember founder = new GroupMember(group, v);
			founder.setAdmin(true);
			founder.setMod(true);
			
			session.persist(founder);
			
			session.getTransaction().commit();
			session.close();
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}

		return "/EditGroup.xhtml?faces-redirect=true&groupId=" + group.getGroupId();
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
