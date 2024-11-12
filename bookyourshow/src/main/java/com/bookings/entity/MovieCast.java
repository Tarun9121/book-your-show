package com.bookings.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.bookings.role.Role;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity @Table(name="casting")
@Data @NoArgsConstructor @AllArgsConstructor
public class MovieCast {
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Movie movie;
    @ManyToOne(cascade = CascadeType.ALL)
    private Actor actor;
    private String characterName;
    private Role role;
}
