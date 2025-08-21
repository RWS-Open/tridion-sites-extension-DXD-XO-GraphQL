package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLField;

public class PromotionStatus {

    @GraphQLField
    private String id;

    @GraphQLField
    private String name;

    @GraphQLField
    private String state;

    @GraphQLField
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
