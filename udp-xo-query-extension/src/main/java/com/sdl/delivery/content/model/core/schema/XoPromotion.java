package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.List;

/**
 * Represents XO Promotion.
 */
@GraphQLDescription("Represents XO Promotion.")
@GraphQLName("XoPromotion")
public class XoPromotion {

    @GraphQLField
    private Boolean found;

    @GraphQLField
    private String id;

    @GraphQLField
    private String name;

    @GraphQLField
    private String scopePublication;

    @GraphQLField
    private String instanceId;

    @GraphQLField
    private String createdDate;

    @GraphQLField
    private String modifiedDate;

    @GraphQLField
    private String state;

    @GraphQLField
    private List<XoTrigger> triggers;

    @GraphQLField
    private Action action;

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

    public String getScopePublication() {
        return scopePublication;
    }

    public void setScopePublication(String scopePublication) {
        this.scopePublication = scopePublication;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<XoTrigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<XoTrigger> triggers) {
        this.triggers = triggers;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
