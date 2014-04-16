package org.CommunityService.ManagedBeans;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ApplicationScoped
@Join(path = "/volunteerleaderboards", to = "/Web/LeaderboardPoints.xhtml")
public class LeaderboardBean {

	static Date lastChecked;
	static Date lastChecked2;
	private List<Volunteer> results = null;
	private List<Volunteer> partial = null;

	public List<Volunteer> getResults() {
		Date currentTime = new Date();
		if (lastChecked == null || lastChecked.before(currentTime)) {
			// don't check for 5 minutes
			lastChecked = (Date) currentTime.clone();
			lastChecked.setMinutes(lastChecked.getMinutes() + 10);
			results = VolunteerService.getLeaderboardByPoints();
			System.out.println("New full leaderboard.");
		}
		return results;
	}

	public void setResults(List<Volunteer> results) {
		this.results = results;
	}

	public List<Volunteer> getPartial() {
		Date currentTime = new Date();
		if ((lastChecked == null || lastChecked.before(currentTime)) && results != null) {
			partial = results.subList(0, 100);
		} else if (lastChecked2 == null || lastChecked2.before(currentTime)) {
			// don't check for 5 minutes
			lastChecked2 = (Date) currentTime.clone();
			lastChecked2.setMinutes(lastChecked2.getMinutes() + 10);
			partial = VolunteerService.getLeaderboardByPoints(100);
			System.out.println("New partial leaderboard.");
		}
		return partial;
	}

	public void setPartial(List<Volunteer> partial) {
		this.partial = partial;
	}
}
