package com.example.patientaccount.doctor.dto;

import com.example.patientaccount.doctor.entity.Visit;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateVisitRequest {
    private String date;
    private String hour;
    private Integer time; // in full hours
    private Boolean consultation;  // konsultacja
    private Boolean treatment; //zabieg

    public static BiFunction<Visit, UpdateVisitRequest, Visit> dtoToEntityUpdater() {
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
