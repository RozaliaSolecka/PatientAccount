package com.example.patientaccount.doctor.model;

import com.example.patientaccount.Specialisation;
import com.example.patientaccount.doctor.entity.Doctor;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DoctorsModel implements Serializable {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Doctor {

        //private Long id;
        //private String name;
        private String surname;
        private  String name;

    }
    @Singular
    private List<Doctor> doctors;

    /**
     * @return mapper for convenient converting entity object to model object
     */
    public static Function<Collection<com.example.patientaccount.doctor.entity.Doctor>, DoctorsModel> entityToModelMapper() {
        return doctors -> {
            DoctorsModel.DoctorsModelBuilder model = DoctorsModel.builder();
            doctors.stream()
                    .map(doctor -> DoctorsModel.Doctor.builder()
                            //.id(doctor.getId())
                            //.name(doctor.getName())
                            .surname(doctor.getSurname())
                            .name(doctor.getName())
                            .build())
                    .forEach(model::doctor);
            return model.build();
        };
    }
}
