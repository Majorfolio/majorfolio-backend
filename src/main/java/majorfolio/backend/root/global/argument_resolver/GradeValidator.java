package majorfolio.backend.root.global.argument_resolver;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.ValidGrade;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.ValidScore;

public class GradeValidator implements ConstraintValidator<ValidGrade, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.isEmpty()){
            return true;
        }
        //학점 정규식
        String gradeExp = "A[+-]?|B[+-]?|C[+-]?|D[+-]?|F|P|NP";
        return s.matches(gradeExp);
    }
}
