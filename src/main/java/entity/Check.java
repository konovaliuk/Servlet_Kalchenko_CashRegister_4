package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Check implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private int canceled;
	private Long creator;
	private Date crtime;
	private double discount;
	private double total;
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
}