package com.example.patientaccount.doctor.model;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import lombok.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VisitCreateModel {
    private String date;
    private String hour;
    private Long id;
    private Integer time; // in full hours
    private Boolean consultation;  // konsultacja
    private Boolean treatment; //zabieg
    private DoctorModel doctorModel;

    public static Function<VisitCreateModel, Visit> modelToEntityMapper(
            Function<String, Doctor> doctorFunction) {
        return visit -> Visit.builder()
                .date(visit.getDate())
                .hour(visit.getHour())
                .id(visit.getId())
                .time(visit.getTime())
                .consultation(visit.getConsultation())
                .treatment(visit.getTreatment())
                //.doctor(doctorFunction.apply(visit.getDoctorModel().getId())).build();
                .doctor(doctorFunction.apply(visit.getDoctorModel().getSurname())).build();
    }
}
