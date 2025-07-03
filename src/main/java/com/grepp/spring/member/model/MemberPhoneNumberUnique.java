package com.grepp.spring.member.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.grepp.spring.member.service.MemberService;
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
 * Validate that the phoneNumber value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = MemberPhoneNumberUnique.MemberPhoneNumberUniqueValidator.class
)
public @interface MemberPhoneNumberUnique {

    String message() default "{exists.member.phoneNumber}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MemberPhoneNumberUniqueValidator implements ConstraintValidator<MemberPhoneNumberUnique, String> {

        private final MemberService memberService;
        private final HttpServletRequest request;

        public MemberPhoneNumberUniqueValidator(final MemberService memberService,
                final HttpServletRequest request) {
            this.memberService = memberService;
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
            final String currentId = pathVariables.get("userId");
            if (currentId != null && value.equalsIgnoreCase(memberService.get(currentId).getPhoneNumber())) {
                // value hasn't changed
                return true;
            }
            return !memberService.phoneNumberExists(value);
        }

    }

}
