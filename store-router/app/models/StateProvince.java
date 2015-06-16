package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class StateProvince extends Model {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	public String name;
	
    public static Model.Finder<String, StateProvince> find = new Model.Finder<String, StateProvince>(String.class, StateProvince.class);

}
