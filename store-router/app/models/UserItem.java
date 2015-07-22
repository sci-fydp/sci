package models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.base.DatedModel;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

public class UserItem extends DatedModel {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@Required
	@JoinColumn(name="user_id", referencedColumnName="id")
	public User user;
	
	@ManyToOne
	@JoinColumn(name="item_id", referencedColumnName="id")
	public Item item;
	
	@ManyToOne
	@JoinColumn(name="location_id", referencedColumnName="id")
	public StoreLocation location;
	
	public String name;
	
	public String description;
	
	public Float price;
    
    public static Model.Finder<String, UserItem> find = new Model.Finder<String, UserItem>(String.class, UserItem.class);
}
