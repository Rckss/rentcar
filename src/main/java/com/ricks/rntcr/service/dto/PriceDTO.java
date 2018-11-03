package com.ricks.rntcr.service.dto;

import com.ricks.rntcr.domain.enumeration.CarClass;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Price entity.
 */
public class PriceDTO implements Serializable {

    private Long id;

    private LocalDate adjDate;

    private String name;

    private String serial;

    private Double price;

    private Integer factorOne;

    private Integer factorTwo;

    private Integer factorThree;

    private Double tax;

    private Double total;

    private CarClass classification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAdjDate() {
        return adjDate;
    }

    public void setAdjDate(LocalDate adjDate) {
        this.adjDate = adjDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getFactorOne() {
        return factorOne;
    }

    public void setFactorOne(Integer factorOne) {
        this.factorOne = factorOne;
    }

    public Integer getFactorTwo() {
        return factorTwo;
    }

    public void setFactorTwo(Integer factorTwo) {
        this.factorTwo = factorTwo;
    }

    public Integer getFactorThree() {
        return factorThree;
    }

    public void setFactorThree(Integer factorThree) {
        this.factorThree = factorThree;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public CarClass getClassification() {
        return classification;
    }

    public void setClassification(CarClass classification) {
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceDTO priceDTO = (PriceDTO) o;
        if (priceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceDTO{" +
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
