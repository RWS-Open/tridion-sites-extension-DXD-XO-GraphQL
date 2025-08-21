package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLField;

public class ActionSort {

    @GraphQLField
    private String name;

    @GraphQLField
    private String sortDirection;

    @GraphQLField
    private boolean isDynamic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }
}
