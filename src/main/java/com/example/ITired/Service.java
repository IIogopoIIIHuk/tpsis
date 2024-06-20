package com.example.ITired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service") // Указывает имя таблицы в базе данных
@Data //чтобы не писать конструктор геттер, сеттер - lombok
@AllArgsConstructor //конструктор со всеми полями, которые присутствуют
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "dateService")
    private String dateService;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @Column(name = "counts")
    private String counts;

    public Service(Long id, String title, String dateService, int price, String description, String counts) {
        this.id = id;
        this.title = title;
        this.dateService = dateService;
        this.price = price;
        this.description = description;
        this.counts = counts;
    }


    private LocalDateTime dateOfCreated;

    @OneToOne(mappedBy = "service", cascade = CascadeType.ALL)
    private Images images;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private OtherClinic clinic;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }


}
