package com.example.patientaccount.doctor.dto;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
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
public class CreateVisitRequest {
    private String date;
    private String hour;
    private Integer time; // in full hours
    private Boolean consultation;  // konsultacja
    private Boolean treatment; //zabieg

    public static Function<CreateVisitRequest, Visit> dtoToEntityMapper() {
        return request -> Visit.builder()
                .date(request.getDate())
                .hour(request.getHour())
                .time(request.getTime())
                .consultation(request.getConsultation())
                .treatment(request.getTreatment())
                .build();
    }
}
