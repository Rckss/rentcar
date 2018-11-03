package com.ricks.rntcr.domain;

import com.ricks.rntcr.domain.enumeration.CarClass;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Price.
 */
@Entity
@Table(name = "price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Price implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adj_date")
    private LocalDate adjDate;

    @Column(name = "name")
    private String name;

    @Column(name = "serial")
    private String serial;

    @Column(name = "price")
    private Double price;

    @Column(name = "factor_one")
    private Integer factorOne;

    @Column(name = "factor_two")
    private Integer factorTwo;

    @Column(name = "factor_three")
    private Integer factorThree;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "total")
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "classification")
    private CarClass classification;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAdjDate() {
        return adjDate;
    }

    public Price adjDate(LocalDate adjDate) {
        this.adjDate = adjDate;
        return this;
    }

    public void setAdjDate(LocalDate adjDate) {
        this.adjDate = adjDate;
    }

    public String getName() {
        return name;
    }

    public Price name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public Price serial(String serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Double getPrice() {
        return price;
    }

    public Price price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getFactorOne() {
        return factorOne;
    }

    public Price factorOne(Integer factorOne) {
        this.factorOne = factorOne;
        return this;
    }

    public void setFactorOne(Integer factorOne) {
        this.factorOne = factorOne;
    }

    public Integer getFactorTwo() {
        return factorTwo;
    }

    public Price factorTwo(Integer factorTwo) {
        this.factorTwo = factorTwo;
        return this;
    }

    public void setFactorTwo(Integer factorTwo) {
        this.factorTwo = factorTwo;
    }

    public Integer getFactorThree() {
        return factorThree;
    }

    public Price factorThree(Integer factorThree) {
        this.factorThree = factorThree;
        return this;
    }

    public void setFactorThree(Integer factorThree) {
        this.factorThree = factorThree;
    }

    public Double getTax() {
        return tax;
    }

    public Price tax(Double tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public Price total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public CarClass getClassification() {
        return classification;
    }

    public Price classification(CarClass classification) {
        this.classification = classification;
        return this;
    }

    public void setClassification(CarClass classification) {
        this.classification = classification;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        if (price.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), price.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Price{" +
            "id=" + getId() +
            ", adjDate='" + getAdjDate() + "'" +
            ", name='" + getName() + "'" +
            ", serial='" + getSerial() + "'" +
            ", price=" + getPrice() +
            ", factorOne=" + getFactorOne() +
            ", factorTwo=" + getFactorTwo() +
            ", factorThree=" + getFactorThree() +
            ", tax=" + getTax() +
            ", total=" + getTotal() +
            ", classification='" + getClassification() + "'" +
            "}";
    }
}
