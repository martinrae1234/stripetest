package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.TypeOfProject;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesforce_uid")
    private String salesforceUID;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_project", nullable = false)
    private TypeOfProject typeOfProject;

    @NotNull
    @Column(name = "fundraising_target", nullable = false)
    private Double fundraisingTarget;

    @NotNull
    @Column(name = "age_category", nullable = false)
    private Boolean ageCategory;

    @NotNull
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Lob
    @Column(name = "project_description")
    private String projectDescription;

    @Lob
    @Column(name = "project_image")
    private byte[] projectImage;

    @Column(name = "project_image_content_type")
    private String projectImageContentType;

    @Column(name = "sponsorship_start")
    private LocalDate sponsorshipStart;

    @Column(name = "sponsorship_end_date")
    private LocalDate sponsorshipEndDate;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private Supporter projectOfsupporter;

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private School projectForSchool;

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

    public Project salesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
        return this;
    }

    public void setSalesforceUID(String salesforceUID) {
        this.salesforceUID = salesforceUID;
    }

    public TypeOfProject getTypeOfProject() {
        return typeOfProject;
    }

    public Project typeOfProject(TypeOfProject typeOfProject) {
        this.typeOfProject = typeOfProject;
        return this;
    }

    public void setTypeOfProject(TypeOfProject typeOfProject) {
        this.typeOfProject = typeOfProject;
    }

    public Double getFundraisingTarget() {
        return fundraisingTarget;
    }

    public Project fundraisingTarget(Double fundraisingTarget) {
        this.fundraisingTarget = fundraisingTarget;
        return this;
    }

    public void setFundraisingTarget(Double fundraisingTarget) {
        this.fundraisingTarget = fundraisingTarget;
    }

    public Boolean isAgeCategory() {
        return ageCategory;
    }

    public Project ageCategory(Boolean ageCategory) {
        this.ageCategory = ageCategory;
        return this;
    }

    public void setAgeCategory(Boolean ageCategory) {
        this.ageCategory = ageCategory;
    }

    public String getProjectName() {
        return projectName;
    }

    public Project projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public Project projectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
        return this;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public byte[] getProjectImage() {
        return projectImage;
    }

    public Project projectImage(byte[] projectImage) {
        this.projectImage = projectImage;
        return this;
    }

    public void setProjectImage(byte[] projectImage) {
        this.projectImage = projectImage;
    }

    public String getProjectImageContentType() {
        return projectImageContentType;
    }

    public Project projectImageContentType(String projectImageContentType) {
        this.projectImageContentType = projectImageContentType;
        return this;
    }

    public void setProjectImageContentType(String projectImageContentType) {
        this.projectImageContentType = projectImageContentType;
    }

    public LocalDate getSponsorshipStart() {
        return sponsorshipStart;
    }

    public Project sponsorshipStart(LocalDate sponsorshipStart) {
        this.sponsorshipStart = sponsorshipStart;
        return this;
    }

    public void setSponsorshipStart(LocalDate sponsorshipStart) {
        this.sponsorshipStart = sponsorshipStart;
    }

    public LocalDate getSponsorshipEndDate() {
        return sponsorshipEndDate;
    }

    public Project sponsorshipEndDate(LocalDate sponsorshipEndDate) {
        this.sponsorshipEndDate = sponsorshipEndDate;
        return this;
    }

    public void setSponsorshipEndDate(LocalDate sponsorshipEndDate) {
        this.sponsorshipEndDate = sponsorshipEndDate;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Project createdByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Project createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Supporter getProjectOfsupporter() {
        return projectOfsupporter;
    }

    public Project projectOfsupporter(Supporter supporter) {
        this.projectOfsupporter = supporter;
        return this;
    }

    public void setProjectOfsupporter(Supporter supporter) {
        this.projectOfsupporter = supporter;
    }

    public School getProjectForSchool() {
        return projectForSchool;
    }

    public Project projectForSchool(School school) {
        this.projectForSchool = school;
        return this;
    }

    public void setProjectForSchool(School school) {
        this.projectForSchool = school;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", salesforceUID='" + getSalesforceUID() + "'" +
            ", typeOfProject='" + getTypeOfProject() + "'" +
            ", fundraisingTarget=" + getFundraisingTarget() +
            ", ageCategory='" + isAgeCategory() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", projectDescription='" + getProjectDescription() + "'" +
            ", projectImage='" + getProjectImage() + "'" +
            ", projectImageContentType='" + getProjectImageContentType() + "'" +
            ", sponsorshipStart='" + getSponsorshipStart() + "'" +
            ", sponsorshipEndDate='" + getSponsorshipEndDate() + "'" +
            ", createdByUserId=" + getCreatedByUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
