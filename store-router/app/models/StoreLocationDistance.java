package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class StoreLocationDistance extends Model {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@Required
	@JoinColumn(name="store_location_a", referencedColumnName="id")
	public StoreLocation storeLocationA;
	
	@ManyToOne
	@Required
	@JoinColumn(name="store_location_b", referencedColumnName="id")
	public StoreLocation storeLocationB;
	
	@Column(name="distance_m")
	public Float distanceM;
	
    public static Model.Finder<String, StoreLocationDistance> find = new Model.Finder<String, StoreLocationDistance>(String.class, StoreLocationDistance.class);

}
