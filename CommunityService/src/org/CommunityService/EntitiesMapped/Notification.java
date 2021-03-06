package org.CommunityService.EntitiesMapped;

// Generated Feb 22, 2014 8:45:42 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Notification generated by hbm2java
 */
@Entity
@Table(name = "Notification", catalog = "volunteerMeData")
public class Notification implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3992575582339276397L;
	private Integer notificationId;
	private String description;
	private Volunteer volunteer;

	private String category;
	private Date creation;
	private Date dependenceDate;

	public Notification() {
	}

	public Notification(String description) {
		this.description = description;
	}

	public Notification(String description, String category, Date creation, Date dependenceDate) {
		this.description = description;
		this.category = category;
		this.creation = creation;
		this.dependenceDate = dependenceDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "NotificationID", unique = true, nullable = false)
	public Integer getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	@Column(name = "Description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volunteerId", nullable = false)
	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	@Column(name = "Category", length = 45)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Creation", length = 19, insertable=false)
	public Date getCreation() {
		return this.creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DependenceDate", length = 19)
	public Date getDependenceDate() {
		return this.dependenceDate;
	}

	public void setDependenceDate(Date dependenceDate) {
		this.dependenceDate = dependenceDate;
	}

}
