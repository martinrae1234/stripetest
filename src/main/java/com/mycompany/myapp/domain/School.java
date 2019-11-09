package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A School.
 */
@Entity
@Table(name = "school")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesforce_uid")
    private String salesforceUID;

    @Column(name = "legacy_school_id")
    private String legacySchoolID;

    @NotNull
    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "sponsored")
    private Boolean sponsored;

    @Column(name = "attendance_total")
    private Integer attendanceTotal;

    @NotNull
    @Column(name = "attendance_boys", nullable = false)
    private Integer attendanceBoys;

    @NotNull
    @Column(name = "attendance_girls", nullable = false)
    private Integer attendanceGirls;

    @NotNull
    @Column(name = "enrolment_total", nullable = false)
    private Integer enrolmentTotal;

    @NotNull
    @Column(name = "location_coordinate_x", nullable = false)
    private Float locationCoordinateX;

    @NotNull
    @Column(name = "location_coordinate_y", nullable = false)
    private Float locationCoordinateY;

    @Lob
    @Column(name = "image_banner")
    private byte[] imageBanner;

    @Column(name = "image_banner_content_type")
    private String imageBannerContentType;

    @Column(name = "text_banner")
    private String textBanner;

    @Lob
    @Column(name = "image_one")
    private byte[] imageOne;

    @Column(name = "image_one_content_type")
    private String imageOneContentType;

    @Lob
    @Column(name = "image_two")
    private byte[] imageTwo;

    @Column(name = "image_two_content_type")
    private String imageTwoContentType;

    @Lob
    @Column(name = "image_three")
    private byte[] imageThree;

    @Column(name = "image_three_content_type")
    private String imageThreeContentType;

    @Lob
    @Column(name = "image_four")
    private byte[] imageFour;

    @Column(name = "image_four_content_type")
    private String imageFourContentType;

    @Column(name = "date_of_last_stock_check")
    private LocalDate dateOfLastStockCheck;

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

    public String getSalesforceUID() {
        return salesforceUID;
    }

    public School salesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
        return this;
    }

    public void setSalesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
    }

    public String getLegacySchoolID() {
        return legacySchoolID;
    }

    public School legacySchoolID(String legacySchoolID) {
        this.legacySchoolID = legacySchoolID;
        return this;
    }

    public void setLegacySchoolID(String legacySchoolID) {
        this.legacySchoolID = legacySchoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public School schoolName(String schoolName) {
        this.schoolName = schoolName;
        return this;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Boolean isSponsored() {
        return sponsored;
    }

    public School sponsored(Boolean sponsored) {
        this.sponsored = sponsored;
        return this;
    }

    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    public Integer getAttendanceTotal() {
        return attendanceTotal;
    }

    public School attendanceTotal(Integer attendanceTotal) {
        this.attendanceTotal = attendanceTotal;
        return this;
    }

    public void setAttendanceTotal(Integer attendanceTotal) {
        this.attendanceTotal = attendanceTotal;
    }

    public Integer getAttendanceBoys() {
        return attendanceBoys;
    }

    public School attendanceBoys(Integer attendanceBoys) {
        this.attendanceBoys = attendanceBoys;
        return this;
    }

    public void setAttendanceBoys(Integer attendanceBoys) {
        this.attendanceBoys = attendanceBoys;
    }

    public Integer getAttendanceGirls() {
        return attendanceGirls;
    }

    public School attendanceGirls(Integer attendanceGirls) {
        this.attendanceGirls = attendanceGirls;
        return this;
    }

    public void setAttendanceGirls(Integer attendanceGirls) {
        this.attendanceGirls = attendanceGirls;
    }

    public Integer getEnrolmentTotal() {
        return enrolmentTotal;
    }

    public School enrolmentTotal(Integer enrolmentTotal) {
        this.enrolmentTotal = enrolmentTotal;
        return this;
    }

    public void setEnrolmentTotal(Integer enrolmentTotal) {
        this.enrolmentTotal = enrolmentTotal;
    }

    public Float getLocationCoordinateX() {
        return locationCoordinateX;
    }

    public School locationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
        return this;
    }

    public void setLocationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
    }

    public Float getLocationCoordinateY() {
        return locationCoordinateY;
    }

    public School locationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
        return this;
    }

    public void setLocationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
    }

    public byte[] getImageBanner() {
        return imageBanner;
    }

    public School imageBanner(byte[] imageBanner) {
        this.imageBanner = imageBanner;
        return this;
    }

    public void setImageBanner(byte[] imageBanner) {
        this.imageBanner = imageBanner;
    }

    public String getImageBannerContentType() {
        return imageBannerContentType;
    }

    public School imageBannerContentType(String imageBannerContentType) {
        this.imageBannerContentType = imageBannerContentType;
        return this;
    }

    public void setImageBannerContentType(String imageBannerContentType) {
        this.imageBannerContentType = imageBannerContentType;
    }

    public String getTextBanner() {
        return textBanner;
    }

    public School textBanner(String textBanner) {
        this.textBanner = textBanner;
        return this;
    }

    public void setTextBanner(String textBanner) {
        this.textBanner = textBanner;
    }

    public byte[] getImageOne() {
        return imageOne;
    }

    public School imageOne(byte[] imageOne) {
        this.imageOne = imageOne;
        return this;
    }

    public void setImageOne(byte[] imageOne) {
        this.imageOne = imageOne;
    }

    public String getImageOneContentType() {
        return imageOneContentType;
    }

    public School imageOneContentType(String imageOneContentType) {
        this.imageOneContentType = imageOneContentType;
        return this;
    }

    public void setImageOneContentType(String imageOneContentType) {
        this.imageOneContentType = imageOneContentType;
    }

    public byte[] getImageTwo() {
        return imageTwo;
    }

    public School imageTwo(byte[] imageTwo) {
        this.imageTwo = imageTwo;
        return this;
    }

    public void setImageTwo(byte[] imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageTwoContentType() {
        return imageTwoContentType;
    }

    public School imageTwoContentType(String imageTwoContentType) {
        this.imageTwoContentType = imageTwoContentType;
        return this;
    }

    public void setImageTwoContentType(String imageTwoContentType) {
        this.imageTwoContentType = imageTwoContentType;
    }

    public byte[] getImageThree() {
        return imageThree;
    }

    public School imageThree(byte[] imageThree) {
        this.imageThree = imageThree;
        return this;
    }

    public void setImageThree(byte[] imageThree) {
        this.imageThree = imageThree;
    }

    public String getImageThreeContentType() {
        return imageThreeContentType;
    }

    public School imageThreeContentType(String imageThreeContentType) {
        this.imageThreeContentType = imageThreeContentType;
        return this;
    }

    public void setImageThreeContentType(String imageThreeContentType) {
        this.imageThreeContentType = imageThreeContentType;
    }

    public byte[] getImageFour() {
        return imageFour;
    }

    public School imageFour(byte[] imageFour) {
        this.imageFour = imageFour;
        return this;
    }

    public void setImageFour(byte[] imageFour) {
        this.imageFour = imageFour;
    }

    public String getImageFourContentType() {
        return imageFourContentType;
    }

    public School imageFourContentType(String imageFourContentType) {
        this.imageFourContentType = imageFourContentType;
        return this;
    }

    public void setImageFourContentType(String imageFourContentType) {
        this.imageFourContentType = imageFourContentType;
    }

    public LocalDate getDateOfLastStockCheck() {
        return dateOfLastStockCheck;
    }

    public School dateOfLastStockCheck(LocalDate dateOfLastStockCheck) {
        this.dateOfLastStockCheck = dateOfLastStockCheck;
        return this;
    }

    public void setDateOfLastStockCheck(LocalDate dateOfLastStockCheck) {
        this.dateOfLastStockCheck = dateOfLastStockCheck;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public School createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public School createdDate(Instant createdDate) {
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
        if (!(o instanceof School)) {
            return false;
        }
        return id != null && id.equals(((School) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", salesforceUID='" + getSalesforceUID() + "'" +
            ", legacySchoolID='" + getLegacySchoolID() + "'" +
            ", schoolName='" + getSchoolName() + "'" +
            ", sponsored='" + isSponsored() + "'" +
            ", attendanceTotal=" + getAttendanceTotal() +
            ", attendanceBoys=" + getAttendanceBoys() +
            ", attendanceGirls=" + getAttendanceGirls() +
            ", enrolmentTotal=" + getEnrolmentTotal() +
            ", locationCoordinateX=" + getLocationCoordinateX() +
            ", locationCoordinateY=" + getLocationCoordinateY() +
            ", imageBanner='" + getImageBanner() + "'" +
            ", imageBannerContentType='" + getImageBannerContentType() + "'" +
            ", textBanner='" + getTextBanner() + "'" +
            ", imageOne='" + getImageOne() + "'" +
            ", imageOneContentType='" + getImageOneContentType() + "'" +
            ", imageTwo='" + getImageTwo() + "'" +
            ", imageTwoContentType='" + getImageTwoContentType() + "'" +
            ", imageThree='" + getImageThree() + "'" +
            ", imageThreeContentType='" + getImageThreeContentType() + "'" +
            ", imageFour='" + getImageFour() + "'" +
            ", imageFourContentType='" + getImageFourContentType() + "'" +
            ", dateOfLastStockCheck='" + getDateOfLastStockCheck() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
