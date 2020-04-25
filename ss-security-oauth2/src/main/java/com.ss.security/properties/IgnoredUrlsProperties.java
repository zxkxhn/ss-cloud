package com.ss.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Exrickx
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security.ignored")
public class IgnoredUrlsProperties {

    private List<String> urls = new ArrayList<>();
}
