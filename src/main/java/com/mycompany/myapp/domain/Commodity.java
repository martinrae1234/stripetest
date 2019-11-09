package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Commodity.
 */
@Entity
@Table(name = "commodity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_last_stock_check")
    private LocalDate dateOfLastStockCheck;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "perishable")
    private Boolean perishable;

    @Column(name = "amount_expirable_in_next_3_months")
    private Double amountExpirableInNext3months;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties("commodities")
    private School commodityForSchool;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfLastStockCheck() {
        return dateOfLastStockCheck;
    }

    public Commodity dateOfLastStockCheck(LocalDate dateOfLastStockCheck) {
        this.dateOfLastStockCheck = dateOfLastStockCheck;
        return this;
    }

    public void setDateOfLastStockCheck(LocalDate dateOfLastStockCheck) {
        this.dateOfLastStockCheck = dateOfLastStockCheck;
    }

    public String getName() {
        return name;
    }

    public Commodity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public Commodity amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean isPerishable() {
        return perishable;
    }

    public Commodity perishable(Boolean perishable) {
        this.perishable = perishable;
        return this;
    }

    public void setPerishable(Boolean perishable) {
        this.perishable = perishable;
    }

    public Double getAmountExpirableInNext3months() {
        return amountExpirableInNext3months;
    }

    public Commodity amountExpirableInNext3months(Double amountExpirableInNext3months) {
        this.amountExpirableInNext3months = amountExpirableInNext3months;
        return this;
    }

    public void setAmountExpirableInNext3months(Double amountExpirableInNext3months) {
        this.amountExpirableInNext3months = amountExpirableInNext3months;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Commodity createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Commodity createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public School getCommodityForSchool() {
        return commodityForSchool;
    }

    public Commodity commodityForSchool(School school) {
        this.commodityForSchool = school;
        return this;
    }

    public void setCommodityForSchool(School school) {
        this.commodityForSchool = school;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commodity)) {
            return false;
        }
        return id != null && id.equals(((Commodity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commodity{" +
            "id=" + getId() +
            ", dateOfLastStockCheck='" + getDateOfLastStockCheck() + "'" +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            ", perishable='" + isPerishable() + "'" +
            ", amountExpirableInNext3months=" + getAmountExpirableInNext3months() +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
