package com.sdl.delivery.content.model.core.schema;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSConfig {

    @Value("${os.scheme}")
    private String scheme;

    @Value("${os.host}")
    private String host;

    @Value("${os.port}")
    private String port;

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getEndpointUrl() {
        return scheme + "://" + host + ":" + port;
    }
}
