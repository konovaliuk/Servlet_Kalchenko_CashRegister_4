package entity;

import java.io.Serializable;

/**
 *
 * @author SergeyK
 */
public class UserType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String type;
    
    private String description;

    public UserType() {
    }

    public UserType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UserType other = (UserType) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    } 
}
