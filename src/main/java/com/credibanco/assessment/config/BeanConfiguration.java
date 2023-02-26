package com.credibanco.assessment.config;


import com.credibanco.assessment.card.persistence.crud.CardCrudRepository;
import com.credibanco.assessment.card.repository.CardRepository;
import com.credibanco.assessment.card.service.CardService;
import com.credibanco.assessment.purchase.persistence.crud.PurchaseCrudRepository;
import com.credibanco.assessment.purchase.service.PurchaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BeanConfiguration {
    @Bean
    public CardService courseServiceImplementation(@Lazy CardCrudRepository cardCrudRepository,@Lazy PurchaseService purchaseService){
        return new CardService(cardCrudRepository, purchaseService);
    }

    @Bean
    public PurchaseService purchaseServiceImplementation(@Lazy PurchaseCrudRepository purchaseCrudRepository,@Lazy CardRepository cardRepository){
        return new PurchaseService(purchaseCrudRepository,cardRepository);
    }
}
