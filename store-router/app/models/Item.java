package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.base.DatedModel;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Item extends DatedModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	public String name;
	
	public String description;
	
	@ManyToOne
	@Required
	@JoinColumn(name="category_id", referencedColumnName="id")
	public InventoryCategory inventoryCategory;
	
    public static Model.Finder<String, Item> find = new Model.Finder<String, Item>(String.class, Item.class);

}
