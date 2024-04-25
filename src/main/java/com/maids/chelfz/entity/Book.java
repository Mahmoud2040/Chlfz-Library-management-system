package com.maids.chelfz.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank
        private String title;
        @NotBlank
        private String author;
        @Column(name = "publication_year")
        private Integer  publicationYear;
        @NotBlank
        private String isbn;
        @Column(name = "is_borrowed", columnDefinition = "BOOLEAN DEFAULT FALSE")
        private Boolean isBorrowed = false;


}
