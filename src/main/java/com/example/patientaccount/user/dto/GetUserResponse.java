package com.example.patientaccount.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.patientaccount.user.entity.User;
import com.example.patientaccount.Role;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetUserResponse {
    private String login;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Long personalIdNumber;
    private String phoneNumber;
    private Role role;
    private String email;
    private String path;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .birthDate(user.getBirthDate())
                .personalIdNumber(user.getPersonalIdNumber())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .login(user.getLogin())
                .role(user.getRole())
                .path(user.getPortraitPath())
                .build();
    }
}
