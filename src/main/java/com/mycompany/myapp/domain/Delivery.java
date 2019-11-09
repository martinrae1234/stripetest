package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Delivery.
 */
@Entity
@Table(name = "delivery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "amount_delivered_rice")
    private Double amountDeliveredRice;

    @Column(name = "amount_delivered_flour")
    private Double amountDeliveredFlour;

    @Lob
    @Column(name = "delivery_note_image")
    private byte[] deliveryNoteImage;

    @Column(name = "delivery_note_image_content_type")
    private String deliveryNoteImageContentType;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties("deliveries")
    private School deliveryForSchool;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Delivery deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getAmountDeliveredRice() {
        return amountDeliveredRice;
    }

    public Delivery amountDeliveredRice(Double amountDeliveredRice) {
        this.amountDeliveredRice = amountDeliveredRice;
        return this;
    }

    public void setAmountDeliveredRice(Double amountDeliveredRice) {
        this.amountDeliveredRice = amountDeliveredRice;
    }

    public Double getAmountDeliveredFlour() {
        return amountDeliveredFlour;
    }

    public Delivery amountDeliveredFlour(Double amountDeliveredFlour) {
        this.amountDeliveredFlour = amountDeliveredFlour;
        return this;
    }

    public void setAmountDeliveredFlour(Double amountDeliveredFlour) {
        this.amountDeliveredFlour = amountDeliveredFlour;
    }

    public byte[] getDeliveryNoteImage() {
        return deliveryNoteImage;
    }

    public Delivery deliveryNoteImage(byte[] deliveryNoteImage) {
        this.deliveryNoteImage = deliveryNoteImage;
        return this;
    }

    public void setDeliveryNoteImage(byte[] deliveryNoteImage) {
        this.deliveryNoteImage = deliveryNoteImage;
    }

    public String getDeliveryNoteImageContentType() {
        return deliveryNoteImageContentType;
    }

    public Delivery deliveryNoteImageContentType(String deliveryNoteImageContentType) {
        this.deliveryNoteImageContentType = deliveryNoteImageContentType;
        return this;
    }

    public void setDeliveryNoteImageContentType(String deliveryNoteImageContentType) {
        this.deliveryNoteImageContentType = deliveryNoteImageContentType;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Delivery createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Delivery createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public School getDeliveryForSchool() {
        return deliveryForSchool;
    }

    public Delivery deliveryForSchool(School school) {
        this.deliveryForSchool = school;
        return this;
    }

    public void setDeliveryForSchool(School school) {
        this.deliveryForSchool = school;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Delivery)) {
            return false;
        }
        return id != null && id.equals(((Delivery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + getId() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", amountDeliveredRice=" + getAmountDeliveredRice() +
            ", amountDeliveredFlour=" + getAmountDeliveredFlour() +
            ", deliveryNoteImage='" + getDeliveryNoteImage() + "'" +
            ", deliveryNoteImageContentType='" + getDeliveryNoteImageContentType() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
