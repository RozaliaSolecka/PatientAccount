package com.example.patientaccount.user.dto;


import com.example.patientaccount.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

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
public class GetUsersResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {
        private String login;
        private String name;
        private String surname;
        private LocalDate birthDate;
        private Long personalIdNumber;
        private String phoneNumber;
        private Role role;
        private String email;
        private String path;
    }

    @Singular
    private List<User> users;

    public static Function<Collection<com.example.patientaccount.user.entity.User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .name(user.getName())
                            .surname(user.getSurname())
                            .birthDate(user.getBirthDate())
                            .personalIdNumber(user.getPersonalIdNumber())
                            .phoneNumber(user.getPhoneNumber())
                            .email(user.getEmail())
                            .login(user.getLogin())
                            .role(user.getRole())
                            .path(user.getPortraitPath())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}

