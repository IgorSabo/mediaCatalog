package com.catalog.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
/*@PropertySources({
  @PropertySource(name = "test config from classpath", value = "classpath:/conf/test.properties")
})*/
public class TestConfig {
  
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    return messageSource;
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder;
  }

  @Bean
  PropertyPlaceholderConfigurer propConfig() {
    PropertyPlaceholderConfigurer ppc =  new PropertyPlaceholderConfigurer();
    ppc.setLocation(new ClassPathResource("test.properties"));
    return ppc;
  }

}
