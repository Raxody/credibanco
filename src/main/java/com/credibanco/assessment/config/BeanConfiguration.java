package com.credibanco.assessment.config;


import com.credibanco.assessment.card.persistence.crud.CardCrudRepository;
import com.credibanco.assessment.card.repository.CardRepository;
import com.credibanco.assessment.card.service.CardService;
import com.credibanco.assessment.purchase.persistence.crud.PurchaseCrudRepository;
import com.credibanco.assessment.purchase.service.PurchaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
