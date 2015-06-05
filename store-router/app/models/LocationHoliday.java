package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class LocationHoliday extends Model {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@Required
	@JoinColumn(name="location_id", referencedColumnName="id")
	public StoreLocation location;
	
	@ManyToOne
	@Required
	@JoinColumn(name="holiday_id", referencedColumnName="id")
	public Holiday holiday;
    
    public static Model.Finder<String, LocationHoliday> find = new Model.Finder<String, LocationHoliday>(String.class, LocationHoliday.class);

}
