package com.grepp.spring.app.model.like_location.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.app.model.like_location.service.LikeLocationService;
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
        validatedBy = LikeLocationUserUnique.LikeLocationUserUniqueValidator.class
)
public @interface LikeLocationUserUnique {

    String message() default "{Exists.likeLocation.user}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class LikeLocationUserUniqueValidator implements ConstraintValidator<LikeLocationUserUnique, String> {

        private final LikeLocationService likeLocationService;
        private final HttpServletRequest request;

        public LikeLocationUserUniqueValidator(final LikeLocationService likeLocationService,
                final HttpServletRequest request) {
            this.likeLocationService = likeLocationService;
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
            final String currentId = pathVariables.get("likeLocationId");
            if (currentId != null && value.equalsIgnoreCase(likeLocationService.get(Long.parseLong(currentId)).getUser())) {
                // value hasn't changed
                return true;
            }
            return !likeLocationService.userExists(value);
        }

    }

}
