package dev.anilp.ecommerce_backend.entity.address;

import dev.anilp.ecommerce_backend.entity.BaseEntity;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.validation.PostalCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Address type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false, length = 6)
    private AddressType addressType;

    @NotBlank(message = "Address line cannot be blank")
    @Size(min = 10, max = 255, message = "Address line must be between 10 and 255 characters")
    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @NotBlank(message = "Street cannot be blank")
    @Size(min = 5, max = 50, message = "Street must be between 5 and 50 characters")
    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @NotBlank(message = "District cannot be blank")
    @Size(min = 3, max = 50, message = "District must be between 5 and 50 characters")
    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @NotBlank(message = "City cannot be blank")
    @Size(min = 3, max = 50, message = "City must be between 5 and 50 characters")
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @NotBlank(message = "Postal code cannot be blank")
    @PostalCode
    @Column(name = "postal_code", nullable = false, length = 5)
    private String postalCode;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address address)) {
            return false;
        }
        return Objects.equals(id, address.id) && addressType == address.addressType && Objects.equals(addressLine, address.addressLine) && Objects.equals(street, address.street) && Objects.equals(district, address.district) && Objects.equals(city, address.city) && Objects.equals(postalCode, address.postalCode) && Objects.equals(user, address.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addressType, addressLine, street, district, city, postalCode, user);
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", addressType=" + addressType +
                ", addressLine='" + addressLine + '\'' +
                ", street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", userId=" + user.getId() +
                '}';
    }
}