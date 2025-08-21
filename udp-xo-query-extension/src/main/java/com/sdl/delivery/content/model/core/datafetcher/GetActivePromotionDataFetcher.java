package com.sdl.delivery.content.model.core.datafetcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdl.delivery.content.model.core.schema.PromotionStatus;
import com.sdl.delivery.content.model.core.schema.TriggerValues;
import com.sdl.delivery.content.model.core.schema.XoPromotion;
import com.sdl.delivery.content.model.core.schema.XoTrigger;
import com.sdl.delivery.content.model.core.utils.ServiceClientUtil;
import graphql.schema.DataFetchingEnvironment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetActivePromotionDataFetcher extends AbstractDataFetcher<PromotionStatus, PromotionStatus> {

    private static final Logger LOG =
            LoggerFactory.getLogger(GetActivePromotionDataFetcher.class);

    public String getName() {
        return "";
    }

    @Override
    public Class<PromotionStatus> getModelClass() {
        return PromotionStatus.class;
    }

    @Override
    public PromotionStatus get(DataFetchingEnvironment environment) throws Exception {

        String osEndpoint =  ServiceClientUtil.getOsEndpoint();
        String promotionId = environment.getArgument("promotionId");
        OkHttpClient client = new OkHttpClient();
        Request homeRequest = new Request.Builder()
                .url(osEndpoint+"/home")
                .get()
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/137.0.0.0 Safari/537.36")
                .header("Accept", "text/html")
                .build();

        Response homeResponse = client.newCall(homeRequest).execute();
        List<String> cookies = homeResponse.headers("Set-Cookie");

        if (cookies.isEmpty()) {
           LOG.error("No cookies received from homepage – ADF session not initialized.");
        } else {
            LOG.error("cookies{} : ", cookies);
        }

        String cookieHeader = cookies.stream()
                .map(c -> c.split(";", 2)[0])
                .collect(Collectors.joining("; "));

        Request request = new Request.Builder()
                .url(osEndpoint+"/api/claims")
                .get()
                .header("Cookie", cookieHeader)
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/137.0.0.0 Safari/537.36")
                .build();

        String location;
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request failed: " + response.code());
            }

            String responseBody = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);

           LOG.error("Response: {}", responseBody);

            location = jsonNode.path("taf:claim:visitor:location").asText().replace("\"", "");
            LOG.error("Location: {}", location);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        XoPromotion promo = getXoPromotionById(promotionId);
        PromotionStatus promotionStatus = new PromotionStatus();

        if (promo != null) {
            promotionStatus.setId(promo.getId());
            promotionStatus.setName(promo.getName());
            promotionStatus.setState(promo.getState());

            if ("PENDING_ACTIVATION".equals(promo.getState())) {
                boolean matchFound = false;

                if (promo.getTriggers() != null) {
                    for (XoTrigger trigger : promo.getTriggers()) {
                        List<String> regionList = trigger.getTriggerValues().getStringValues();
                        if (regionList != null && regionList.contains(location)) {
                            matchFound = true;
                            break;
                        }
                    }
                }
                if (matchFound) {
                    LOG.info("Promotion matches visitor's region '{}'", location);
                    promotionStatus.setStatus("Active");
                } else {
                    LOG.info("No triggers match the visitor's region '{}'", location);
                    promotionStatus.setStatus("Inactive");
                }
            } else {
                LOG.info("Promotion is not PENDING_ACTIVATION state, state={}", promo.getState());
                promotionStatus.setStatus("Inactive");
            }

        } else {
            LOG.warn("No promotion found for id={}", promotionId);
            promotionStatus.setStatus("Inactive");
        }

        return promotionStatus;
    }

    private XoPromotion getXoPromotionById(String promotionId) throws IOException {

        String osEndpoint =  ServiceClientUtil.getOsEndpoint() + "/xo-promotions/_doc/" + promotionId + "?refresh=true";
        final OkHttpClient client = ServiceClientUtil.getClient();
        final ObjectMapper mapper = ServiceClientUtil.getMapper();

        Request request = new Request.Builder()
                .url(osEndpoint)
                .get()
                .addHeader("Authorization", ServiceClientUtil.getAuthHeader())
                .build();

        XoPromotion promotion;
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to fetch promotions: HTTP " + response.code());
            }
            String responseBody = response.body().string();
            JsonNode root = mapper.readTree(responseBody);

            JsonNode source = root.get("_source");

            promotion = new XoPromotion();
            promotion.setId(source.path("id").asText(""));
            promotion.setName(source.path("name").asText(""));
            promotion.setState(source.path("state").asText(""));

            List<XoTrigger> triggers = new ArrayList<>();
            for (JsonNode triggerNode : source.get("triggers")) {
                XoTrigger trigger = new XoTrigger();
                trigger.setName(triggerNode.get("name").asText());

                TriggerValues triggerValues = new TriggerValues();

                JsonNode valuesNode = triggerNode.get("triggerValues");
                if (valuesNode.has("stringValues")) {
                    List<String> stringValues = new ArrayList<>();
                    for (JsonNode val : valuesNode.get("stringValues")) {
                        stringValues.add(val.asText());
                    }
                    triggerValues.setStringValues(stringValues);
                }
                if (valuesNode.has("longValues")) {
                    List<Long> longValues = new ArrayList<>();
                    for (JsonNode val : valuesNode.get("longValues")) {
                        longValues.add(val.asLong());
                    }
                    triggerValues.setLongValues(longValues);
                }

                trigger.setTriggerValues(triggerValues);
                triggers.add(trigger);
            }
            promotion.setTriggers(triggers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return promotion;
    }

    @Override
    protected boolean isConnection() {
        return false;
    }
}
