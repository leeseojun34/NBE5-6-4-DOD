package com.grepp.spring.app.model.favorite_location.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.app.model.favorite_location.service.FavoriteLocationService;
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
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = FavoriteLocationMemberUnique.FavoriteLocationMemberUniqueValidator.class
)
public @interface FavoriteLocationMemberUnique {

    String message() default "{Exists.favoriteLocation.member}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class FavoriteLocationMemberUniqueValidator implements ConstraintValidator<FavoriteLocationMemberUnique, String> {

        private final FavoriteLocationService favoriteLocationService;
        private final HttpServletRequest request;

        public FavoriteLocationMemberUniqueValidator(
                final FavoriteLocationService favoriteLocationService,
                final HttpServletRequest request) {
            this.favoriteLocationService = favoriteLocationService;
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
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(favoriteLocationService.get(Long.parseLong(currentId)).getMember())) {
                // value hasn't changed
                return true;
            }
            return !favoriteLocationService.memberExists(value);
        }

    }

}
