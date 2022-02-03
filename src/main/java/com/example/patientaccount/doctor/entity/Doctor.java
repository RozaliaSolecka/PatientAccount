package com.example.patientaccount.doctor.entity;

import com.example.patientaccount.Specialisation;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "doctors")
public class Doctor implements Serializable {
    private String name;
    @Id
    private String surname;
    private Specialisation specialisation;
    private Double stars;
    private Boolean registrar;
    /**
     * List of doctor's visits.
     */

    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Visit> visits;
}
