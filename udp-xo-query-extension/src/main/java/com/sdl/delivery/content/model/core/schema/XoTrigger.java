package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLField;

public class XoTrigger {

    @GraphQLField
    private String name;

    @GraphQLField
    private String operator;

    @GraphQLField
    private boolean isScopeTrigger;

    @GraphQLField
    private TriggerValues triggerValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isScopeTrigger() {
        return isScopeTrigger;
    }

    public void setScopeTrigger(boolean scopeTrigger) {
        isScopeTrigger = scopeTrigger;
    }

    public TriggerValues getTriggerValues() {
        return triggerValues;
    }

    public void setTriggerValues(TriggerValues triggerValues) {
        this.triggerValues = triggerValues;
    }
}
