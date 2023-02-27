package com.credibanco.assessment.purchase.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {


    @Id
    @Column(name = "reference_number", length = 6)
    private String referenceNumber;
    @Column(name = "pan", length = 19)
    private String pan;
    @Column(name = "purchase_value")
    private Long purchaseValue;
    @Column(name = "purchase_address", length = 50)
    private String purchaseAddress;
    @Column(name = "status_buy", length = 12)
    private String status;
    @Column(name = "time_purchase")
    private LocalDateTime timePurchase;

    public Purchase( String pan, Long purchaseValue, String purchaseAddress, String status, LocalDateTime timePurchase) {
        this.pan = pan;
        this.purchaseValue = purchaseValue;
        this.purchaseAddress = purchaseAddress;
        this.status = status;
        this.timePurchase = timePurchase;
    }
}
