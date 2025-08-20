package com.sdl.delivery.content.model.core.datafetcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdl.delivery.content.model.core.schema.HitItem;
import com.sdl.delivery.content.model.core.schema.Hits;
import com.sdl.delivery.content.model.core.schema.Source;
import com.sdl.delivery.content.model.core.schema.Total;
import com.sdl.delivery.content.model.core.schema.XoPromotions;
import com.sdl.delivery.content.model.core.utils.ServiceClientUtil;
import graphql.schema.DataFetchingEnvironment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class XoPromotionDataFetcher extends AbstractDataFetcher<XoPromotions, XoPromotions> {

    public String getName() {
        return "";
    }

    @Override
    public Class<XoPromotions> getModelClass() {
        return XoPromotions.class;
    }

    @Override
    public XoPromotions get(DataFetchingEnvironment environment) throws Exception {

        String osEndpoint =  ServiceClientUtil.getOsEndpoint() + "/xo-promotions/_search";
        final OkHttpClient client = ServiceClientUtil.getClient();
        final ObjectMapper mapper = ServiceClientUtil.getMapper();
        Request request = new Request.Builder()
                .url(osEndpoint)
                .get()
                .addHeader("Authorization", ServiceClientUtil.getAuthHeader())
                .build();

        int hitCount = 0;
        List<HitItem> hitList = null;
        Total total = new Total();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to fetch promotions: HTTP " + response.code());
            }
            String responseBody = response.body().string();
            JsonNode root = mapper.readTree(responseBody);
            hitCount = root.path("hits").path("total").path("value").asInt(0);
            total.setValue(hitCount);

            hitList = new ArrayList<>();

            for (JsonNode hitNode : root.path("hits").path("hits")) {
                HitItem item = new HitItem();
                item.setIndex(hitNode.path("_index").asText());
                Source source = new Source();
                source.setId(hitNode.path("_source").path("id").asText());
                source.setName(hitNode.path("_source").path("name").asText());

                item.setSource(source);
                hitList.add(item);
            }
        }

        Hits hits = new Hits();
        hits.setTotal(total);
        hits.setHits(hitList);
        return new XoPromotions(hits);
    }

    @Override
    protected boolean isConnection() {
        return false;
    }


}
