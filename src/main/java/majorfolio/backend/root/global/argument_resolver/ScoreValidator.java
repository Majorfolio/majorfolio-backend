package majorfolio.backend.root.global.argument_resolver;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.ValidScore;

public class ScoreValidator implements ConstraintValidator<ValidScore, Float> {

    @Override
    public boolean isValid(Float v, ConstraintValidatorContext constraintValidatorContext) {
        if(v==null){
            return true;
        }
        String floatV = v.toString();
        String decimal = floatV.split("\\.")[1];

        return decimal.length() <= 1;
    }
}
