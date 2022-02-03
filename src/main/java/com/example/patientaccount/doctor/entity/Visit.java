package com.example.patientaccount.doctor.entity;

import com.example.patientaccount.doctor.model.DoctorsModel;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString()
@EqualsAndHashCode()
@Entity
@Table(name = "visits")
public class Visit implements Serializable {
    private String date;
    private String hour;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Integer time; // in full hours
    private Boolean consultation;  // konsultacja
    private Boolean treatment; //zabieg

    @ManyToOne
    @JoinColumn(name = "doctor")
    private Doctor doctor;
}
