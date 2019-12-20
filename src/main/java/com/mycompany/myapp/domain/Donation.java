package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mycompany.myapp.domain.enumeration.Currency;

import com.mycompany.myapp.domain.enumeration.PaymentMethod;

import com.mycompany.myapp.domain.enumeration.Frequency;

import com.mycompany.myapp.domain.enumeration.CollectionDate;

import com.mycompany.myapp.domain.enumeration.CardType;

/**
 * A Donation.
 */
@Entity
@Table(name = "donation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Donation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesforce_uid")
    private String salesforceUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod = PaymentMethod.CARDPAYMENT;
	
  
    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private Frequency frequency;

    
    @Column(name = "age_category")
    private Boolean ageCategory;

    
    @Column(name = "gift_aidable")
    private Boolean giftAidable;

    
    @Column(name = "gift_aid_message")
    private String giftAidMessage = "Default";

    
    @Column(name = "account_holder_name")
    private String accountHolderName = "Default";
    

    @Column(name = "account_number")
    private Integer accountNumber;

    @Column(name = "sortcode")
    private Integer sortcode;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "collection_date")
    private CollectionDate collectionDate = CollectionDate.FIRST;
    
    //@NotNull
    @Column(name = "is_account_holder")
    private Boolean isAccountHolder = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "expiry_month")
    private Integer expiryMonth;

    @Column(name = "expiry_year")
    private Integer expiryYear;

    @Column(name = "card_security_code")
    private Integer cardSecurityCode;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties("donations")
    private Supporter donationToSupporter;

    @ManyToOne
    @JsonIgnoreProperties("donations")
    private Project donatesToProject;

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

    public Donation salesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
        return this;
    }

    public void setSalesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Donation currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public Donation amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Donation paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public Donation frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Boolean isAgeCategory() {
        return ageCategory;
    }

    public Donation ageCategory(Boolean ageCategory) {
        this.ageCategory = ageCategory;
        return this;
    }

    public void setAgeCategory(Boolean ageCategory) {
        this.ageCategory = ageCategory;
    }

    public Boolean isGiftAidable() {
        return giftAidable;
    }

    public Donation giftAidable(Boolean giftAidable) {
        this.giftAidable = giftAidable;
        return this;
    }

    public void setGiftAidable(Boolean giftAidable) {
        this.giftAidable = giftAidable;
    }

    public String getGiftAidMessage() {
        return giftAidMessage;
    }

    public Donation giftAidMessage(String giftAidMessage) {
        this.giftAidMessage = giftAidMessage;
        return this;
    }

    public void setGiftAidMessage(String giftAidMessage) {
        this.giftAidMessage = giftAidMessage;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public Donation accountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        return this;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public Donation accountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getSortcode() {
        return sortcode;
    }

    public Donation sortcode(Integer sortcode) {
        this.sortcode = sortcode;
        return this;
    }

    public void setSortcode(Integer sortcode) {
        this.sortcode = sortcode;
    }

    public CollectionDate getCollectionDate() {
        return collectionDate;
    }

    public Donation collectionDate(CollectionDate collectionDate) {
        this.collectionDate = collectionDate;
        return this;
    }

    public void setCollectionDate(CollectionDate collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Boolean isIsAccountHolder() {
        return isAccountHolder;
    }

    public Donation isAccountHolder(Boolean isAccountHolder) {
        this.isAccountHolder = isAccountHolder;
        return this;
    }

    public void setIsAccountHolder(Boolean isAccountHolder) {
        this.isAccountHolder = isAccountHolder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Donation cardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public Donation cardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public Donation expiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public Donation expiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Integer getCardSecurityCode() {
        return cardSecurityCode;
    }

    public Donation cardSecurityCode(Integer cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
        return this;
    }

    public void setCardSecurityCode(Integer cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Donation createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Donation createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Supporter getDonationToSupporter() {
        return donationToSupporter;
    }

    public Donation donationToSupporter(Supporter supporter) {
        this.donationToSupporter = supporter;
        return this;
    }

    public void setDonationToSupporter(Supporter supporter) {
        this.donationToSupporter = supporter;
    }

    public Project getDonatesToProject() {
        return donatesToProject;
    }

    public Donation donatesToProject(Project project) {
        this.donatesToProject = project;
        return this;
    }

    public void setDonatesToProject(Project project) {
        this.donatesToProject = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donation)) {
            return false;
        }
        return id != null && id.equals(((Donation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Donation{" +
            "id=" + getId() +
            ", salesforceUID='" + getSalesforceUID() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", amount=" + getAmount() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", ageCategory='" + isAgeCategory() + "'" +
            ", giftAidable='" + isGiftAidable() + "'" +
            ", giftAidMessage='" + getGiftAidMessage() + "'" +
            ", accountHolderName='" + getAccountHolderName() + "'" +
            ", accountNumber=" + getAccountNumber() +
            ", sortcode=" + getSortcode() +
            ", collectionDate='" + getCollectionDate() + "'" +
            ", isAccountHolder='" + isIsAccountHolder() + "'" +
            ", cardType='" + getCardType() + "'" +
            ", cardNumber=" + getCardNumber() +
            ", expiryMonth=" + getExpiryMonth() +
            ", expiryYear=" + getExpiryYear() +
            ", cardSecurityCode=" + getCardSecurityCode() +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
