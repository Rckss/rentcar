package com.ricks.rntcr.service.dto;

import com.ricks.rntcr.domain.enumeration.Status;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the RentHistory entity.
 */
public class RentHistoryDTO implements Serializable {

    private Long id;

    private LocalDate regDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double totalPaid;

    private Status status;

    private Set<CarDTO> cars = new HashSet<>();

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<CarDTO> getCars() {
        return cars;
    }

    public void setCars(Set<CarDTO> cars) {
        this.cars = cars;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long userId) {
        this.clientId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RentHistoryDTO rentHistoryDTO = (RentHistoryDTO) o;
        if (rentHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rentHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RentHistoryDTO{" +
            "id=" + getId() +
            ", regDate='" + getRegDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", totalPaid=" + getTotalPaid() +
            ", status='" + getStatus() + "'" +
            ", client=" + getClientId() +
            "}";
    }
}
