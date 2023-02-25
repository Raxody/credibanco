package com.credibanco.assessment.card.persistence.crud;


import com.credibanco.assessment.card.model.entity.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardCrudRepository extends CrudRepository<Card,String> {
    Card findTopByOrderByPanDesc();
}
