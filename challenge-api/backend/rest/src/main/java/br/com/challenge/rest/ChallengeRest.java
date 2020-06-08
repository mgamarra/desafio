package br.com.challenge.rest;

import java.util.Collections;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.google.common.base.Predicates;

import br.com.challenge.core.base.Constants;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@SpringBootApplication(
		scanBasePackages = Constants.ROOT_PACKAGE
)
@EnableSwagger2
public class ChallengeRest {

	private static String apiPath;
	private static String mavenProfile;

	private static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(ChallengeRest.class, args);
		log.info("------------------------------------");
		log.info("Welcome to REST Api.");
		log.info("We are running on: " + apiPath);
		log.info("------------------------------------");
	}


	@Value("http://localhost:${server.port}/rest/v1/ping")
	private void setApiPath(String a) {
		apiPath = a;
	}

	@Value("${extra.activeProfile}")
	private void setMavenProfile(String a) {
		mavenProfile = a;
	}

	@Bean
	@Primary
	public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder() {
		Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();

		b.modules(new Hibernate5Module().disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING))

				.featuresToEnable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)
				.featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
				.featuresToDisable(SerializationFeature.WRAP_ROOT_VALUE)
				.serializationInclusion(JsonInclude.Include.NON_NULL)
				.failOnEmptyBeans(false)
				.failOnUnknownProperties(false);

		return b;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new LocaleResolver() {
			@Override
			public Locale resolveLocale(HttpServletRequest request) {
				return Constants.PT_BR;
			}

			@Override
			public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
				LocaleContextHolder.setLocale(locale);
			}
		};
	}

	@Bean
	@Primary
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
	@Bean
	public Docket api() {
		ApiInfo apiInfo = new ApiInfo("CHALLENGE - REST API", "", "1.0", "Terms of service",
				new Contact("Bruno Gomes", "", "brunofreiregomes@gmail.com"),
				"License of API",
				"", Collections.emptyList());


		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error")))
				.build()
				.apiInfo(apiInfo);
	}
}
