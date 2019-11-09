package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

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

    @NotNull
    @Column(name = "banner_name", nullable = false)
    private String bannerName;

    @NotNull
    @Size(max = 40)
    @Column(name = "heading_one", length = 40, nullable = false)
    private String headingOne;

    
    @Lob
    @Column(name = "description_one", nullable = false)
    private String descriptionOne;

    @Lob
    @Column(name = "quote_picture")
    private byte[] quotePicture;

    @Column(name = "quote_picture_content_type")
    private String quotePictureContentType;

    @Column(name = "quote_content")
    private String quoteContent;

    @Column(name = "header_two")
    private String headerTwo;

    @Lob
    @Column(name = "description_two")
    private String descriptionTwo;

    
    @Lob
    @Column(name = "bottom_picture", nullable = false)
    private byte[] bottomPicture;

    @Column(name = "bottom_picture_content_type", nullable = false)
    private String bottomPictureContentType;

    
    @Lob
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

    public Message activeMessage(Boolean activeMessage) {
        this.activeMessage = activeMessage;
        return this;
    }

    public void setActiveMessage(Boolean activeMessage) {
        this.activeMessage = activeMessage;
    }

    public byte[] getBannerPicture() {
        return bannerPicture;
    }

    public Message bannerPicture(byte[] bannerPicture) {
        this.bannerPicture = bannerPicture;
        return this;
    }

    public void setBannerPicture(byte[] bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    public String getBannerPictureContentType() {
        return bannerPictureContentType;
    }

    public Message bannerPictureContentType(String bannerPictureContentType) {
        this.bannerPictureContentType = bannerPictureContentType;
        return this;
    }

    public void setBannerPictureContentType(String bannerPictureContentType) {
        this.bannerPictureContentType = bannerPictureContentType;
    }

    public String getBannerName() {
        return bannerName;
    }

    public Message bannerName(String bannerName) {
        this.bannerName = bannerName;
        return this;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getHeadingOne() {
        return headingOne;
    }

    public Message headingOne(String headingOne) {
        this.headingOne = headingOne;
        return this;
    }

    public void setHeadingOne(String headingOne) {
        this.headingOne = headingOne;
    }

    public String getDescriptionOne() {
        return descriptionOne;
    }

    public Message descriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
        return this;
    }

    public void setDescriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
    }

    public byte[] getQuotePicture() {
        return quotePicture;
    }

    public Message quotePicture(byte[] quotePicture) {
        this.quotePicture = quotePicture;
        return this;
    }

    public void setQuotePicture(byte[] quotePicture) {
        this.quotePicture = quotePicture;
    }

    public String getQuotePictureContentType() {
        return quotePictureContentType;
    }

    public Message quotePictureContentType(String quotePictureContentType) {
        this.quotePictureContentType = quotePictureContentType;
        return this;
    }

    public void setQuotePictureContentType(String quotePictureContentType) {
        this.quotePictureContentType = quotePictureContentType;
    }

    public String getQuoteContent() {
        return quoteContent;
    }

    public Message quoteContent(String quoteContent) {
        this.quoteContent = quoteContent;
        return this;
    }

    public void setQuoteContent(String quoteContent) {
        this.quoteContent = quoteContent;
    }

    public String getHeaderTwo() {
        return headerTwo;
    }

    public Message headerTwo(String headerTwo) {
        this.headerTwo = headerTwo;
        return this;
    }

    public void setHeaderTwo(String headerTwo) {
        this.headerTwo = headerTwo;
    }

    public String getDescriptionTwo() {
        return descriptionTwo;
    }

    public Message descriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
        return this;
    }

    public void setDescriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
    }

    public byte[] getBottomPicture() {
        return bottomPicture;
    }

    public Message bottomPicture(byte[] bottomPicture) {
        this.bottomPicture = bottomPicture;
        return this;
    }

    public void setBottomPicture(byte[] bottomPicture) {
        this.bottomPicture = bottomPicture;
    }

    public String getBottomPictureContentType() {
        return bottomPictureContentType;
    }

    public Message bottomPictureContentType(String bottomPictureContentType) {
        this.bottomPictureContentType = bottomPictureContentType;
        return this;
    }

    public void setBottomPictureContentType(String bottomPictureContentType) {
        this.bottomPictureContentType = bottomPictureContentType;
    }

    public String getBottomDescription() {
        return bottomDescription;
    }

    public Message bottomDescription(String bottomDescription) {
        this.bottomDescription = bottomDescription;
        return this;
    }

    public void setBottomDescription(String bottomDescription) {
        this.bottomDescription = bottomDescription;
    }

    public String getButtonText() {
        return buttonText;
    }

    public Message buttonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonURL() {
        return buttonURL;
    }

    public Message buttonURL(String buttonURL) {
        this.buttonURL = buttonURL;
        return this;
    }

    public void setButtonURL(String buttonURL) {
        this.buttonURL = buttonURL;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Message createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Message createdDate(Instant createdDate) {
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
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", activeMessage='" + isActiveMessage() + "'" +
            ", bannerPicture='" + getBannerPicture() + "'" +
            ", bannerPictureContentType='" + getBannerPictureContentType() + "'" +
            ", bannerName='" + getBannerName() + "'" +
            ", headingOne='" + getHeadingOne() + "'" +
            ", descriptionOne='" + getDescriptionOne() + "'" +
            ", quotePicture='" + getQuotePicture() + "'" +
            ", quotePictureContentType='" + getQuotePictureContentType() + "'" +
            ", quoteContent='" + getQuoteContent() + "'" +
            ", headerTwo='" + getHeaderTwo() + "'" +
            ", descriptionTwo='" + getDescriptionTwo() + "'" +
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
