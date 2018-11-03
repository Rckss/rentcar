package com.ricks.rntcr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ricks.rntcr.domain.enumeration.Status;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A RentHistory.
 */
@Entity
@Table(name = "rent_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_date")
    private LocalDate regDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "total_paid")
    private Double totalPaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rent_history_car",
               joinColumns = @JoinColumn(name = "rent_histories_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cars_id", referencedColumnName = "id"))
    private Set<Car> cars = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private User client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public RentHistory regDate(LocalDate regDate) {
        this.regDate = regDate;
        return this;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public RentHistory startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public RentHistory endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public RentHistory totalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
        return this;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Status getStatus() {
        return status;
    }

    public RentHistory status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public RentHistory cars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }

    public RentHistory addCar(Car car) {
        this.cars.add(car);
        return this;
    }

    public RentHistory removeCar(Car car) {
        this.cars.remove(car);
        return this;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public User getClient() {
        return client;
    }

    public RentHistory client(User user) {
        this.client = user;
        return this;
    }

    public void setClient(User user) {
        this.client = user;
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
        RentHistory rentHistory = (RentHistory) o;
        if (rentHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rentHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RentHistory{" +
            "id=" + getId() +
            ", regDate='" + getRegDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", totalPaid=" + getTotalPaid() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
