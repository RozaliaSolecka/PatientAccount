package com.example.patientaccount.doctor.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import com.example.patientaccount.doctor.entity.Doctor;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetDoctorsResponse {
    @Singular
    private List<String> doctors;

    public static Function<Collection<Doctor>, GetDoctorsResponse> entityToDtoMapper() {
        return doctors -> {
            GetDoctorsResponse.GetDoctorsResponseBuilder response = GetDoctorsResponse.builder();
            doctors.stream()
                    .map(Doctor::getSurname)
                    .forEach(response::doctor);
            return response.build();
        };
    }
}
