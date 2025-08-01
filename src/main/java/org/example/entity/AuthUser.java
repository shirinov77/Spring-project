package org.example.entity;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
