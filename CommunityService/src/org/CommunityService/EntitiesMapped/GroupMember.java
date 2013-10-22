package org.CommunityService.EntitiesMapped;

// Generated Oct 21, 2013 2:07:36 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * GroupMember generated by hbm2java
 */
@Entity
@Table(name = "GroupMember", catalog = "dbAppData")
public class GroupMember implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7287668326221680360L;
    private Integer groupMemberId;
    private Group group;
    private Volunteer volunteer;
    private Date joinedDate;

    public GroupMember() {
    }

    public GroupMember(Group group, Volunteer volunteer, Date joinedDate) {
	this.group = group;
	this.volunteer = volunteer;
	this.joinedDate = joinedDate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "GroupMemberID", unique = true, nullable = false)
    public Integer getGroupMemberId() {
	return this.groupMemberId;
    }

    public void setGroupMemberId(Integer groupMemberId) {
	this.groupMemberId = groupMemberId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GroupID", nullable = false)
    public Group getGroup() {
	return this.group;
    }

    public void setGroup(Group group) {
	this.group = group;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VolunteerID", nullable = false)
    public Volunteer getVolunteer() {
	return this.volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
	this.volunteer = volunteer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JoinedDate", nullable = false, length = 19)
    public Date getJoinedDate() {
	return this.joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
	this.joinedDate = joinedDate;
    }

}
