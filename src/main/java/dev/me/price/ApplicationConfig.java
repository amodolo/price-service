package dev.me.price;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Configuration
public class ApplicationConfig {

    @Configuration
    protected static class OpenApiConfiguration {

        @Bean
        @ConditionalOnMissingBean
        OpenAPI apiInfo() {
            return new OpenAPI().info(getInfo());
        }

        private Info getInfo() {
            return new Info().title("Price domain")
                    .description("API catalog for the resources exposed by price microservice")
                    .version("1.0.0");
        }
    }

    @RestControllerAdvice
    protected static class ConstraintExceptionHandler {

        @ResponseBody
        @ResponseStatus(BAD_REQUEST)
        @ExceptionHandler(ConstraintViolationException.class)
        public void handleConstraintViolationException(ConstraintViolationException exception,
                                                       ServletWebRequest webRequest) {
            // status code handled by @ResponseStatus
        }
    }
}
