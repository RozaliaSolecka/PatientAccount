package com.example.patientaccount.doctor.model;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
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
public class VisitsModel implements Serializable {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Visit {

        private Long id;
        private Doctor doctor;

    }
    @Singular
    private List<Visit> visits;

    /**
     * @return mapper for convenient converting entity object to model object
     */
    public static Function<Collection<com.example.patientaccount.doctor.entity.Visit>, VisitsModel> entityToModelMapper() {
        return visits -> {
            VisitsModel.VisitsModelBuilder model = VisitsModel.builder();
            visits.stream()
                    .map(visit -> Visit.builder()
                            .id(visit.getId())
                            .doctor(visit.getDoctor())
                            .build())
                    .forEach(model::visit);
            return model.build();
        };
    }
}