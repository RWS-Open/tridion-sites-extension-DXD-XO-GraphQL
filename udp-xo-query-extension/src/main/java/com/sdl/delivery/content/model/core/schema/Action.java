package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLField;

import java.util.List;

public class Action {

    @GraphQLField
    private ActionSort actionSort;

    @GraphQLField
    private String originalQuery;

    @GraphQLField
    private List<ActionItem> actionItems;

    public ActionSort getActionSort() {
        return actionSort;
    }

    public void setActionSort(ActionSort actionSort) {
        this.actionSort = actionSort;
    }

    public String getOriginalQuery() {
        return originalQuery;
    }

    public void setOriginalQuery(String originalQuery) {
        this.originalQuery = originalQuery;
    }

    public List<ActionItem> getActionItems() {
        return actionItems;
    }

    public void setActionItems(List<ActionItem> actionItems) {
        this.actionItems = actionItems;
    }
}
