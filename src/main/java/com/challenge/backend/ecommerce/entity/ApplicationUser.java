package com.challenge.backend.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "app_user", schema = "ecommerce")
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name can not be blank")
    @Size(min = 2,max = 30, message = "Name must be more than 2 and less than 30 character")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Surname can not be blank")
    @Size(min = 2,max = 30, message = "Name must be more than 2 and less than 30 character")
    private String surname;

    @Column(name = "email")
    @NotBlank(message = "Email can not be blank")
    @Size(min = 10,max = 80, message = "Name must be more than 10 and less than 80 character")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password can not be blank")
    private String password;

    @Column(name = "register_date")
    private Date registerDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_role", schema = "ecommerce",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
