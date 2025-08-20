package com.sdl.delivery.content.model.core;

import com.sdl.delivery.content.graphql.schema.graphql.SchemaBuilder;
import com.sdl.delivery.content.model.core.query.XoQuery;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class XoAnnotationRegister {

    private final SchemaBuilder schemaBuilder;

    public XoAnnotationRegister(SchemaBuilder schemaBuilder) {
        this.schemaBuilder = schemaBuilder;
    }

    @PostConstruct
    public void registerXoQuery() {
        schemaBuilder.registerExtensionType(XoQuery.class);
    }
}
