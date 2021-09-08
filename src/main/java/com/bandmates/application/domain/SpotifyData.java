package com.bandmates.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topGenre;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Profile profile;
}
