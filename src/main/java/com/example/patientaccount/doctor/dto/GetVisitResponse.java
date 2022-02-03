package com.example.patientaccount.doctor.dto;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetVisitResponse {
    private String date;
    private String hour;
    private Long id;
    private Integer time; // in full hours
    private Boolean consultation;  // konsultacja
    private Boolean treatment; //zabieg

    public static Function<Visit, GetVisitResponse> entityToDtoMapper() {
        return visit -> GetVisitResponse.builder()
                .date(visit.getDate())
                .hour(visit.getHour())
                .id(visit.getId())
                .time(visit.getTime())
                .consultation(visit.getConsultation())
                .treatment(visit.getTreatment())
                .build();
    }
}
