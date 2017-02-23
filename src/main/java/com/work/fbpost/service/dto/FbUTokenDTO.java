package com.work.fbpost.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FbUToken entity.
 */
public class FbUTokenDTO implements Serializable {

    private Long id;

    private String userid;

    private String uToken;

    private String createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getuToken() {
        return uToken;
    }

    public void setuToken(String uToken) {
        this.uToken = uToken;
    }
    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FbUTokenDTO fbUTokenDTO = (FbUTokenDTO) o;

        if ( ! Objects.equals(id, fbUTokenDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FbUTokenDTO{" +
            "id=" + id +
            ", userid='" + userid + "'" +
            ", uToken='" + uToken + "'" +
            ", createdOn='" + createdOn + "'" +
            '}';
    }
}
