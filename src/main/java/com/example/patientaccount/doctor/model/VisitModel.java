package com.example.patientaccount.doctor.model;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VisitModel {
    private String date;
    private String hour;
    private Long id;
    private Integer time; // in full hours
    private Boolean consultation;  // konsultacja
    private Boolean treatment; //zabieg
    private Doctor doctor;
    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Visit, VisitModel> entityToModelMapper() {
        return visit -> VisitModel.builder()
                .date(visit.getDate())
                .hour(visit.getHour())
                .id(visit.getId())
                .time(visit.getTime())
                .consultation(visit.getConsultation())
                .treatment(visit.getTreatment())
                .doctor(visit.getDoctor()).build();
    }
}
