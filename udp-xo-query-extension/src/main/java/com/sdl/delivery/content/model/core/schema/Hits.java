package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.List;

@GraphQLName("Hits")
public class Hits {
    @GraphQLField
    private Total total;
    @GraphQLField
    private List<HitItem> hits;

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public List<HitItem> getHits() {
        return hits;
    }

    public void setHits(List<HitItem> hits) {
        this.hits = hits;
    }
}
