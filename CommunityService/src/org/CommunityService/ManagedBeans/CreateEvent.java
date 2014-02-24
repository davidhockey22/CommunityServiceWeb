package org.CommunityService.ManagedBeans;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.Services.EventService;

@ManagedBean
public class CreateEvent {

	private String eventname;

	private String description;

	private String location;

	private Date begintime = new Date();
	private Date endtime = new Date();

	private int recurrance; // TODO need to set up patterns

	private float hoursBonus = (float) 1.00; // TODO Should be an admin only
												// privilege;

	public String Create() throws Exception {
		Event event = new Event(eventname, begintime, endtime);
		event.setDescription(description);
		event.setLocation(location);
		EventService.addEvent(event);
		return "NewEvent";
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getRecurrance() {
		return recurrance;
	}

	public void setRecurrance(int recurrance) {
		this.recurrance = recurrance;
	}

	public float getHoursBonus() {
		return hoursBonus;
	}

	public void setHoursBonus(float hoursBonus) {
		this.hoursBonus = hoursBonus;
	}

}
