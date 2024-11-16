package com.bookings.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity @Table(name="users")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="uuid-char")
    private UUID id;
    private String fullName;
    private String email;
    private String mobileNo;
    @Column(nullable = false)
    private boolean deleted = false; // Soft delete flag

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Booking> bookingList = new ArrayList<>();
}
