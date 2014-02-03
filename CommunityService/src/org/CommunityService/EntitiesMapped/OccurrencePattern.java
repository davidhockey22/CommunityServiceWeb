package org.CommunityService.EntitiesMapped;

// Generated Feb 3, 2014 2:50:59 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OccurrencePattern generated by hbm2java
 */
@Entity
@Table(name = "occurrencePattern", catalog = "dbAppData")
public class OccurrencePattern implements java.io.Serializable {

	private Integer occurrencePatternId;
	private String ocurName;
	private String pattern;

	public OccurrencePattern() {
	}

	public OccurrencePattern(String ocurName, String pattern) {
		this.ocurName = ocurName;
		this.pattern = pattern;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "occurrencePatternID", unique = true, nullable = false)
	public Integer getOccurrencePatternId() {
		return this.occurrencePatternId;
	}

	public void setOccurrencePatternId(Integer occurrencePatternId) {
		this.occurrencePatternId = occurrencePatternId;
	}

	@Column(name = "OcurName", nullable = false, length = 20)
	public String getOcurName() {
		return this.ocurName;
	}

	public void setOcurName(String ocurName) {
		this.ocurName = ocurName;
	}

	@Column(name = "Pattern", nullable = false, length = 45)
	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
