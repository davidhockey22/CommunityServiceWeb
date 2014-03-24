package org.CommunityService.ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Join(path = "/schedule", to = "/Web/MySchedule.xhtml")
@ManagedBean
@RequestScoped
public class MyScheduleBean implements Serializable {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	@PostConstruct
	public void postConstructor() {
		List<EventVolunteer> events = VolunteerService.getEventVolunteersByVolunteer(currentVolunteer.getVolunteer());
		List<ScheduleEvent> scheduleEvents = new ArrayList<ScheduleEvent>();
		for (Iterator<EventVolunteer> iterator = events.iterator(); iterator.hasNext();) {
			EventVolunteer eventVolunteer = (EventVolunteer) iterator.next();
			System.out.println(eventVolunteer.getEvent().getEventName());
			scheduleEvents.add(new MyScheduleEvent(eventVolunteer));
		}
		eventModel = new DefaultScheduleModel(scheduleEvents);
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}
}

class MyScheduleEvent extends DefaultScheduleEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EventVolunteer eventVolunteer;
	boolean allDay;

	public MyScheduleEvent(EventVolunteer eventVolunteer) {
		super();
		this.eventVolunteer = eventVolunteer;
		Event event = eventVolunteer.getEvent();
		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
		if (event.getBeginTime().getTime() % MILLIS_PER_DAY == 0 && event.getEndTime().getTime() % MILLIS_PER_DAY == 0
				&& event.getBeginTime().compareTo(event.getEndTime()) != 0)
			allDay = true;
		else
			allDay = false;
	}

	@Override
	public Object getData() {
		return eventVolunteer;
	}

	@Override
	public Date getEndDate() {
		return eventVolunteer.getEvent().getEndTime();
	}

	@Override
	public Date getStartDate() {
		return eventVolunteer.getEvent().getBeginTime();
	}

	@Override
	public String getTitle() {
		return eventVolunteer.getEvent().getEventName();
	}

	@Override
	public boolean isAllDay() {
		return allDay;
	}

	@Override
	public boolean isEditable() {
		return false;
	}

}