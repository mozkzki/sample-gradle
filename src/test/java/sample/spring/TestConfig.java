package sample.spring;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

@Configuration
@ComponentScan("gradle.test.spring")
public class TestConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer setPropertySource(Environment environment) {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        ConfigurableEnvironment configurableEnvironment = ((ConfigurableEnvironment) environment);

        System.out.println(configurableEnvironment);

        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("test_param", "aaaaaaaaaaaaa");
        propertySources.addFirst(new MapPropertySource("systemProperties", map));

        propertySourcesPlaceholderConfigurer.setEnvironment(configurableEnvironment);

        String value = configurableEnvironment.getProperty("test_param");
        System.out.println(value);

        return propertySourcesPlaceholderConfigurer;
    }

}