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
 * FacebookLogIn generated by hbm2java
 */
@Entity
@Table(name = "FacebookLogIn", catalog = "dbAppData")
public class FacebookLogIn implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3719461314557395992L;
    private Integer facebookLogInId;
    private Volunteer volunteer;
    private String token;

    public FacebookLogIn() {
    }

    public FacebookLogIn(Volunteer volunteer, String token) {
	this.volunteer = volunteer;
	this.token = token;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "FacebookLogInID", unique = true, nullable = false)
    public Integer getFacebookLogInId() {
	return this.facebookLogInId;
    }

    public void setFacebookLogInId(Integer facebookLogInId) {
	this.facebookLogInId = facebookLogInId;
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
