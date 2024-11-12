package com.bookings.entity;

import com.bookings.role.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.Year;
import java.util.UUID;

@Entity @Table(name = "actors")
@Data @NoArgsConstructor @AllArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="uuid-char")
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String nationality;
    private String biography;
    private Gender gender;
    private Year debutYear;
}
