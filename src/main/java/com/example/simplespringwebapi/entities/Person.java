package com.example.simplespringwebapi.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(unique = true)
    private long inn;

    @Column(unique = true, nullable = false)
    private long telephone;

    private int regionCode;
    private String regionName;
    private String cityName;
    private String streetName;
    private int houseNumber;
    private int houseCorpsNumber;
    private int houseRoomNumber;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "PERSON_ID")
    private Set<PersonOrder> orders;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getInn() {
        return inn;
    }

    public long getTelephone() {
        return telephone;
    }

    public int getRegionCode() {
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

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getHouseCorpsNumber() {
        return houseCorpsNumber;
    }

    public int getHouseRoomNumber() {
        return houseRoomNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public void setRegionCode(int regionCode) {
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

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setHouseCorpsNumber(int houseCorpsNumber) {
        this.houseCorpsNumber = houseCorpsNumber;
    }

    public void setHouseRoomNumber(int houseRoomNumber) {
        this.houseRoomNumber = houseRoomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id
                && Objects.equals(name, person.name)
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
                id, name, inn, telephone, regionCode, regionName, cityName,
                streetName, houseNumber, houseCorpsNumber, houseRoomNumber
        );
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
