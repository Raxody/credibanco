package com.credibanco.assessment.card.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Setter
@Getter
public class DtoCreateCard {
    private static final String REGEX_ONLY_NUMBERS = "^[0-9]+$";
    private static final String THE_OWNER_MUST_NOT_BE_EMPTY = "El nombre del propietario no debe ser vacio";
    private static final String THE_OWNER_MUST_ONLY_ACCEPT_LETTERS_AND_SPACES = "El nombre del propietario solo acepta letras y espacios";
    private static final String  REGEX_ONLY_LETTERS_AND_SPACES = "^[a-zA-Z\\s]+$";
    private static final String THE_ID_MUST_NOT_BE_EMPTY = "La cedula no debe estar vacia";
    private static final String THE_ID_MUST_HAVE_MINIMUM_TEN_CHARACTERS = "La cedula debe tener minimo 10 caracteres";
    private static final String THE_ID_MUST_NOT_HAVE_MORE_THAN_FIFTEEN_CHARACTERS = "La cedula debe tener maximo 15 caracteres";
    private static final String THE_ID_ONLY_ACCEPT_NUMBERS = "La cedula solo permite numeros";
    private static final String THE_NUMBER_PHONE_MUST_NOT_BE_EMPTY = "El numero de celular no debe estar vacio";
    private static final String THE_NUMBER_PHONE_ONLY_ACCEPT_NUMBERS = "El numero de celular solo permite numeros";
    private static final String THE_NUMBER_TELEPHONE_MUST_BE_TEN_CHARACTERS_LONG = "El numero de celular debe tener 10 caracteres exactos";
    private static final String THE_TYPE_OF_CARD_MUST_NOT_BE_EMPTY = "El numero de la tarjeta no debe ser vacio";
    private static final String THE_TYPE_OF_CARD_ONLY_ACCEPTS_DEBIT_OR_CREDIT = "El tipo de tarjeta solo acepta debido o credito";



    @NotBlank(message = THE_OWNER_MUST_NOT_BE_EMPTY)
    @Pattern(regexp = REGEX_ONLY_LETTERS_AND_SPACES, message = THE_OWNER_MUST_ONLY_ACCEPT_LETTERS_AND_SPACES)
    private String owner;

    @NotBlank(message = THE_ID_MUST_NOT_BE_EMPTY)
    @Length(min = 10, message = THE_ID_MUST_HAVE_MINIMUM_TEN_CHARACTERS)
    @Length(max = 15, message = THE_ID_MUST_NOT_HAVE_MORE_THAN_FIFTEEN_CHARACTERS)
    @Pattern(regexp = REGEX_ONLY_NUMBERS, message = THE_ID_ONLY_ACCEPT_NUMBERS)
    private String id;

    @NotBlank(message = THE_NUMBER_PHONE_MUST_NOT_BE_EMPTY)
    @Size(min = 10, max = 10, message = THE_NUMBER_TELEPHONE_MUST_BE_TEN_CHARACTERS_LONG)
    @Pattern(regexp = REGEX_ONLY_NUMBERS, message = THE_NUMBER_PHONE_ONLY_ACCEPT_NUMBERS)
    private String numberPhone;

    @NotBlank(message = THE_TYPE_OF_CARD_MUST_NOT_BE_EMPTY)
    @Pattern(regexp = "^(credito|debito)$",message = THE_TYPE_OF_CARD_ONLY_ACCEPTS_DEBIT_OR_CREDIT)
    private String typeCard;
}
