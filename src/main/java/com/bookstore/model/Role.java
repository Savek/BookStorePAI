package com.bookstore.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Savek on 2017-04-16.
 */
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    String role;

    @Getter public enum RoleName {
        ROLE_ADMIN(1L, "ROLE_ADMIN"),
        ROLE_EMPLOYED(2L, "ROLE_EMPLOYED"),
        ROLE_MEMBER(3L, "ROLE_MEMBER");

        private Long id;
        private String roleName;

        RoleName(Long id, String roleName) {
            this.id = id;
            this.roleName = roleName;
        }
    }
}
