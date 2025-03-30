package com.example.demo.App.Board.domain;

import com.example.demo.App.Auth.domain.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

}
