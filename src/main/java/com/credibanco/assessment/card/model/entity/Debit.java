package com.credibanco.assessment.card.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("debito")
public class Debit extends Card {

    public Debit() {
    }

    public Debit(String pan, String owner, String id, String numberPhone, String status, LocalDateTime creationTime, Integer enrollmentNumber) {
        super(pan, owner, id, numberPhone, status, creationTime, enrollmentNumber);
    }

    public Debit(String pan, String owner, String id, String numberPhone) {
        super(pan, owner, id, numberPhone);
    }

    public Debit(String owner, String id, String numberPhone) {
        super(owner, id, numberPhone);
    }
}
