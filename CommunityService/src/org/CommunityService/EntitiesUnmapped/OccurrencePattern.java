package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

/**
 * OccurrencePattern generated by hbm2java
 */
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

	public Integer getOccurrencePatternId() {
		return this.occurrencePatternId;
	}

	public void setOccurrencePatternId(Integer occurrencePatternId) {
		this.occurrencePatternId = occurrencePatternId;
	}

	public String getOcurName() {
		return this.ocurName;
	}

	public void setOcurName(String ocurName) {
		this.ocurName = ocurName;
	}

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}