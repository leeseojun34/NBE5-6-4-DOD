package com.grepp.spring.candidate_date.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.candidate_date.service.CandidateDateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the date value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = CandidateDateDateUnique.CandidateDateDateUniqueValidator.class
)
public @interface CandidateDateDateUnique {

    String message() default "{exists.candidateDate.date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CandidateDateDateUniqueValidator implements ConstraintValidator<CandidateDateDateUnique, LocalDateTime> {

        private final CandidateDateService candidateDateService;
        private final HttpServletRequest request;

        public CandidateDateDateUniqueValidator(final CandidateDateService candidateDateService,
                final HttpServletRequest request) {
            this.candidateDateService = candidateDateService;
            this.request = request;
        }

        @Override
        public boolean isValid(final LocalDateTime value,
                final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("candidateDateId");
            if (currentId != null && value.equals(candidateDateService.get(Long.parseLong(currentId)).getDate())) {
                // value hasn't changed
                return true;
            }
            return !candidateDateService.dateExists(value);
        }

    }

}
