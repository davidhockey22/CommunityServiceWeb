package org.CommunityService.EntitiesMapped;

// Generated Feb 3, 2014 2:50:59 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SocialLinks generated by hbm2java
 */
@Entity
@Table(name = "SocialLinks", catalog = "dbAppData")
public class SocialLinks implements java.io.Serializable {

	private Integer socialId;
	private Volunteer volunteer;
	private String link;

	public SocialLinks() {
	}

	public SocialLinks(Volunteer volunteer, String link) {
		this.volunteer = volunteer;
		this.link = link;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SocialID", unique = true, nullable = false)
	public Integer getSocialId() {
		return this.socialId;
	}

	public void setSocialId(Integer socialId) {
		this.socialId = socialId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VolunteerID", nullable = false)
	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	@Column(name = "Link", nullable = false, length = 100)
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}