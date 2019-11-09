package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.TypeOfProject;

/**
 * A HomePage.
 */
@Entity
@Table(name = "home_page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HomePage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost_of_feeding_a_child")
    private Double costOfFeedingAChild;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_project")
    private TypeOfProject typeOfProject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCostOfFeedingAChild() {
        return costOfFeedingAChild;
    }

    public HomePage costOfFeedingAChild(Double costOfFeedingAChild) {
        this.costOfFeedingAChild = costOfFeedingAChild;
        return this;
    }

    public void setCostOfFeedingAChild(Double costOfFeedingAChild) {
        this.costOfFeedingAChild = costOfFeedingAChild;
    }

    public TypeOfProject getTypeOfProject() {
        return typeOfProject;
    }

    public HomePage typeOfProject(TypeOfProject typeOfProject) {
        this.typeOfProject = typeOfProject;
        return this;
    }

    public void setTypeOfProject(TypeOfProject typeOfProject) {
        this.typeOfProject = typeOfProject;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HomePage)) {
            return false;
        }
        return id != null && id.equals(((HomePage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HomePage{" +
            "id=" + getId() +
            ", costOfFeedingAChild=" + getCostOfFeedingAChild() +
            ", typeOfProject='" + getTypeOfProject() + "'" +
            "}";
    }
}
