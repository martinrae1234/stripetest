package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mycompany.myapp.domain.enumeration.TypeOfStaff;

/**
 * A Staff.
 */
@Entity
@Table(name = "staff")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesforce_uid")
    private String salesforceUID;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "affiliate")
    private String affiliate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_staff")
    private TypeOfStaff typeOfStaff;

    @NotNull
    @Column(name = "location_coordinate_x", nullable = false)
    private Float locationCoordinateX;

    @NotNull
    @Column(name = "location_coordinate_y", nullable = false)
    private Float locationCoordinateY;

    
    @Lob
    @Column(name = "staff_picture", nullable = false)
    private byte[] staffPicture;

    @Column(name = "staff_picture_content_type", nullable = false)
    private String staffPictureContentType;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("staff")
    private Affiliate staffOfAffiliate;

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

    public Staff salesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
        return this;
    }

    public void setSalesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public Staff firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Staff secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public Staff affiliate(String affiliate) {
        this.affiliate = affiliate;
        return this;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public TypeOfStaff getTypeOfStaff() {
        return typeOfStaff;
    }

    public Staff typeOfStaff(TypeOfStaff typeOfStaff) {
        this.typeOfStaff = typeOfStaff;
        return this;
    }

    public void setTypeOfStaff(TypeOfStaff typeOfStaff) {
        this.typeOfStaff = typeOfStaff;
    }

    public Float getLocationCoordinateX() {
        return locationCoordinateX;
    }

    public Staff locationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
        return this;
    }

    public void setLocationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
    }

    public Float getLocationCoordinateY() {
        return locationCoordinateY;
    }

    public Staff locationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
        return this;
    }

    public void setLocationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
    }

    public byte[] getStaffPicture() {
        return staffPicture;
    }

    public Staff staffPicture(byte[] staffPicture) {
        this.staffPicture = staffPicture;
        return this;
    }

    public void setStaffPicture(byte[] staffPicture) {
        this.staffPicture = staffPicture;
    }

    public String getStaffPictureContentType() {
        return staffPictureContentType;
    }

    public Staff staffPictureContentType(String staffPictureContentType) {
        this.staffPictureContentType = staffPictureContentType;
        return this;
    }

    public void setStaffPictureContentType(String staffPictureContentType) {
        this.staffPictureContentType = staffPictureContentType;
    }

    public String getPosition() {
        return position;
    }

    public Staff position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public Staff description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Staff createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Staff createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public Staff user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Affiliate getStaffOfAffiliate() {
        return staffOfAffiliate;
    }

    public Staff staffOfAffiliate(Affiliate affiliate) {
        this.staffOfAffiliate = affiliate;
        return this;
    }

    public void setStaffOfAffiliate(Affiliate affiliate) {
        this.staffOfAffiliate = affiliate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Staff)) {
            return false;
        }
        return id != null && id.equals(((Staff) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Staff{" +
            "id=" + getId() +
            ", salesforceUID='" + getSalesforceUID() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", affiliate='" + getAffiliate() + "'" +
            ", typeOfStaff='" + getTypeOfStaff() + "'" +
            ", locationCoordinateX=" + getLocationCoordinateX() +
            ", locationCoordinateY=" + getLocationCoordinateY() +
            ", staffPicture='" + getStaffPicture() + "'" +
            ", staffPictureContentType='" + getStaffPictureContentType() + "'" +
            ", position='" + getPosition() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
