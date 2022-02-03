package com.example.patientaccount.doctor.dto;

import com.example.patientaccount.Specialisation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import com.example.patientaccount.doctor.entity.Doctor;
import lombok.*;
import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetDoctorResponse {
    private String surname;
    private String name;
    private Specialisation specialisation;
    private Double stars;
    private Boolean registrar;

    public static Function<Doctor, GetDoctorResponse> entityToDtoMapper() {
        return doctor -> {
            GetDoctorResponse.GetDoctorResponseBuilder response = GetDoctorResponse.builder();
            response.surname(doctor.getSurname());
            response.name(doctor.getName());
            response.specialisation(doctor.getSpecialisation());
            response.stars(doctor.getStars());
            response.registrar(doctor.getRegistrar());

            return response.build();
        };
    }

}
