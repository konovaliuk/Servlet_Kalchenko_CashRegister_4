package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
*
* @author SergeyK
*/
public class Check implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private int canceled;
	private Long creator;
	private Date crtime;
	private double discount;
	private double total;
	private Integer registration;
	private List<Checkspec> checkspecs;

	public Check() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCanceled() {
		return this.canceled;
	}

	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}

	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCrtime() {
		return this.crtime;
	}

	public void setCrtime(Date crtime) {
		this.crtime = crtime;
	}

	public double getDiscount() {
		return this.discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}	

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

	public List<Checkspec> getCheckspecs() {
		return this.checkspecs;
	}

	public void setCheckspecs(List<Checkspec> checkspecs) {
		this.checkspecs = checkspecs;
	}

	public Checkspec addCheckspec(Checkspec checkspec) {
		getCheckspecs().add(checkspec);
		checkspec.setCheck(this);

		return checkspec;
	}

	public Checkspec removeCheckspec(Checkspec checkspec) {
		getCheckspecs().remove(checkspec);
		checkspec.setCheck(null);

		return checkspec;
	}
	
	@Override
	public int hashCode() {
        int hash = 31;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Check other = (Check) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
		return true;
	}
}