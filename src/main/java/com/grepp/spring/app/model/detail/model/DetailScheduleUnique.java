package com.grepp.spring.app.model.detail.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.app.model.detail.service.DetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the scheduleId value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = DetailScheduleUnique.DetailScheduleUniqueValidator.class
)
public @interface DetailScheduleUnique {

    String message() default "{Exists.detail.schedule}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DetailScheduleUniqueValidator implements ConstraintValidator<DetailScheduleUnique, Long> {

        private final DetailService detailService;
        private final HttpServletRequest request;

        public DetailScheduleUniqueValidator(final DetailService detailService,
                final HttpServletRequest request) {
            this.detailService = detailService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("detailId");
            if (currentId != null && value.equals(detailService.get(Long.parseLong(currentId)).getSchedule())) {
                // value hasn't changed
                return true;
            }
            return !detailService.scheduleExists(value);
        }

    }

}
