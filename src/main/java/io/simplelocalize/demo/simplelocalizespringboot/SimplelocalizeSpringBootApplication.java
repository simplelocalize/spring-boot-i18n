package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@Configuration
public class SimplelocalizeSpringBootApplication implements WebMvcConfigurer
{


  public static void main(String[] args)
  {
    SpringApplication.run(SimplelocalizeSpringBootApplication.class, args);
  }

  /**
   * This bean is responsible for resolving texts from message_XX.properties files
   */
  @Bean
  public ResourceBundleMessageSource messageSource() {
    var resourceBundleMessageSource = new ResourceBundleMessageSource();
    resourceBundleMessageSource.setBasenames("i18n/messages");
    resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    resourceBundleMessageSource.setDefaultLocale(Locale.of("en"));
    resourceBundleMessageSource.setDefaultEncoding("UTF-8");
    return resourceBundleMessageSource;
  }

  /**
   * This bean is responsible for setting a default locale for webpages
   */
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(Locale.of("en"));
    return sessionLocaleResolver;
  }

  /**
   * This bean is responsible for resolving locale from 'lang' parameter in URL
   * Example:
   *    /welcome?lang=pl_PL (Polish language, messages_pl_PL.properties)
   *    /welcome?lang=es_ES (Spanish language, messages_es_ES.properties)
   */
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }

  /**
   * This method is responsible for registering localeChangeInterceptor, so it can be used in the application
   * Overrides WebMvcConfigurer.addInterceptors method
   * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry)
  {
    registry.addInterceptor(localeChangeInterceptor());
  }


}
