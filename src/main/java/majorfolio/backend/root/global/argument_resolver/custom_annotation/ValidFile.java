package majorfolio.backend.root.global.argument_resolver.custom_annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import majorfolio.backend.root.global.argument_resolver.MultipartFileValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 파일 validation annotation
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileValidator.class)
public @interface ValidFile {
    String message() default "Invalid File";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
