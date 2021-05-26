package com.example.simplespringwebapi.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personName;

    @Column(unique = true)
    private Long inn;

    @Column(unique = true, nullable = false)
    private Long telephone;

    private Integer regionCode;
    private String regionName;
    private String cityName;
    private String streetName;
    private Integer houseNumber;
    private Integer houseCorpsNumber;
    private Integer houseRoomNumber;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "PERSON_ID")
    private Set<PersonOrder> orders;

    public Long getId() {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public Long getInn() {
        return inn;
    }

    public Long getTelephone() {
        return telephone;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public Integer getHouseCorpsNumber() {
        return houseCorpsNumber;
    }

    public Integer getHouseRoomNumber() {
        return houseRoomNumber;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setInn(Long inn) {
        this.inn = inn;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setHouseCorpsNumber(Integer houseCorpsNumber) {
        this.houseCorpsNumber = houseCorpsNumber;
    }

    public void setHouseRoomNumber(Integer houseRoomNumber) {
        this.houseRoomNumber = houseRoomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id)
                && Objects.equals(personName, person.personName)
                && Objects.equals(inn, person.inn)
                && Objects.equals(telephone, person.telephone)
                && Objects.equals(regionCode, person.regionCode)
                && Objects.equals(regionName, person.regionName)
                && Objects.equals(cityName, person.cityName)
                && Objects.equals(streetName, person.streetName)
                && Objects.equals(houseNumber, person.houseNumber)
                && Objects.equals(houseCorpsNumber, person.houseCorpsNumber)
                && Objects.equals(houseRoomNumber, person.houseRoomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, personName, inn, telephone, regionCode, regionName, cityName,
                streetName, houseNumber, houseCorpsNumber, houseRoomNumber
        );
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + personName + '\'' +
                ", inn=" + inn +
                ", telephone=" + telephone +
                ", regionCode=" + regionCode +
                ", regionName='" + regionName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                ", houseCorpsNumber=" + houseCorpsNumber +
                ", houseRoomNumber=" + houseRoomNumber +
                '}';
    }
}
