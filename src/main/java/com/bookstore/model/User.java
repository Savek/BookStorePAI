package com.bookstore.model;

import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Savek on 2017-04-15.
 */

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Column(unique = true)
    @NotNull
    private String login;
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private Boolean enabled;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public User(String name, String surname, String login, String password, String email, Boolean enabled, Role role) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }
}
