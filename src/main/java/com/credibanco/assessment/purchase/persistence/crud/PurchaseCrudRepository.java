package com.credibanco.assessment.purchase.persistence.crud;


import com.credibanco.assessment.purchase.model.entity.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseCrudRepository extends CrudRepository<Purchase,String> {

    Purchase findTopByOrderByReferenceNumberDesc();
    int deletePurchasesByPan(String pan);
}
