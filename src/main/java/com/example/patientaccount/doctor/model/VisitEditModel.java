package com.example.patientaccount.doctor.model;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VisitEditModel {
    private String date;
    private String hour;
    private Integer time; // in full hours
    private Boolean consultation;  // konsultacja
    private Boolean treatment; //zabieg

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Visit, VisitEditModel> entityToModelMapper() {
        return visit -> VisitEditModel.builder()
                .date(visit.getDate())
                .hour(visit.getHour())
                .time(visit.getTime())
                .consultation(visit.getConsultation())
                .treatment(visit.getTreatment())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Visit, VisitEditModel, Visit> modelToEntityUpdater() {
        return (visit, request) -> {
            visit.setDate(request.getDate());
            visit.setHour(request.getHour());
            visit.setTime(request.getTime());
            visit.setConsultation(request.getConsultation());
            visit.setTreatment(request.getTreatment());
            return visit;
        };
    }
}
