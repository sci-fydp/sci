package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class LocationItem extends Model {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@Required
	@JoinColumn(name="location_id", referencedColumnName="id")
	public StoreLocation storeLocation;
	
	@ManyToOne
	@Required
	@JoinColumn(name="item_id", referencedColumnName="id")
	public Item item;
	
	public Float price;
	
	@Column(name="stock_count")
	public int stockCount;
    
    public static Model.Finder<String, LocationItem> find = new Model.Finder<String, LocationItem>(String.class, LocationItem.class);

}
