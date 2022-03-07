package com.dgtt.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 *
 * @author Nguyen_Toan
 */
@Configuration
@EnableScheduling
public class ApplicationConfigCommon {

    private static final Resource[] DEV_PROPERTIES
            = new ClassPathResource[]{new ClassPathResource("dev-env.properties"),};

    private static final Resource[] TEST_PROPERTIES
            = new FileSystemResource[]{new FileSystemResource("./config/test-env.properties"),};

//  private static final Resource[] PROD_PROPERTIES =
//      new ClassPathResource[] {new ClassPathResource("prod-env.properties"),};
    private static final Resource[] PROD_PROPERTIES
            = new FileSystemResource[]{new FileSystemResource("./config/prod-env.properties"),};

    @Profile("dev")
    public static class DevConfig {

        private DevConfig() {

        }

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(DEV_PROPERTIES);
            return pspc;
        }
    }

    @Component
    @Profile("test")
    public static class TestConfig {

        private TestConfig() {

        }

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(TEST_PROPERTIES);

            return pspc;
        }
    }

    @Component
    @Profile("prod")
    public static class ProdConfig {

        private ProdConfig() {
        }

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(PROD_PROPERTIES);

            return pspc;
        }
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

//    @Bean
//     public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(20);
//        executor.setMaxPoolSize(100);
//        executor.setQueueCapacity(200);
//        executor.initialize();
//        return executor;
//    }
}
