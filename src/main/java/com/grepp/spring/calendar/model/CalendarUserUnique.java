package com.grepp.spring.calendar.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.calendar.service.CalendarService;
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
 * Validate that the userId value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = CalendarUserUnique.CalendarUserUniqueValidator.class
)
public @interface CalendarUserUnique {

    String message() default "{Exists.calendar.user}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CalendarUserUniqueValidator implements ConstraintValidator<CalendarUserUnique, String> {

        private final CalendarService calendarService;
        private final HttpServletRequest request;

        public CalendarUserUniqueValidator(final CalendarService calendarService,
                final HttpServletRequest request) {
            this.calendarService = calendarService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("calendarId");
            if (currentId != null && value.equalsIgnoreCase(calendarService.get(Long.parseLong(currentId)).getUser())) {
                // value hasn't changed
                return true;
            }
            return !calendarService.userExists(value);
        }

    }

}
