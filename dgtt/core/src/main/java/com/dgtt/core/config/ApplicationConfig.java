package com.dgtt.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.modelmapper.ModelMapper;
/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 *
 * @author sadfsafbhsaid
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper getModelMap() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setUseOSGiClassLoaderBridging(true)
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
        return modelMapper;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean
    public MessageSourceEn messageSourceEn() {

        return new MessageSourceEn();
    }

    /**
     * Bean read message properties
     *
     * @return
     */
    @Bean
    public MessageSourceVi messageSourceVi() {

        return new MessageSourceVi();
    }

    /**
     * Bean to custom message read from message properties
     *
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSourceEn().getMessageSource());
        return bean;
    }

    @Getter
    @Setter
    public class MessageSourceEn {

        private MessageSource messageSource;

        public MessageSourceEn() {
            ReloadableResourceBundleMessageSource bundleMessageSource
                    = new ReloadableResourceBundleMessageSource();
            bundleMessageSource.setBasename("classpath:messages_en");
            bundleMessageSource.setDefaultEncoding("UTF-8");
            this.messageSource = bundleMessageSource;
        }

        public String getMessageEn(String codeMsg) {
            return messageSource.getMessage(codeMsg, null, Locale.ENGLISH);
        }

        public String getMessageEn(String codeMsg, @Nullable Object[] args) {
            return messageSource.getMessage(codeMsg, args, Locale.ENGLISH);
        }
    }

    @Getter
    @Setter
    public class MessageSourceVi {

        private MessageSource messageSource;

        public MessageSourceVi() {
            ReloadableResourceBundleMessageSource bundleMessageSource
                    = new ReloadableResourceBundleMessageSource();
            bundleMessageSource.setBasename("classpath:messages_vi");
            bundleMessageSource.setDefaultEncoding("UTF-8");
            this.messageSource = bundleMessageSource;
        }

        public String getMessageVi(String codeMsg) {
            return messageSource.getMessage(codeMsg, null, new Locale("vi", "VN"));
        }

        public String getMessageVi(String codeMsg, @Nullable Object[] args) {
            return messageSource.getMessage(codeMsg, args, new Locale("vi", "VN"));
        }
    }

}
