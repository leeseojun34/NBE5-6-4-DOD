package com.grepp.spring.location_candidate.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.location_candidate.service.LocationCandidateService;
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
 * Validate that the locationName value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = LocationCandidateLocationNameUnique.LocationCandidateLocationNameUniqueValidator.class
)
public @interface LocationCandidateLocationNameUnique {

    String message() default "{exists.locationCandidate.locationName}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class LocationCandidateLocationNameUniqueValidator implements ConstraintValidator<LocationCandidateLocationNameUnique, String> {

        private final LocationCandidateService locationCandidateService;
        private final HttpServletRequest request;

        public LocationCandidateLocationNameUniqueValidator(
                final LocationCandidateService locationCandidateService,
                final HttpServletRequest request) {
            this.locationCandidateService = locationCandidateService;
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
            final String currentId = pathVariables.get("locationCandidateId");
            if (currentId != null && value.equalsIgnoreCase(locationCandidateService.get(Long.parseLong(currentId)).getLocationName())) {
                // value hasn't changed
                return true;
            }
            return !locationCandidateService.locationNameExists(value);
        }

    }

}
