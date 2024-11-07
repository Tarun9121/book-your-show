package com.bookings.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity @Table(name = "theater_movie")
@Data @NoArgsConstructor @AllArgsConstructor
public class TheaterMovie {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="uuid-char")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="theater_id")
    private Theater theater;
    @OneToMany(mappedBy = "theaterMovie", cascade = CascadeType.ALL)
    private List<Show> shows = new ArrayList<>();
}
