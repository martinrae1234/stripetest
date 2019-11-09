package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mycompany.myapp.domain.enumeration.SupporterSalutation;

/**
 * A Supporter.
 */
@Entity
@Table(name = "supporter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Supporter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesforce_uid")
    private String salesforceUID;

    @NotNull
    @Column(name = "age_category", nullable = false)
    private Boolean ageCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "supporter_salutation")
    private SupporterSalutation supporterSalutation;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "county", nullable = false)
    private String county;

    @NotNull
    @Column(name = "postcode", nullable = false)
    private String postcode;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @Lob
    @Column(name = "supporter_picture")
    private byte[] supporterPicture;

    @Column(name = "supporter_picture_content_type")
    private String supporterPictureContentType;

    @NotNull
    @Column(name = "contactable_by_email", nullable = false)
    private Boolean contactableByEmail;

    @NotNull
    @Column(name = "contactable_by_post", nullable = false)
    private Boolean contactableByPost;

    @NotNull
    @Column(name = "location_coordinate_x", nullable = false)
    private Float locationCoordinateX;

    @NotNull
    @Column(name = "location_coordinate_y", nullable = false)
    private Float locationCoordinateY;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("supporters")
    private Affiliate supporterOfAffiliate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesforceUID() {
        return salesforceUID;
    }

    public Supporter salesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
        return this;
    }

    public void setSalesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
    }

    public Boolean isAgeCategory() {
        return ageCategory;
    }

    public Supporter ageCategory(Boolean ageCategory) {
        this.ageCategory = ageCategory;
        return this;
    }

    public void setAgeCategory(Boolean ageCategory) {
        this.ageCategory = ageCategory;
    }

    public SupporterSalutation getSupporterSalutation() {
        return supporterSalutation;
    }

    public Supporter supporterSalutation(SupporterSalutation supporterSalutation) {
        this.supporterSalutation = supporterSalutation;
        return this;
    }

    public void setSupporterSalutation(SupporterSalutation supporterSalutation) {
        this.supporterSalutation = supporterSalutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public Supporter firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Supporter secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public Supporter email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Supporter streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Supporter city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public Supporter county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public Supporter postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public Supporter country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getSupporterPicture() {
        return supporterPicture;
    }

    public Supporter supporterPicture(byte[] supporterPicture) {
        this.supporterPicture = supporterPicture;
        return this;
    }

    public void setSupporterPicture(byte[] supporterPicture) {
        this.supporterPicture = supporterPicture;
    }

    public String getSupporterPictureContentType() {
        return supporterPictureContentType;
    }

    public Supporter supporterPictureContentType(String supporterPictureContentType) {
        this.supporterPictureContentType = supporterPictureContentType;
        return this;
    }

    public void setSupporterPictureContentType(String supporterPictureContentType) {
        this.supporterPictureContentType = supporterPictureContentType;
    }

    public Boolean isContactableByEmail() {
        return contactableByEmail;
    }

    public Supporter contactableByEmail(Boolean contactableByEmail) {
        this.contactableByEmail = contactableByEmail;
        return this;
    }

    public void setContactableByEmail(Boolean contactableByEmail) {
        this.contactableByEmail = contactableByEmail;
    }

    public Boolean isContactableByPost() {
        return contactableByPost;
    }

    public Supporter contactableByPost(Boolean contactableByPost) {
        this.contactableByPost = contactableByPost;
        return this;
    }

    public void setContactableByPost(Boolean contactableByPost) {
        this.contactableByPost = contactableByPost;
    }

    public Float getLocationCoordinateX() {
        return locationCoordinateX;
    }

    public Supporter locationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
        return this;
    }

    public void setLocationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
    }

    public Float getLocationCoordinateY() {
        return locationCoordinateY;
    }

    public Supporter locationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
        return this;
    }

    public void setLocationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public Supporter facebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
        return this;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public Supporter instagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
        return this;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public Supporter twitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
        return this;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Supporter createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Supporter createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public Supporter user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Affiliate getSupporterOfAffiliate() {
        return supporterOfAffiliate;
    }

    public Supporter supporterOfAffiliate(Affiliate affiliate) {
        this.supporterOfAffiliate = affiliate;
        return this;
    }

    public void setSupporterOfAffiliate(Affiliate affiliate) {
        this.supporterOfAffiliate = affiliate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supporter)) {
            return false;
        }
        return id != null && id.equals(((Supporter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Supporter{" +
            "id=" + getId() +
            ", salesforceUID='" + getSalesforceUID() + "'" +
            ", ageCategory='" + isAgeCategory() + "'" +
            ", supporterSalutation='" + getSupporterSalutation() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", email='" + getEmail() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", county='" + getCounty() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", country='" + getCountry() + "'" +
            ", supporterPicture='" + getSupporterPicture() + "'" +
            ", supporterPictureContentType='" + getSupporterPictureContentType() + "'" +
            ", contactableByEmail='" + isContactableByEmail() + "'" +
            ", contactableByPost='" + isContactableByPost() + "'" +
            ", locationCoordinateX=" + getLocationCoordinateX() +
            ", locationCoordinateY=" + getLocationCoordinateY() +
            ", facebookUrl='" + getFacebookUrl() + "'" +
            ", instagramUrl='" + getInstagramUrl() + "'" +
            ", twitterUrl='" + getTwitterUrl() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
