package dev.me.price;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationConfigTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(ApplicationConfig.class);

    @Test
    @DisplayName("given an empty context, when the context starts should register OpenApi bean")
    void givenEmptyContext_whenContextRuns_shouldRegisterOpenAPIBean() {
        // when
        runner.run(context -> {
            // then
            assertThat(context).hasSingleBean(OpenAPI.class);
        });
    }

    @Test
    @DisplayName("given an empty context, when the context starts should register OpenApi bean with expected properties set")
    void givenEmptyContext_whenContextRuns_shouldRegisterOpenAPIBeanWithExpectedPropertiesSet() {
        // given
        runner.run(context -> {
            // when
            var actual = context.getBean(OpenAPI.class);
            // then
            assertThat(actual.getInfo())
                    .hasFieldOrPropertyWithValue("title", "Price domain")
                    .hasFieldOrPropertyWithValue("description", "API catalog for the resources exposed by price microservice")
                    .hasFieldOrPropertyWithValue("version", "1.0.0");
        });
    }

    @Test
    @DisplayName("given a context with another OpenAPI bean, when the context starts should not register further OpenAPI bean")
    void givenContextWithOpenAPIBean_whenContextRuns_shouldNotRegisterFurtherOpenAPIBean() {
        // given - when
        runner.withBean(OpenAPI.class).run(context -> {
            // then
            assertThat(context).hasSingleBean(OpenAPI.class);
        });
    }
}