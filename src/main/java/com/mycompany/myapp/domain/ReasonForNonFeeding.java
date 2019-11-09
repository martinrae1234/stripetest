package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ReasonForNonFeeding.
 */
@Entity
@Table(name = "reason_for_non_feeding")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReasonForNonFeeding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_non_feeding")
    private Instant dateOfNonFeeding;

    @Column(name = "reason_for_non_feeding")
    private String reasonForNonFeeding;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties("reasonForNonFeedings")
    private School schoolNotFed;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateOfNonFeeding() {
        return dateOfNonFeeding;
    }

    public ReasonForNonFeeding dateOfNonFeeding(Instant dateOfNonFeeding) {
        this.dateOfNonFeeding = dateOfNonFeeding;
        return this;
    }

    public void setDateOfNonFeeding(Instant dateOfNonFeeding) {
        this.dateOfNonFeeding = dateOfNonFeeding;
    }

    public String getReasonForNonFeeding() {
        return reasonForNonFeeding;
    }

    public ReasonForNonFeeding reasonForNonFeeding(String reasonForNonFeeding) {
        this.reasonForNonFeeding = reasonForNonFeeding;
        return this;
    }

    public void setReasonForNonFeeding(String reasonForNonFeeding) {
        this.reasonForNonFeeding = reasonForNonFeeding;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public ReasonForNonFeeding createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ReasonForNonFeeding createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public School getSchoolNotFed() {
        return schoolNotFed;
    }

    public ReasonForNonFeeding schoolNotFed(School school) {
        this.schoolNotFed = school;
        return this;
    }

    public void setSchoolNotFed(School school) {
        this.schoolNotFed = school;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReasonForNonFeeding)) {
            return false;
        }
        return id != null && id.equals(((ReasonForNonFeeding) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReasonForNonFeeding{" +
            "id=" + getId() +
            ", dateOfNonFeeding='" + getDateOfNonFeeding() + "'" +
            ", reasonForNonFeeding='" + getReasonForNonFeeding() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
