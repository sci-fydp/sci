package models;

import javax.persistence.Column;

import models.base.DatedModel;
import play.db.ebean.Model;

public class UserShoppingList extends DatedModel {

	private static final long serialVersionUID = 1L;

	public int id;
	
	@Column(name="user_id")
	public int userId;
	
	public String name;
	
    public static Model.Finder<String, UserShoppingList> find = new Model.Finder<String, UserShoppingList>(String.class, UserShoppingList.class);
}
