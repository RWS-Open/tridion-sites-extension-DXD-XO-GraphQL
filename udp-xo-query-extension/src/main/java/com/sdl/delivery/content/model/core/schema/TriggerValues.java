package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLField;

import java.util.List;

public class TriggerValues {

    @GraphQLField
    private List<String> stringValues;

    @GraphQLField
    private List<Long> longValues;

    public List<String> getStringValues() {
        return stringValues;
    }

    public void setStringValues(List<String> stringValues) {
        this.stringValues = stringValues;
    }

    public List<Long> getLongValues() {
        return longValues;
    }

    public void setLongValues(List<Long> longValues) {
        this.longValues = longValues;
    }
}
