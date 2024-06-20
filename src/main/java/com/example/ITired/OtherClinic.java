package com.example.ITired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "other_clinic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherClinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "services")
    private String services; // Возможно, стоит создать отдельную сущность для услуг

    @Column(name = "prices")
    private String prices;
}
