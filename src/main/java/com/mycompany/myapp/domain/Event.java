package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesforce_uid")
    private String salesforceUID;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_date")
    private ZonedDateTime eventDate;

    @Column(name = "event_description")
    private String eventDescription;

    @Lob
    @Column(name = "event_banner")
    private byte[] eventBanner;

    @Column(name = "event_banner_content_type")
    private String eventBannerContentType;

    @Lob
    @Column(name = "event_picture_one")
    private byte[] eventPictureOne;

    @Column(name = "event_picture_one_content_type")
    private String eventPictureOneContentType;

    @Lob
    @Column(name = "event_pricture_two")
    private byte[] eventPrictureTwo;

    @Column(name = "event_pricture_two_content_type")
    private String eventPrictureTwoContentType;

    @NotNull
    @Column(name = "location_coordinate_x", nullable = false)
    private Float locationCoordinateX;

    @NotNull
    @Column(name = "location_coordinate_y", nullable = false)
    private Float locationCoordinateY;

    @Column(name = "attendees")
    private Integer attendees;

    @Column(name = "maximum_attendees")
    private Integer maximumAttendees;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Project project;

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

    public Event salesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
        return this;
    }

    public void setSalesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
    }

    public String getEventName() {
        return eventName;
    }

    public Event eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ZonedDateTime getEventDate() {
        return eventDate;
    }

    public Event eventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public Event eventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
        return this;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public byte[] getEventBanner() {
        return eventBanner;
    }

    public Event eventBanner(byte[] eventBanner) {
        this.eventBanner = eventBanner;
        return this;
    }

    public void setEventBanner(byte[] eventBanner) {
        this.eventBanner = eventBanner;
    }

    public String getEventBannerContentType() {
        return eventBannerContentType;
    }

    public Event eventBannerContentType(String eventBannerContentType) {
        this.eventBannerContentType = eventBannerContentType;
        return this;
    }

    public void setEventBannerContentType(String eventBannerContentType) {
        this.eventBannerContentType = eventBannerContentType;
    }

    public byte[] getEventPictureOne() {
        return eventPictureOne;
    }

    public Event eventPictureOne(byte[] eventPictureOne) {
        this.eventPictureOne = eventPictureOne;
        return this;
    }

    public void setEventPictureOne(byte[] eventPictureOne) {
        this.eventPictureOne = eventPictureOne;
    }

    public String getEventPictureOneContentType() {
        return eventPictureOneContentType;
    }

    public Event eventPictureOneContentType(String eventPictureOneContentType) {
        this.eventPictureOneContentType = eventPictureOneContentType;
        return this;
    }

    public void setEventPictureOneContentType(String eventPictureOneContentType) {
        this.eventPictureOneContentType = eventPictureOneContentType;
    }

    public byte[] getEventPrictureTwo() {
        return eventPrictureTwo;
    }

    public Event eventPrictureTwo(byte[] eventPrictureTwo) {
        this.eventPrictureTwo = eventPrictureTwo;
        return this;
    }

    public void setEventPrictureTwo(byte[] eventPrictureTwo) {
        this.eventPrictureTwo = eventPrictureTwo;
    }

    public String getEventPrictureTwoContentType() {
        return eventPrictureTwoContentType;
    }

    public Event eventPrictureTwoContentType(String eventPrictureTwoContentType) {
        this.eventPrictureTwoContentType = eventPrictureTwoContentType;
        return this;
    }

    public void setEventPrictureTwoContentType(String eventPrictureTwoContentType) {
        this.eventPrictureTwoContentType = eventPrictureTwoContentType;
    }

    public Float getLocationCoordinateX() {
        return locationCoordinateX;
    }

    public Event locationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
        return this;
    }

    public void setLocationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
    }

    public Float getLocationCoordinateY() {
        return locationCoordinateY;
    }

    public Event locationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
        return this;
    }

    public void setLocationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
    }

    public Integer getAttendees() {
        return attendees;
    }

    public Event attendees(Integer attendees) {
        this.attendees = attendees;
        return this;
    }

    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
    }

    public Integer getMaximumAttendees() {
        return maximumAttendees;
    }

    public Event maximumAttendees(Integer maximumAttendees) {
        this.maximumAttendees = maximumAttendees;
        return this;
    }

    public void setMaximumAttendees(Integer maximumAttendees) {
        this.maximumAttendees = maximumAttendees;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Event createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Event createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Project getProject() {
        return project;
    }

    public Event project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", salesforceUID='" + getSalesforceUID() + "'" +
            ", eventName='" + getEventName() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", eventDescription='" + getEventDescription() + "'" +
            ", eventBanner='" + getEventBanner() + "'" +
            ", eventBannerContentType='" + getEventBannerContentType() + "'" +
            ", eventPictureOne='" + getEventPictureOne() + "'" +
            ", eventPictureOneContentType='" + getEventPictureOneContentType() + "'" +
            ", eventPrictureTwo='" + getEventPrictureTwo() + "'" +
            ", eventPrictureTwoContentType='" + getEventPrictureTwoContentType() + "'" +
            ", locationCoordinateX=" + getLocationCoordinateX() +
            ", locationCoordinateY=" + getLocationCoordinateY() +
            ", attendees=" + getAttendees() +
            ", maximumAttendees=" + getMaximumAttendees() +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
