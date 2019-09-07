package entity;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class UserType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUserType;

    private String type;
    
    private String description;

    public UserType() {
    }

    public UserType(Long idUserType) {
        this.idUserType = idUserType;
    }

    public Long getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(Long idUserType) {
        this.idUserType = idUserType;
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
    public boolean equals(Object object) {
        if (!(object instanceof UserType)) {
            return false;
        }
        UserType other = (UserType) object;
        if ((this.idUserType == null && other.idUserType != null) || (this.idUserType != null && !this.idUserType.equals(other.idUserType))) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserType != null ? idUserType.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString() {
        return "UserType[ idUserType=" + idUserType + " ]";
    }
    
}
