package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A News.
 */
@Entity
@Table(name = "news")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "active_message", nullable = false)
    private Boolean activeMessage;

    
    @Lob
    @Column(name = "banner_picture", nullable = false)
    private byte[] bannerPicture;

    @Column(name = "banner_picture_content_type", nullable = false)
    private String bannerPictureContentType;

    @Column(name = "date_of_news_creation")
    private Instant dateOfNewsCreation;

    @NotNull
    @Column(name = "banner_name", nullable = false)
    private String bannerName;

    
    @Lob
    @Column(name = "description_one", nullable = false)
    private String descriptionOne;

    
    @Lob
    @Column(name = "picture_one", nullable = false)
    private byte[] pictureOne;

    @Column(name = "picture_one_content_type", nullable = false)
    private String pictureOneContentType;

    @Lob
    @Column(name = "description_two")
    private String descriptionTwo;

    @Lob
    @Column(name = "picture_two")
    private byte[] pictureTwo;

    @Column(name = "picture_two_content_type")
    private String pictureTwoContentType;

    @Lob
    @Column(name = "description_three")
    private String descriptionThree;

    @Lob
    @Column(name = "picture_three")
    private byte[] pictureThree;

    @Column(name = "picture_three_content_type")
    private String pictureThreeContentType;

    
    @Lob
    @Column(name = "bottom_picture", nullable = false)
    private byte[] bottomPicture;

    @Column(name = "bottom_picture_content_type", nullable = false)
    private String bottomPictureContentType;

    @NotNull
    @Column(name = "bottom_description", nullable = false)
    private String bottomDescription;

    @Column(name = "button_text")
    private String buttonText;

    @Column(name = "button_url")
    private String buttonURL;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActiveMessage() {
        return activeMessage;
    }

    public News activeMessage(Boolean activeMessage) {
        this.activeMessage = activeMessage;
        return this;
    }

    public void setActiveMessage(Boolean activeMessage) {
        this.activeMessage = activeMessage;
    }

    public byte[] getBannerPicture() {
        return bannerPicture;
    }

    public News bannerPicture(byte[] bannerPicture) {
        this.bannerPicture = bannerPicture;
        return this;
    }

    public void setBannerPicture(byte[] bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    public String getBannerPictureContentType() {
        return bannerPictureContentType;
    }

    public News bannerPictureContentType(String bannerPictureContentType) {
        this.bannerPictureContentType = bannerPictureContentType;
        return this;
    }

    public void setBannerPictureContentType(String bannerPictureContentType) {
        this.bannerPictureContentType = bannerPictureContentType;
    }

    public Instant getDateOfNewsCreation() {
        return dateOfNewsCreation;
    }

    public News dateOfNewsCreation(Instant dateOfNewsCreation) {
        this.dateOfNewsCreation = dateOfNewsCreation;
        return this;
    }

    public void setDateOfNewsCreation(Instant dateOfNewsCreation) {
        this.dateOfNewsCreation = dateOfNewsCreation;
    }

    public String getBannerName() {
        return bannerName;
    }

    public News bannerName(String bannerName) {
        this.bannerName = bannerName;
        return this;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getDescriptionOne() {
        return descriptionOne;
    }

    public News descriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
        return this;
    }

    public void setDescriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
    }

    public byte[] getPictureOne() {
        return pictureOne;
    }

    public News pictureOne(byte[] pictureOne) {
        this.pictureOne = pictureOne;
        return this;
    }

    public void setPictureOne(byte[] pictureOne) {
        this.pictureOne = pictureOne;
    }

    public String getPictureOneContentType() {
        return pictureOneContentType;
    }

    public News pictureOneContentType(String pictureOneContentType) {
        this.pictureOneContentType = pictureOneContentType;
        return this;
    }

    public void setPictureOneContentType(String pictureOneContentType) {
        this.pictureOneContentType = pictureOneContentType;
    }

    public String getDescriptionTwo() {
        return descriptionTwo;
    }

    public News descriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
        return this;
    }

    public void setDescriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
    }

    public byte[] getPictureTwo() {
        return pictureTwo;
    }

    public News pictureTwo(byte[] pictureTwo) {
        this.pictureTwo = pictureTwo;
        return this;
    }

    public void setPictureTwo(byte[] pictureTwo) {
        this.pictureTwo = pictureTwo;
    }

    public String getPictureTwoContentType() {
        return pictureTwoContentType;
    }

    public News pictureTwoContentType(String pictureTwoContentType) {
        this.pictureTwoContentType = pictureTwoContentType;
        return this;
    }

    public void setPictureTwoContentType(String pictureTwoContentType) {
        this.pictureTwoContentType = pictureTwoContentType;
    }

    public String getDescriptionThree() {
        return descriptionThree;
    }

    public News descriptionThree(String descriptionThree) {
        this.descriptionThree = descriptionThree;
        return this;
    }

    public void setDescriptionThree(String descriptionThree) {
        this.descriptionThree = descriptionThree;
    }

    public byte[] getPictureThree() {
        return pictureThree;
    }

    public News pictureThree(byte[] pictureThree) {
        this.pictureThree = pictureThree;
        return this;
    }

    public void setPictureThree(byte[] pictureThree) {
        this.pictureThree = pictureThree;
    }

    public String getPictureThreeContentType() {
        return pictureThreeContentType;
    }

    public News pictureThreeContentType(String pictureThreeContentType) {
        this.pictureThreeContentType = pictureThreeContentType;
        return this;
    }

    public void setPictureThreeContentType(String pictureThreeContentType) {
        this.pictureThreeContentType = pictureThreeContentType;
    }

    public byte[] getBottomPicture() {
        return bottomPicture;
    }

    public News bottomPicture(byte[] bottomPicture) {
        this.bottomPicture = bottomPicture;
        return this;
    }

    public void setBottomPicture(byte[] bottomPicture) {
        this.bottomPicture = bottomPicture;
    }

    public String getBottomPictureContentType() {
        return bottomPictureContentType;
    }

    public News bottomPictureContentType(String bottomPictureContentType) {
        this.bottomPictureContentType = bottomPictureContentType;
        return this;
    }

    public void setBottomPictureContentType(String bottomPictureContentType) {
        this.bottomPictureContentType = bottomPictureContentType;
    }

    public String getBottomDescription() {
        return bottomDescription;
    }

    public News bottomDescription(String bottomDescription) {
        this.bottomDescription = bottomDescription;
        return this;
    }

    public void setBottomDescription(String bottomDescription) {
        this.bottomDescription = bottomDescription;
    }

    public String getButtonText() {
        return buttonText;
    }

    public News buttonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonURL() {
        return buttonURL;
    }

    public News buttonURL(String buttonURL) {
        this.buttonURL = buttonURL;
        return this;
    }

    public void setButtonURL(String buttonURL) {
        this.buttonURL = buttonURL;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public News createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public News createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof News)) {
            return false;
        }
        return id != null && id.equals(((News) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "News{" +
            "id=" + getId() +
            ", activeMessage='" + isActiveMessage() + "'" +
            ", bannerPicture='" + getBannerPicture() + "'" +
            ", bannerPictureContentType='" + getBannerPictureContentType() + "'" +
            ", dateOfNewsCreation='" + getDateOfNewsCreation() + "'" +
            ", bannerName='" + getBannerName() + "'" +
            ", descriptionOne='" + getDescriptionOne() + "'" +
            ", pictureOne='" + getPictureOne() + "'" +
            ", pictureOneContentType='" + getPictureOneContentType() + "'" +
            ", descriptionTwo='" + getDescriptionTwo() + "'" +
            ", pictureTwo='" + getPictureTwo() + "'" +
            ", pictureTwoContentType='" + getPictureTwoContentType() + "'" +
            ", descriptionThree='" + getDescriptionThree() + "'" +
            ", pictureThree='" + getPictureThree() + "'" +
            ", pictureThreeContentType='" + getPictureThreeContentType() + "'" +
            ", bottomPicture='" + getBottomPicture() + "'" +
            ", bottomPictureContentType='" + getBottomPictureContentType() + "'" +
            ", bottomDescription='" + getBottomDescription() + "'" +
            ", buttonText='" + getButtonText() + "'" +
            ", buttonURL='" + getButtonURL() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
