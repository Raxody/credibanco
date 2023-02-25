package com.credibanco.assessment.card.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_card", length = 10)
@Getter
@Setter
@NoArgsConstructor
public class Card {

    @Id
    @Column(name = "pan", length = 19)
    private String pan;
    @Column(name = "owner", length = 50)
    private String owner;
    @Column(name = "id", length = 15)
    private String id;
    @Column(name = "number_phone", length = 10)
    private String numberPhone;
    @Column(name = "status", length = 10)
    private String status;
    private LocalDateTime creationTime;
    private Integer enrollmentNumber;

    public Card(String pan, String owner, String id, String numberPhone, String status, LocalDateTime creationTime, Integer enrollmentNumber) {
        this.pan = pan;
        this.owner = owner;
        this.id = id;
        this.numberPhone = numberPhone;
        this.status = status;
        this.creationTime = creationTime;
        this.enrollmentNumber = enrollmentNumber;
    }

    public Card(String pan, String owner, String id, String numberPhone) {
        this.pan = pan;
        this.owner = owner;
        this.id = id;
        this.numberPhone = numberPhone;
    }

    public Card(String owner, String id, String numberPhone) {
        this.owner = owner;
        this.id = id;
        this.numberPhone = numberPhone;
    }

}
