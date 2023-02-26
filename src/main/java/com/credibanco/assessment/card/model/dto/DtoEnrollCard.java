package com.credibanco.assessment.card.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Setter
@Getter
public class DtoEnrollCard {

    private static final String THE_PAN_MUST_NOT_BE_EMPTY = "El numero de la tarjeta no debe ser vacio";
    private static final String THE_PAN_MUST_HAVE_MINIMUM_FOURTEEN_CHARACTERS = "El numero de la tarjeta debe tener minimo 14 caracteres";
    private static final String THE_PAN_MUST_NOT_HAVE_MORE_THAN_NINETEEN_CHARACTERS = "El numero de la tarjeta debe tener maximo 19 caracteres";
    private static final String REGEX_ONLY_NUMBERS = "^[0-9]+$";
    private static final String THE_PAN_ONLY_ACCEPT_NUMBERS = "El numero de la tarjeta solo permite numeros";
    private static final String THE_VALIDATION_NUMBER_MUST_NOT_BE_EMPTY = "El numero de validacion no debe ser vacio";
    private static final String THE_NUMBER_VALIDATION_MUST_HAVE_MINIMUM_TWO_CHARACTERS = "El numero de validacion debe tener minimo 2 caracteres";
    private static final String THE_NUMBER_VALIDATION_MUST_NOT_HAVE_MORE_THAN_THREE_CHARACTERS = "El numero de validacion debe tener maximo 3 caracteres";
    private static final String THE_VALIDATION_NUMBER_ONLY_ACCEPT_NUMBERS = "El numero de validacion solo permite numeros";


    @NotBlank(message = THE_PAN_MUST_NOT_BE_EMPTY)
    @Length(min = 16, message = THE_PAN_MUST_HAVE_MINIMUM_FOURTEEN_CHARACTERS)
    @Length(max = 19, message = THE_PAN_MUST_NOT_HAVE_MORE_THAN_NINETEEN_CHARACTERS)
    @Pattern(regexp = REGEX_ONLY_NUMBERS, message = THE_PAN_ONLY_ACCEPT_NUMBERS)
    private String pan;

    @NotBlank(message = THE_VALIDATION_NUMBER_MUST_NOT_BE_EMPTY)
    @Pattern(regexp = REGEX_ONLY_NUMBERS, message = THE_VALIDATION_NUMBER_ONLY_ACCEPT_NUMBERS)
    @Length(min = 2, message = THE_NUMBER_VALIDATION_MUST_HAVE_MINIMUM_TWO_CHARACTERS)
    @Length(max = 3, message = THE_PAN_MUST_NOT_HAVE_MORE_THAN_NINETEEN_CHARACTERS)
    private String numberValidation;
}
