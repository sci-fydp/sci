package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Holiday extends Model {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	public String name;
	
	@Column(name="month_of_year")
	public int monthOfYear;

	@Column(name="day_of_month")
	public int dayOfMonth;
	
	@Column(name="day_of_week")
	public int dayOfWeek;
	
	@Column(name="iteration_of_week")
	public int iterationOfWeek;
    
    public static Model.Finder<String, Holiday> find = new Model.Finder<String, Holiday>(String.class, Holiday.class);

}
