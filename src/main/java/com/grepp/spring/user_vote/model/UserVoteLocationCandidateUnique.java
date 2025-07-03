package com.grepp.spring.user_vote.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.user_vote.service.UserVoteService;
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
 * Validate that the locationCandidateId value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = UserVoteLocationCandidateUnique.UserVoteLocationCandidateUniqueValidator.class
)
public @interface UserVoteLocationCandidateUnique {

    String message() default "{Exists.userVote.location-candidate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UserVoteLocationCandidateUniqueValidator implements ConstraintValidator<UserVoteLocationCandidateUnique, Long> {

        private final UserVoteService userVoteService;
        private final HttpServletRequest request;

        public UserVoteLocationCandidateUniqueValidator(final UserVoteService userVoteService,
                final HttpServletRequest request) {
            this.userVoteService = userVoteService;
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
            final String currentId = pathVariables.get("userVoteId");
            if (currentId != null && value.equals(userVoteService.get(Long.parseLong(currentId)).getLocationCandidate())) {
                // value hasn't changed
                return true;
            }
            return !userVoteService.locationCandidateExists(value);
        }

    }

}
