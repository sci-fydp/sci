package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class UserShoppingListItem extends Model {

	private static final long serialVersionUID = 1L;
	
	@Required
	@JoinColumn(name="shopping_list_id", referencedColumnName="id")
	public UserShoppingList shoppingList;
	
	@ManyToOne
	@JoinColumn(name="item_id", referencedColumnName="id")
	public Item item;
	
	@ManyToOne
	@JoinColumn(name="location_id", referencedColumnName="id")
	public StoreLocation location;
	
	public String name;
	
	public String description;
	
	public Float price;
    
    public static Model.Finder<String, UserShoppingListItem> find = new Model.Finder<String, UserShoppingListItem>(String.class, UserShoppingListItem.class);
}
