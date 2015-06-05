package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.base.DatedModel;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Store extends DatedModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	public String name;
	
	@ManyToOne
	@Required
	@JoinColumn(name="inventory_category_id", referencedColumnName="id")
	public InventoryCategory inventoryCategory;
	
	public Boolean active;
    
    public static Model.Finder<String, Store> find = new Model.Finder<String, Store>(String.class, Store.class);

}
