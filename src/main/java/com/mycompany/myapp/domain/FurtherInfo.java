package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FurtherInfo.
 */
@Entity
@Table(name = "further_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FurtherInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "active_further_info", nullable = false)
    private Boolean activeFurtherInfo;

    
    @Lob
    @Column(name = "banner_picture", nullable = false)
    private byte[] bannerPicture;

    @Column(name = "banner_picture_content_type", nullable = false)
    private String bannerPictureContentType;

    @NotNull
    @Size(max = 40)
    @Column(name = "banner_name", length = 40, nullable = false)
    private String bannerName;

    @NotNull
    @Column(name = "header_one", nullable = false)
    private String headerOne;

    
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

    public Boolean isActiveFurtherInfo() {
        return activeFurtherInfo;
    }

    public FurtherInfo activeFurtherInfo(Boolean activeFurtherInfo) {
        this.activeFurtherInfo = activeFurtherInfo;
        return this;
    }

    public void setActiveFurtherInfo(Boolean activeFurtherInfo) {
        this.activeFurtherInfo = activeFurtherInfo;
    }

    public byte[] getBannerPicture() {
        return bannerPicture;
    }

    public FurtherInfo bannerPicture(byte[] bannerPicture) {
        this.bannerPicture = bannerPicture;
        return this;
    }

    public void setBannerPicture(byte[] bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    public String getBannerPictureContentType() {
        return bannerPictureContentType;
    }

    public FurtherInfo bannerPictureContentType(String bannerPictureContentType) {
        this.bannerPictureContentType = bannerPictureContentType;
        return this;
    }

    public void setBannerPictureContentType(String bannerPictureContentType) {
        this.bannerPictureContentType = bannerPictureContentType;
    }

    public String getBannerName() {
        return bannerName;
    }

    public FurtherInfo bannerName(String bannerName) {
        this.bannerName = bannerName;
        return this;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getHeaderOne() {
        return headerOne;
    }

    public FurtherInfo headerOne(String headerOne) {
        this.headerOne = headerOne;
        return this;
    }

    public void setHeaderOne(String headerOne) {
        this.headerOne = headerOne;
    }

    public String getDescriptionOne() {
        return descriptionOne;
    }

    public FurtherInfo descriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
        return this;
    }

    public void setDescriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
    }

    public byte[] getPictureOne() {
        return pictureOne;
    }

    public FurtherInfo pictureOne(byte[] pictureOne) {
        this.pictureOne = pictureOne;
        return this;
    }

    public void setPictureOne(byte[] pictureOne) {
        this.pictureOne = pictureOne;
    }

    public String getPictureOneContentType() {
        return pictureOneContentType;
    }

    public FurtherInfo pictureOneContentType(String pictureOneContentType) {
        this.pictureOneContentType = pictureOneContentType;
        return this;
    }

    public void setPictureOneContentType(String pictureOneContentType) {
        this.pictureOneContentType = pictureOneContentType;
    }

    public String getDescriptionTwo() {
        return descriptionTwo;
    }

    public FurtherInfo descriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
        return this;
    }

    public void setDescriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
    }

    public byte[] getPictureTwo() {
        return pictureTwo;
    }

    public FurtherInfo pictureTwo(byte[] pictureTwo) {
        this.pictureTwo = pictureTwo;
        return this;
    }

    public void setPictureTwo(byte[] pictureTwo) {
        this.pictureTwo = pictureTwo;
    }

    public String getPictureTwoContentType() {
        return pictureTwoContentType;
    }

    public FurtherInfo pictureTwoContentType(String pictureTwoContentType) {
        this.pictureTwoContentType = pictureTwoContentType;
        return this;
    }

    public void setPictureTwoContentType(String pictureTwoContentType) {
        this.pictureTwoContentType = pictureTwoContentType;
    }

    public String getDescriptionThree() {
        return descriptionThree;
    }

    public FurtherInfo descriptionThree(String descriptionThree) {
        this.descriptionThree = descriptionThree;
        return this;
    }

    public void setDescriptionThree(String descriptionThree) {
        this.descriptionThree = descriptionThree;
    }

    public byte[] getPictureThree() {
        return pictureThree;
    }

    public FurtherInfo pictureThree(byte[] pictureThree) {
        this.pictureThree = pictureThree;
        return this;
    }

    public void setPictureThree(byte[] pictureThree) {
        this.pictureThree = pictureThree;
    }

    public String getPictureThreeContentType() {
        return pictureThreeContentType;
    }

    public FurtherInfo pictureThreeContentType(String pictureThreeContentType) {
        this.pictureThreeContentType = pictureThreeContentType;
        return this;
    }

    public void setPictureThreeContentType(String pictureThreeContentType) {
        this.pictureThreeContentType = pictureThreeContentType;
    }

    public byte[] getBottomPicture() {
        return bottomPicture;
    }

    public FurtherInfo bottomPicture(byte[] bottomPicture) {
        this.bottomPicture = bottomPicture;
        return this;
    }

    public void setBottomPicture(byte[] bottomPicture) {
        this.bottomPicture = bottomPicture;
    }

    public String getBottomPictureContentType() {
        return bottomPictureContentType;
    }

    public FurtherInfo bottomPictureContentType(String bottomPictureContentType) {
        this.bottomPictureContentType = bottomPictureContentType;
        return this;
    }

    public void setBottomPictureContentType(String bottomPictureContentType) {
        this.bottomPictureContentType = bottomPictureContentType;
    }

    public String getBottomDescription() {
        return bottomDescription;
    }

    public FurtherInfo bottomDescription(String bottomDescription) {
        this.bottomDescription = bottomDescription;
        return this;
    }

    public void setBottomDescription(String bottomDescription) {
        this.bottomDescription = bottomDescription;
    }

    public String getButtonText() {
        return buttonText;
    }

    public FurtherInfo buttonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonURL() {
        return buttonURL;
    }

    public FurtherInfo buttonURL(String buttonURL) {
        this.buttonURL = buttonURL;
        return this;
    }

    public void setButtonURL(String buttonURL) {
        this.buttonURL = buttonURL;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public FurtherInfo createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public FurtherInfo createdDate(Instant createdDate) {
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
        if (!(o instanceof FurtherInfo)) {
            return false;
        }
        return id != null && id.equals(((FurtherInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FurtherInfo{" +
            "id=" + getId() +
            ", activeFurtherInfo='" + isActiveFurtherInfo() + "'" +
            ", bannerPicture='" + getBannerPicture() + "'" +
            ", bannerPictureContentType='" + getBannerPictureContentType() + "'" +
            ", bannerName='" + getBannerName() + "'" +
            ", headerOne='" + getHeaderOne() + "'" +
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
