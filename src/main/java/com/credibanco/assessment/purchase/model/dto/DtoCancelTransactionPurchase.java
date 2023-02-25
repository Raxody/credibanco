package com.credibanco.assessment.purchase.model.dto;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DtoCancelTransactionPurchase {

    private static final String THE_PAN_MUST_NOT_BE_EMPTY = "El numero de la tarjeta no debe ser vacio";
    private static final String THE_PAN_MUST_HAVE_MINIMUM_FOURTEEN_CHARACTERS = "El numero de la tarjeta debe tener minimo 14 caracteres";
    private static final String THE_PAN_MUST_NOT_HAVE_MORE_THAN_NINETEEN_CHARACTERS = "El numero de la tarjeta debe tener maximo 19 caracteres";
    private static final String REGEX_ONLY_NUMBERS = "^[0-9]+$";
    private static final String THE_PAN_ONLY_ACCEPT_NUMBERS = "El numero de la tarjeta solo permite numeros";
    private static final String THE_PURCHASE_VALUE_MUST_NOT_BE_EMPTY = "El valor de la compra no puede ser vacio";
    private static final String THE_PURCHASE_VALUE_ONLY_ACCEPT_NUMBERS = "El valor de la compra solo permite numeros";
    private static final String THE_REFERENCE_NUMBER_MUST_BE_THREE_CHARACTERS_LONG = "El numero de referencia debe tener 3 caracteres exactos";
    private static final String THE_REFERENCE_NUMBER_MUST_NOT_BE_EMPTY = "El numero de referencia no puede ser vacio";
    private static final String THE_REFERENCE_NUMBER_ONLY_ACCEPT_NUMBERS = "El numero de referencia solo permite numeros";

    @NotBlank(message = THE_PAN_MUST_NOT_BE_EMPTY)
    @Length(min = 16, message = THE_PAN_MUST_HAVE_MINIMUM_FOURTEEN_CHARACTERS)
    @Length(max = 19, message = THE_PAN_MUST_NOT_HAVE_MORE_THAN_NINETEEN_CHARACTERS)
    @Pattern(regexp = REGEX_ONLY_NUMBERS, message = THE_PAN_ONLY_ACCEPT_NUMBERS)
    private String pan;
    @NotBlank(message = THE_PURCHASE_VALUE_MUST_NOT_BE_EMPTY)
    @Pattern(regexp = REGEX_ONLY_NUMBERS, message = THE_PURCHASE_VALUE_ONLY_ACCEPT_NUMBERS )
    private String purchaseValue;
    @NotBlank(message = THE_REFERENCE_NUMBER_MUST_NOT_BE_EMPTY )
    @Size(min = 3, max = 3, message = THE_REFERENCE_NUMBER_MUST_BE_THREE_CHARACTERS_LONG)
    @Pattern(regexp = REGEX_ONLY_NUMBERS, message = THE_REFERENCE_NUMBER_ONLY_ACCEPT_NUMBERS)
    private String referenceNumber;


}
