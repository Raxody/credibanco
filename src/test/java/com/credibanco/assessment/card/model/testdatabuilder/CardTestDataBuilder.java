package com.credibanco.assessment.card.model.testdatabuilder;

import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.entity.Card;

public class CardTestDataBuilder {

    private String pan;
    private String owner;
    private String id;
    private String numberPhone;
    private String status;
    private String enrollmentNumber = "0";
    private String typeCard;

    public CardTestDataBuilder withPan(String pan) {
        this.pan = pan;
        return this;
    }

    public CardTestDataBuilder withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public CardTestDataBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public CardTestDataBuilder withNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
        return this;
    }

    public CardTestDataBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public CardTestDataBuilder withEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
        return this;
    }

    public CardTestDataBuilder withTypeCard(String typeCard) {
        this.typeCard = typeCard;
        return this;
    }

    public DtoCreateCard buildDtoCreateCard() {
        return new DtoCreateCard(owner, id, numberPhone, typeCard);
    }

    public DtoEnrollCard buildDtoEnrollCard() {
        return new DtoEnrollCard(pan,enrollmentNumber);
    }

    public Card build() {
        return new Card(pan, owner, id, numberPhone, status, Integer.parseInt(enrollmentNumber));
    }
}
