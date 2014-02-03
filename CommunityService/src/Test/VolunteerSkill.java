package Test;

// Generated Feb 3, 2014 2:14:25 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * VolunteerSkill generated by hbm2java
 */
@Entity
@Table(name = "Volunteer_Skill", catalog = "dbAppData")
public class VolunteerSkill implements java.io.Serializable {

	private VolunteerSkillId id;
	private Skill skill;
	private Volunteer volunteer;
	private byte certified;
	private String certifiedFrom;

	public VolunteerSkill() {
	}

	public VolunteerSkill(VolunteerSkillId id, Skill skill,
			Volunteer volunteer, byte certified) {
		this.id = id;
		this.skill = skill;
		this.volunteer = volunteer;
		this.certified = certified;
	}

	public VolunteerSkill(VolunteerSkillId id, Skill skill,
			Volunteer volunteer, byte certified, String certifiedFrom) {
		this.id = id;
		this.skill = skill;
		this.volunteer = volunteer;
		this.certified = certified;
		this.certifiedFrom = certifiedFrom;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "skillId", column = @Column(name = "SkillID", nullable = false)),
			@AttributeOverride(name = "volunteerId", column = @Column(name = "VolunteerID", nullable = false)) })
	public VolunteerSkillId getId() {
		return this.id;
	}

	public void setId(VolunteerSkillId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SkillID", nullable = false, insertable = false, updatable = false)
	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VolunteerID", nullable = false, insertable = false, updatable = false)
	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	@Column(name = "Certified", nullable = false)
	public byte getCertified() {
		return this.certified;
	}

	public void setCertified(byte certified) {
		this.certified = certified;
	}

	@Column(name = "CertifiedFrom", length = 45)
	public String getCertifiedFrom() {
		return this.certifiedFrom;
	}

	public void setCertifiedFrom(String certifiedFrom) {
		this.certifiedFrom = certifiedFrom;
	}

}
