package com.sdl.delivery.content.model.core.query;

import com.sdl.delivery.content.model.core.datafetcher.GetActivePromotionDataFetcher;
import com.sdl.delivery.content.model.core.datafetcher.GetXoDetailsDataFetcher;
import com.sdl.delivery.content.model.core.datafetcher.XoPromotionDataFetcher;
import com.sdl.delivery.content.model.core.schema.PromotionStatus;
import com.sdl.delivery.content.model.core.schema.XoPromotion;
import com.sdl.delivery.content.model.core.schema.XoPromotions;
import graphql.annotations.annotationTypes.GraphQLDataFetcher;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLTypeExtension;
import org.slf4j.Logger;

import static com.sdl.delivery.content.model.core.utils.XOAPIConstants.PROMOTION_ID;
import static org.slf4j.LoggerFactory.getLogger;

@GraphQLTypeExtension(ContentQuery.class)
public class XoQuery {

    private static final Logger LOG = getLogger(XoQuery.class);

    @GraphQLField
    @GraphQLDescription("XO Promotion")
    @GraphQLDataFetcher(XoPromotionDataFetcher.class)
    public XoPromotions xoPromotion() {
        LOG.info("XO Promotion Method Executed");
        return null;
    }

    @GraphQLField
    @GraphQLDescription("Get XO Promotion Details")
    @GraphQLDataFetcher(GetXoDetailsDataFetcher.class)
    public XoPromotion getXOItem(
            @GraphQLName(PROMOTION_ID) @GraphQLDescription("promotion ID") String promotionId) {
        LOG.info("Get XO Item Method Executed");
        return null;
    }

    @GraphQLField
    @GraphQLDescription("Get Active Promotion Details")
    @GraphQLDataFetcher(GetActivePromotionDataFetcher.class)
    public PromotionStatus getActivePromotion(
            @GraphQLName(PROMOTION_ID) @GraphQLDescription("promotion ID") String promotionId) {
        LOG.info("Active Promotion Method Executed");
        return null;
    }
}
