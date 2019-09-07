package entity;

import java.io.Serializable;

/**
 * @author SergeyK
 */
public class Good implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private int code;
	private String name;	
	private String measure;	
	private double quant;
	private String comments;

	public Good() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeasure() {
		return this.measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}


	public double getQuant() {
		return this.quant;
	}

	public void setQuant(double quant) {
		this.quant = quant;
	}
	
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
    @Override
    public String toString() {
        return "good[ id=" + id + ", code=" + code + ", name=" + name + ", measure=" + measure + ", comments=" + comments + " ]";
    }
}
