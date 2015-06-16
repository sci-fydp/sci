package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class InventoryCategory extends Model {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	public String name;
	
    public static Model.Finder<String, InventoryCategory> find = new Model.Finder<String, InventoryCategory>(String.class, InventoryCategory.class);

}
