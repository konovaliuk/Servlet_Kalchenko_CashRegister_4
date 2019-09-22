package entity;

import java.io.Serializable;

public class Checkspec implements Serializable {
	private static final long serialVersionUID = 1L;

	private int canceled;
	private Long id;
	private Long idCheck;
	private Long idGood;
	private int nds;
	private double ndstotal;
	private double price;
	private double quant;
	private double total;
	private int xcode;
	private String xname;
	private Check check;
	
	public Checkspec() {
	}

	public int getCanceled() {
		return this.canceled;
	}

	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCheck() {
		return this.idCheck;
	}

	public void setIdCheck(Long idCheck) {
		this.idCheck = idCheck;
	}

	public Long getIdGood() {
		return this.idGood;
	}

	public void setIdGood(Long idGood) {
		this.idGood = idGood;
	}

	public int getNds() {
		return this.nds;
	}

	public void setNds(int nds) {
		this.nds = nds;
	}

	public double getNdstotal() {
		return this.ndstotal;
	}

	public void setNdstotal(double ndstotal) {
		this.ndstotal = ndstotal;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getQuant() {
		return this.quant;
	}

	public void setQuant(double quant) {
		this.quant = quant;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public Check getCheck() {
		return this.check;
	}

	public void setCheck(Check check) {
		this.check = check;
	}
	
	public int getXcode() {
		return this.xcode;
	}

	public void setXcode(int xcode) {
		this.xcode = xcode;
	}

	public String getXname() {
		return this.xname;
	}

	public void setXname(String xname) {
		this.xname = xname;
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
		Checkspec other = (Checkspec) obj;
		if (id != other.id)
			return false;
		return true;
	}
}