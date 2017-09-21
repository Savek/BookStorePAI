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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String description;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date publicationDate;

    @Column(columnDefinition = "int default 0")
    @NotNull
    private Long status;

    @NotNull
    private String author;

    @ManyToOne
    private User borrower;

}
