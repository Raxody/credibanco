package com.credibanco.assessment.card.config;


import com.credibanco.assessment.card.persistence.crud.CardCrudRepository;
import com.credibanco.assessment.card.service.CreateCardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateCardService courseServiceImplementation(CardCrudRepository cardCrudRepository){
        return new CreateCardService(cardCrudRepository);
    }

}
