package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class City extends Model {
	
	private static final long serialVersionUID = 1L;

	@Id
	public int id;
	public String name;
	
    public City() {}
    
    public static Model.Finder<String, City> find = new Model.Finder<String, City>(String.class, City.class);

}
