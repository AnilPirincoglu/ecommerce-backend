package dev.anilp.ecommerce_backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        return phoneNumber
                .matches("^(212|216|222|224|226|232|242|246|252|258|262|264|272|274|276|282|284|288|" +
                        "312|318|324|326|332|344|346|352|354|356|362|364|370|372|376|380|384|388|" +
                        "412|414|416|424|426|432|434|438|442|446|454|456|462|464|472|478|482|484|486|488|" +
                        "501|505|506|507|516|530|531|532|533|534|535|536|537|538|539|540|541|542|543|544|" +
                        "545|546|547|548|549|552|553|554|555|559|561)([0-9]{3})([0-9]{4})$");
    }
}