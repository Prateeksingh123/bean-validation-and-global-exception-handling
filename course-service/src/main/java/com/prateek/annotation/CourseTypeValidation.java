package com.prateek.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseTypeValidator.class)
public @interface CourseTypeValidation {
    String message() default "Invalid course type. Must be 'LIVE' or 'RECORDED'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
