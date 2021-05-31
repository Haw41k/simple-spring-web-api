package com.example.simplespringwebapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
public class PersonOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PERSON_ID")
    private Long personId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime creationDate;
    private String description;
    private BigDecimal sum;

    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonOrder that = (PersonOrder) o;
        return id.equals(that.id)
                && personId.equals(that.personId)
                && Objects.equals(creationDate, that.creationDate)
                && Objects.equals(description, that.description)
                && Objects.equals(sum, that.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, creationDate, description, sum);
    }

    @Override
    public String toString() {
        return "PersonOrder{" +
                "id=" + id +
                ", personId=" + personId +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                ", sum=" + sum +
                '}';
    }
}
