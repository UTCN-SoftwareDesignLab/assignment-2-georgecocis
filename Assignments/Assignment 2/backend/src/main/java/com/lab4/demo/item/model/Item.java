package com.lab4.demo.item.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    private String title;

    @Column(length = 64, nullable = false)
    private String author;

    @Column(length = 20, nullable = false)
    private String genre;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float price;
}
