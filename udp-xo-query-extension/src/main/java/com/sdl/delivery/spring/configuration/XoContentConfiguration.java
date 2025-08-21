package com.sdl.delivery.spring.configuration;

import org.slf4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * XOContentModelConfiguration.
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(
        basePackages = {"com.sdl.delivery.content.model.core",
                "com.sdl.delivery.content.model.core.datafetcher",
                "com.sdl.delivery.content.model.core.query",
                "com.sdl.delivery.content.model.core.schema",
                "com.sdl.delivery.content.model.core.utils",
                "com.sdl.delivery.spring.configuration",
                "com.sdl.delivery.xo.status",
                "com.tridion.storage.namespace",
                "com.sdl.delivery.spring.provider"},
        excludeFilters = @ComponentScan.Filter(Configuration.class))
public class XoContentConfiguration {
    private static final Logger LOG = getLogger(XoContentConfiguration.class);
}
