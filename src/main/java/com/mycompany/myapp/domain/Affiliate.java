package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mycompany.myapp.domain.enumeration.Currency;

/**
 * A Affiliate.
 */
@Entity
@Table(name = "affiliate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Affiliate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesforce_uid")
    private String salesforceUID;

    @Column(name = "affiliate_name")
    private String affiliateName;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

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

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "location_coordinate_x", nullable = false)
    private Float locationCoordinateX;

    @NotNull
    @Column(name = "location_coordinate_y", nullable = false)
    private Float locationCoordinateY;

    @Column(name = "default_language")
    private String defaultLanguage;

    @Column(name = "card_payment")
    private Boolean cardPayment;

    @Column(name = "single_card_payment")
    private Boolean singleCardPayment;

    @Column(name = "recurring_card_payment")
    private Boolean recurringCardPayment;

    @Column(name = "direct_debit")
    private Boolean directDebit;

    @Column(name = "gift_aid")
    private Boolean giftAid;

    @Column(name = "general_fundraising")
    private Boolean generalFundraising;

    @Column(name = "school_fundraising")
    private Boolean schoolFundraising;

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

    public Affiliate salesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
        return this;
    }

    public void setSalesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
    }

    public String getAffiliateName() {
        return affiliateName;
    }

    public Affiliate affiliateName(String affiliateName) {
        this.affiliateName = affiliateName;
        return this;
    }

    public void setAffiliateName(String affiliateName) {
        this.affiliateName = affiliateName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Affiliate currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Affiliate streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Affiliate city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public Affiliate county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public Affiliate postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public Affiliate country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public Affiliate email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Affiliate phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getLocationCoordinateX() {
        return locationCoordinateX;
    }

    public Affiliate locationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
        return this;
    }

    public void setLocationCoordinateX(Float locationCoordinateX) {
        this.locationCoordinateX = locationCoordinateX;
    }

    public Float getLocationCoordinateY() {
        return locationCoordinateY;
    }

    public Affiliate locationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
        return this;
    }

    public void setLocationCoordinateY(Float locationCoordinateY) {
        this.locationCoordinateY = locationCoordinateY;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public Affiliate defaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        return this;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public Boolean isCardPayment() {
        return cardPayment;
    }

    public Affiliate cardPayment(Boolean cardPayment) {
        this.cardPayment = cardPayment;
        return this;
    }

    public void setCardPayment(Boolean cardPayment) {
        this.cardPayment = cardPayment;
    }

    public Boolean isSingleCardPayment() {
        return singleCardPayment;
    }

    public Affiliate singleCardPayment(Boolean singleCardPayment) {
        this.singleCardPayment = singleCardPayment;
        return this;
    }

    public void setSingleCardPayment(Boolean singleCardPayment) {
        this.singleCardPayment = singleCardPayment;
    }

    public Boolean isRecurringCardPayment() {
        return recurringCardPayment;
    }

    public Affiliate recurringCardPayment(Boolean recurringCardPayment) {
        this.recurringCardPayment = recurringCardPayment;
        return this;
    }

    public void setRecurringCardPayment(Boolean recurringCardPayment) {
        this.recurringCardPayment = recurringCardPayment;
    }

    public Boolean isDirectDebit() {
        return directDebit;
    }

    public Affiliate directDebit(Boolean directDebit) {
        this.directDebit = directDebit;
        return this;
    }

    public void setDirectDebit(Boolean directDebit) {
        this.directDebit = directDebit;
    }

    public Boolean isGiftAid() {
        return giftAid;
    }

    public Affiliate giftAid(Boolean giftAid) {
        this.giftAid = giftAid;
        return this;
    }

    public void setGiftAid(Boolean giftAid) {
        this.giftAid = giftAid;
    }

    public Boolean isGeneralFundraising() {
        return generalFundraising;
    }

    public Affiliate generalFundraising(Boolean generalFundraising) {
        this.generalFundraising = generalFundraising;
        return this;
    }

    public void setGeneralFundraising(Boolean generalFundraising) {
        this.generalFundraising = generalFundraising;
    }

    public Boolean isSchoolFundraising() {
        return schoolFundraising;
    }

    public Affiliate schoolFundraising(Boolean schoolFundraising) {
        this.schoolFundraising = schoolFundraising;
        return this;
    }

    public void setSchoolFundraising(Boolean schoolFundraising) {
        this.schoolFundraising = schoolFundraising;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Affiliate createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Affiliate createdDate(Instant createdDate) {
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
        if (!(o instanceof Affiliate)) {
            return false;
        }
        return id != null && id.equals(((Affiliate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Affiliate{" +
            "id=" + getId() +
            ", salesforceUID='" + getSalesforceUID() + "'" +
            ", affiliateName='" + getAffiliateName() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", county='" + getCounty() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", country='" + getCountry() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", locationCoordinateX=" + getLocationCoordinateX() +
            ", locationCoordinateY=" + getLocationCoordinateY() +
            ", defaultLanguage='" + getDefaultLanguage() + "'" +
            ", cardPayment='" + isCardPayment() + "'" +
            ", singleCardPayment='" + isSingleCardPayment() + "'" +
            ", recurringCardPayment='" + isRecurringCardPayment() + "'" +
            ", directDebit='" + isDirectDebit() + "'" +
            ", giftAid='" + isGiftAid() + "'" +
            ", generalFundraising='" + isGeneralFundraising() + "'" +
            ", schoolFundraising='" + isSchoolFundraising() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
