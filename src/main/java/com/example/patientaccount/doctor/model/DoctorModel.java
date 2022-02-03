package com.example.patientaccount.doctor.model;

import com.example.patientaccount.doctor.entity.Visit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.example.patientaccount.Specialisation;
import com.example.patientaccount.doctor.entity.Doctor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DoctorModel {
    private String name;
    private String surname;
    private Specialisation specialisation;
    //private Long id;
    private Double stars;
    private Boolean registrar;
    private List<Visit> visits;

    public static Function<Doctor, DoctorModel> entityToModelMapper() {
        return doctor -> DoctorModel.builder()
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .specialisation(doctor.getSpecialisation())
                //.id(doctor.getId())
                .stars(doctor.getStars())
                .registrar(doctor.getRegistrar())
                .visits(doctor.getVisits())
                .build();
    }

    public static BiFunction<Doctor, DoctorModel, Doctor> modelToEntityUpdater() {
        return (doctor, request) -> {
            doctor.setName(request.getName());
            doctor.setSurname(request.getSurname());
            doctor.setSpecialisation(request.getSpecialisation());
            //doctor.setId(request.getId());
            doctor.setStars(request.getStars());
            doctor.setRegistrar(request.getRegistrar());
            doctor.setVisits(request.getVisits());

            return doctor;
        };
    }
}
