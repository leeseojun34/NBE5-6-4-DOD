package com.grepp.spring.app.model.social_auth_tokens.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.app.model.social_auth_tokens.service.SocialAuthTokensService;
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
 * Validate that the refreshToken value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = SocialAuthTokensRefreshTokenUnique.SocialAuthTokensRefreshTokenUniqueValidator.class
)
public @interface SocialAuthTokensRefreshTokenUnique {

    String message() default "{exists.socialAuthTokens.refreshToken}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SocialAuthTokensRefreshTokenUniqueValidator implements ConstraintValidator<SocialAuthTokensRefreshTokenUnique, String> {

        private final SocialAuthTokensService socialAuthTokensService;
        private final HttpServletRequest request;

        public SocialAuthTokensRefreshTokenUniqueValidator(
                final SocialAuthTokensService socialAuthTokensService,
                final HttpServletRequest request) {
            this.socialAuthTokensService = socialAuthTokensService;
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
            if (currentId != null && value.equalsIgnoreCase(socialAuthTokensService.get(Long.parseLong(currentId)).getRefreshToken())) {
                // value hasn't changed
                return true;
            }
            return !socialAuthTokensService.refreshTokenExists(value);
        }

    }

}
