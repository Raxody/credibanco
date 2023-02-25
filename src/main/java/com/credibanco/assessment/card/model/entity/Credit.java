package com.credibanco.assessment.card.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("credito")
public class Credit extends Card{

    public Credit() {
    }

    public Credit(String pan, String owner, String id, String numberPhone, String status, LocalDateTime creationTime, Integer enrollmentNumber) {
        super(pan, owner, id, numberPhone, status, creationTime, enrollmentNumber);
    }

    public Credit(String pan, String owner, String id, String numberPhone) {
        super(pan, owner, id, numberPhone);
    }

    public Credit(String owner, String id, String numberPhone) {
        super(owner, id, numberPhone);
    }
}
