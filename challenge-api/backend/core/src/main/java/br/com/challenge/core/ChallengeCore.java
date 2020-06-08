package br.com.challenge.core;

import java.security.SecureRandom;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;

import br.com.challenge.core.base.Constants;
import br.com.challenge.core.base.repository.CustomJpaRepository;

@Configuration
@EnableAsync
@PropertySource("classpath:application.properties")
@EntityScan(basePackages = Constants.ENTITY_PACKAGE)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = Constants.REPOSITORY_PACKAGE,
        repositoryImplementationPostfix = "CustomImpl",
        repositoryBaseClass = CustomJpaRepository.class
)
public class ChallengeCore {

    @Value("${extra.security.hashids.salt}")
    private String hashidsSalt;

    @Value("${extra.security.hashids.lenght}")
    private Integer hashidsLenght;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public PasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }


    /*
     * Security
     */

    @Bean
    public Hashids hashids() {
        return new Hashids(hashidsSalt, hashidsLenght);
    }

    /*
     * Validation
     */

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /*
     * WS
     */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
