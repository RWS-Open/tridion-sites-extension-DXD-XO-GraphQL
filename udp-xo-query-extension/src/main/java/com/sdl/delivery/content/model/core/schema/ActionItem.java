package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLField;

public class ActionItem {

    @GraphQLField
    private String componentId;

    @GraphQLField
    private String componentTemplateId;

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getComponentTemplateId() {
        return componentTemplateId;
    }

    public void setComponentTemplateId(String componentTemplateId) {
        this.componentTemplateId = componentTemplateId;
    }
}
