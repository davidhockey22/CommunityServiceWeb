package org.CommunityService.ManagedBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Join(path = "/schedule", to = "/Web/MySchedule.xhtml")
@ManagedBean
@SessionScoped
public class MyScheduleBean implements Serializable {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean login;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScheduleModel model;

	private ScheduleEvent currentEvent = new DefaultScheduleEvent("", new Date(), new Date(), null);

	@PostConstruct
	public void postConstructor() {
		model = new LazyScheduleModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override 
			public void loadEvents(Date start, Date end) {
				List<EventVolunteer> events = VolunteerService.getEventVolunteersByVolunteer(login.getVolunteer());
				for (Iterator<EventVolunteer> iterator = events.iterator(); iterator.hasNext();) {
					EventVolunteer eventVolunteer = (EventVolunteer) iterator.next();
					DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent();
					scheduleEvent.setData(eventVolunteer);
					scheduleEvent.setStartDate(eventVolunteer.getEvent().getBeginTime());
					scheduleEvent.setEndDate(eventVolunteer.getEvent().getEndTime());
					scheduleEvent.setTitle(eventVolunteer.getEvent().getEventName().replace("'", "\\'"));
					scheduleEvent.setAllDay(false);
					scheduleEvent.setEditable(false);
					this.addEvent(scheduleEvent);
				}
			}
		};
	}

	public LoginBean getLogin() {
		return login;
	}

	public void setLogin(LoginBean login) {
		this.login = login;
	}

	public ScheduleModel getEventModel() {
		return model;
	}

	public EventVolunteer getEventVolunteer() {
		return (EventVolunteer) currentEvent.getData();
	}
	
	public ScheduleEvent getEvent() {
		return currentEvent;
	}

	public void setEvent(ScheduleEvent event) {
		this.currentEvent = event;
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
		currentEvent = (ScheduleEvent) selectEvent.getObject();
	}
}