package space.efremov.searchrequestlogger.model;

import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchRequestValidationTest {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    public void shouldNotValidateWhenUidEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        SearchRequest request = new SearchRequest(Instant.now().getEpochSecond(), "", "search request", 12.1d, 123.3d, null);
        Validator validator = createValidator();
        Set<ConstraintViolation<SearchRequest>> constraintViolations = validator.validate(request);
        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<SearchRequest> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("uid");
        assertThat(violation.getMessage()).isEqualTo("must not be blank");
    }


}
