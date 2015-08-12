package models.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.DateTime;

import play.data.format.Formats;
import play.db.ebean.Model;

@MappedSuperclass
public abstract class DatedModel extends Model {

	private static final long serialVersionUID = 1L;

	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "creation_date")
	public DateTime creationDate;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "modify_date")
	public DateTime modifyDate;
	
	@Override
    public void save() {
    	createdAt();
    	super.save();
    }
    
    @Override
    public void update() {
    	updatedAt();
    	super.update();
    }
    
    @PrePersist
    void createdAt() {
    	creationDate = modifyDate = new DateTime();
    }
   
    @PreUpdate
    void updatedAt() {
    	modifyDate = new DateTime();
    }
}
