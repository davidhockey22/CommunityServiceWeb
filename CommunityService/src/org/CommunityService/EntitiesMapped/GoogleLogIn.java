package org.CommunityService.EntitiesMapped;

// Generated Oct 21, 2013 2:07:36 PM by Hibernate Tools 3.4.0.CR1

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
 * GoogleLogIn generated by hbm2java
 */
@Entity
@Table(name = "GoogleLogIn", catalog = "dbAppData")
public class GoogleLogIn implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1340727480996179633L;
    private Integer googleLogInId;
    private Volunteer volunteer;
    private String token;

    public GoogleLogIn() {
    }

    public GoogleLogIn(Volunteer volunteer, String token) {
	this.volunteer = volunteer;
	this.token = token;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "GoogleLogInID", unique = true, nullable = false)
    public Integer getGoogleLogInId() {
	return this.googleLogInId;
    }

    public void setGoogleLogInId(Integer googleLogInId) {
	this.googleLogInId = googleLogInId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VolunteerID", nullable = false)
    public Volunteer getVolunteer() {
	return this.volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
	this.volunteer = volunteer;
    }

    @Column(name = "Token", nullable = false, length = 45)
    public String getToken() {
	return this.token;
    }

    public void setToken(String token) {
	this.token = token;
    }

}
