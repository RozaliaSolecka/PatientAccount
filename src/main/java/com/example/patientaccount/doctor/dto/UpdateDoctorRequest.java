package com.example.patientaccount.doctor.dto;

import com.example.patientaccount.Specialisation;
import com.example.patientaccount.doctor.entity.Doctor;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateDoctorRequest {
    private Specialisation specialisation;
    private Double stars;
    private Boolean registrar;

    public static BiFunction<Doctor, UpdateDoctorRequest, Doctor> dtoToEntityUpdater() {
        return (doctor, request) -> {
            doctor.setSpecialisation(request.getSpecialisation());
            doctor.setStars(request.getStars());
            doctor.setRegistrar(request.getRegistrar());
            return doctor;
        };
    }
}
