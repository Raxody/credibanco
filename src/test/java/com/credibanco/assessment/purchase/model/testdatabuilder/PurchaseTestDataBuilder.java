package com.credibanco.assessment.purchase.model.testdatabuilder;
import com.credibanco.assessment.purchase.model.dto.DtoCancelTransactionPurchase;
import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.model.entity.Purchase;
import java.time.LocalDateTime;

public class PurchaseTestDataBuilder {
    private String referenceNumber;
    private String pan;
    private String purchaseValue = "0";
    private String purchaseAddress;
    private String status;
    private LocalDateTime timePurchase;


    public PurchaseTestDataBuilder withPan(String pan) {
        this.pan = pan;
        return this;
    }

    public PurchaseTestDataBuilder withPurchaseValue(String purchaseValue) {
        this.purchaseValue = purchaseValue;
        return this;
    }


    public PurchaseTestDataBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public PurchaseTestDataBuilder withPurchaseAddress(String purchaseAddress) {
        this.purchaseAddress = purchaseAddress;
        return this;
    }

    public PurchaseTestDataBuilder withTimePurchase(LocalDateTime timePurchase) {
        this.timePurchase = timePurchase;
        return this;
    }

    public PurchaseTestDataBuilder withReferenceNumber(String referenceNumber) {
        this.referenceNumber= referenceNumber;
        return this;
    }


    public DtoCreatePurchase buildDtoCreatePurchase() {
        return new DtoCreatePurchase(pan, purchaseValue, purchaseAddress);
    }


    public DtoCancelTransactionPurchase buildDtoCancelTransactionPurchase() {
        return new DtoCancelTransactionPurchase(pan, purchaseValue, referenceNumber);
    }

    public Purchase build(){
        return new Purchase(referenceNumber, pan, Long.parseLong(purchaseValue), purchaseAddress,  status, timePurchase);
    }
}
