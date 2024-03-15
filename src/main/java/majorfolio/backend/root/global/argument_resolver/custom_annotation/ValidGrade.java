package majorfolio.backend.root.global.argument_resolver.custom_annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import majorfolio.backend.root.global.argument_resolver.GradeValidator;
import majorfolio.backend.root.global.argument_resolver.ScoreValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 점수 validation annotation
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GradeValidator.class)
public @interface ValidGrade {
    String message() default "Invalid Grade";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
