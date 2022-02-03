package com.example.patientaccount.doctor.dto;

import com.example.patientaccount.Specialisation;
import com.example.patientaccount.doctor.entity.Doctor;
import lombok.*;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateDoctorRequest {
    private String name;
    private String surname;
    private Specialisation specialisation;
    private Double stars;
    private Boolean registrar;

    public static Function<CreateDoctorRequest, Doctor> dtoToEntityMapper() {
        return request -> Doctor.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .specialisation(request.getSpecialisation())
                .stars(request.getStars())
                .registrar(request.getRegistrar())
                .build();
    }
}
