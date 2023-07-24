package com.springEx.boilerplate1.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.Mapper;

@Entity
@Mapper
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;
}
