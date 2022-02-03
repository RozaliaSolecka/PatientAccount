package com.example.patientaccount.doctor.dto;

import lombok.*;

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
public class GetVisitsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Visit {

        private Long id;
    }

    @Singular
    private List<Visit> visits;

    public static Function<Collection<com.example.patientaccount.doctor.entity.Visit>, GetVisitsResponse> entityToDtoMapper() {
        return visits -> {
            GetVisitsResponseBuilder response = GetVisitsResponse.builder();
            visits.stream()
                    .map(visit -> Visit.builder()
                            .id(visit.getId())
                            .build())
                    .forEach(response::visit);
            return response.build();
        };
    }
}
