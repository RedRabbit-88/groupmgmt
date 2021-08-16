package com.app.domain.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Address {

    private String city;
    private String district;
    private String zipCode;

    public Address() {
    }

    @Builder
    public Address(String city, String district, String zipCode) {
        this.city = city;
        this.district = district;
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity())
                && Objects.equals(getDistrict(), address.getDistrict())
                && Objects.equals(getZipCode(), address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getDistrict(), getZipCode());
    }
}
