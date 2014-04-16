package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.List;

import org.CommunityService.EntitiesMapped.Notification;
import org.CommunityService.EntitiesMapped.Volunteer;

public class NotificationService {

	public static List<Notification> getUserNotifications(Volunteer v) {
		String hql = "select v.notifications from Volunteer as v where v.volunteerId=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(String.valueOf(v.getVolunteerId()));
		List<Notification> nots = ((List<Notification>) DBConnection.query(hql, params));
		return nots;
	}

	public static void createNotification(Volunteer v, String desc, String category) {
		Notification n = new Notification();
		n.setCategory(category);
		n.setDescription(desc);
		n.setVolunteer(v);
		DBConnection.save(n);
	}

}
