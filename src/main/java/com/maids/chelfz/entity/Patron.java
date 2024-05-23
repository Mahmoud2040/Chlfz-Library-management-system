package com.maids.chelfz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Patron implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @NotBlank
        @Size(min = 6, message = "Name must have at least 6 characters")
        String name;
        @NotBlank(message = "Email is required")
        @Email
        String email;
        @NotBlank(message = "Phone is required")
        String phone;
        @NotBlank(message = "Address is required")
        String address;
}