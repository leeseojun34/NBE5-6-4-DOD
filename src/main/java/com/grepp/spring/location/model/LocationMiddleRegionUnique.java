package com.grepp.spring.location.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.location.service.LocationService;
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
 * Validate that the middleRegionId value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = LocationMiddleRegionUnique.LocationMiddleRegionUniqueValidator.class
)
public @interface LocationMiddleRegionUnique {

    String message() default "{Exists.location.middle-region}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class LocationMiddleRegionUniqueValidator implements ConstraintValidator<LocationMiddleRegionUnique, Long> {

        private final LocationService locationService;
        private final HttpServletRequest request;

        public LocationMiddleRegionUniqueValidator(final LocationService locationService,
                final HttpServletRequest request) {
            this.locationService = locationService;
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
            final String currentId = pathVariables.get("locationId");
            if (currentId != null && value.equals(locationService.get(Long.parseLong(currentId)).getMiddleRegion())) {
                // value hasn't changed
                return true;
            }
            return !locationService.middleRegionExists(value);
        }

    }

}
