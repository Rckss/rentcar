package com.ricks.rntcr.service.dto;

import com.ricks.rntcr.domain.enumeration.CarClass;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Price entity. This class is used in PriceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prices?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PriceCriteria implements Serializable {
    /**
     * Class for filtering CarClass
     */
    public static class CarClassFilter extends Filter<CarClass> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter adjDate;

    private StringFilter name;

    private StringFilter serial;

    private DoubleFilter price;

    private IntegerFilter factorOne;

    private IntegerFilter factorTwo;

    private IntegerFilter factorThree;

    private DoubleFilter tax;

    private DoubleFilter total;

    private CarClassFilter classification;

    public PriceCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getAdjDate() {
        return adjDate;
    }

    public void setAdjDate(LocalDateFilter adjDate) {
        this.adjDate = adjDate;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSerial() {
        return serial;
    }

    public void setSerial(StringFilter serial) {
        this.serial = serial;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public IntegerFilter getFactorOne() {
        return factorOne;
    }

    public void setFactorOne(IntegerFilter factorOne) {
        this.factorOne = factorOne;
    }

    public IntegerFilter getFactorTwo() {
        return factorTwo;
    }

    public void setFactorTwo(IntegerFilter factorTwo) {
        this.factorTwo = factorTwo;
    }

    public IntegerFilter getFactorThree() {
        return factorThree;
    }

    public void setFactorThree(IntegerFilter factorThree) {
        this.factorThree = factorThree;
    }

    public DoubleFilter getTax() {
        return tax;
    }

    public void setTax(DoubleFilter tax) {
        this.tax = tax;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public CarClassFilter getClassification() {
        return classification;
    }

    public void setClassification(CarClassFilter classification) {
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
        final PriceCriteria that = (PriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(adjDate, that.adjDate) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serial, that.serial) &&
            Objects.equals(price, that.price) &&
            Objects.equals(factorOne, that.factorOne) &&
            Objects.equals(factorTwo, that.factorTwo) &&
            Objects.equals(factorThree, that.factorThree) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(total, that.total) &&
            Objects.equals(classification, that.classification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        adjDate,
        name,
        serial,
        price,
        factorOne,
        factorTwo,
        factorThree,
        tax,
        total,
        classification
        );
    }

    @Override
    public String toString() {
        return "PriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (adjDate != null ? "adjDate=" + adjDate + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serial != null ? "serial=" + serial + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (factorOne != null ? "factorOne=" + factorOne + ", " : "") +
                (factorTwo != null ? "factorTwo=" + factorTwo + ", " : "") +
                (factorThree != null ? "factorThree=" + factorThree + ", " : "") +
                (tax != null ? "tax=" + tax + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (classification != null ? "classification=" + classification + ", " : "") +
            "}";
    }

}
