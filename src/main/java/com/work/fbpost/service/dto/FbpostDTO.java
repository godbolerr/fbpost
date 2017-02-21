package com.work.fbpost.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Fbpost entity.
 */
public class FbpostDTO implements Serializable {

    private Long id;

    private String url;

    private String description;

    private String name;

    private String caption;

    private String place;

    private String createdOn;

    private String createdBy;

    private String objectId;

    private String privacy;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FbpostDTO fbpostDTO = (FbpostDTO) o;

        if ( ! Objects.equals(id, fbpostDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FbpostDTO{" +
            "id=" + id +
            ", url='" + url + "'" +
            ", description='" + description + "'" +
            ", name='" + name + "'" +
            ", caption='" + caption + "'" +
            ", place='" + place + "'" +
            ", createdOn='" + createdOn + "'" +
            ", createdBy='" + createdBy + "'" +
            ", objectId='" + objectId + "'" +
            ", privacy='" + privacy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
