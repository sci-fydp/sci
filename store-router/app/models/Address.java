package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Address extends Model {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	public String address;
	
	@ManyToOne
	@Required
	@JoinColumn(name="city_id", referencedColumnName="id")
	public City city;
	
	@Column(name="postal_code")
	public String postalCode;
	
	@ManyToOne
	@Required
	@JoinColumn(name="state_province_id", referencedColumnName="id")
	public StateProvince stateProvince;

	@ManyToOne
	@Required
	@JoinColumn(name="country_id", referencedColumnName="id")
	public Country country;
	
    public static Model.Finder<String, Address> find = new Model.Finder<String, Address>(String.class, Address.class);

}
