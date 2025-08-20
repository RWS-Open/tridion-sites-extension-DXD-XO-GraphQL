package com.sdl.delivery.content.model.core.schema;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

/**
 * Represents XO Promotions.
 */
@GraphQLDescription("Represents XO Promotions.")
@GraphQLName("XoPromotions")
public class XoPromotions {

    @GraphQLField
    private Hits hits;

    public XoPromotions(Hits hits) {
        this.hits = hits;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }
}
