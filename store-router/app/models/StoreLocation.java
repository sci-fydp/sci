package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.base.DatedModel;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class StoreLocation extends DatedModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	@ManyToOne
	@Required
	@JoinColumn(name="store_id", referencedColumnName="id")
	public Store store;
	
	@ManyToOne
	@Required
	@JoinColumn(name="address_id", referencedColumnName="id")
	public Address address;
	
	public Boolean active;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "sunday_begin_time")
	public Date sundayBeginTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "sunday_end_time")
	public Date sundayEndTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "monday_begin_time")
	public Date mondayBeginTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "monday_end_time")
	public Date mondayEndTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "tuesday_begin_time")
	public Date tuesdayBeginTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "tuesday_end_time")
	public Date tuesdayEndTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "wednesday_begin_time")
	public Date wednesdayBeginTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "wednesday_end_time")
	public Date wednesdayEndTime;

	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "thursday_begin_time")
	public Date thursdayBeginTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "thursday_end_time")
	public Date thursdayEndTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "friday_begin_time")
	public Date fridayBeginTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "friday_end_time")
	public Date fridayEndTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "saturday_begin_time")
	public Date saturdayBeginTime;
	
	@Formats.DateTime(pattern="HH:mm")
	@Column(name = "saturday_end_time")
	public Date saturdayEndTime;
	
    public static Model.Finder<String, StoreLocation> find = new Model.Finder<String, StoreLocation>(String.class, StoreLocation.class);

}
