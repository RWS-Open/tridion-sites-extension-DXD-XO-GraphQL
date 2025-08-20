package com.sdl.delivery.content.model.core.datafetcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdl.delivery.content.model.core.schema.ActionItem;
import com.sdl.delivery.content.model.core.schema.ActionSort;
import com.sdl.delivery.content.model.core.schema.TriggerValues;
import com.sdl.delivery.content.model.core.schema.XoPromotion;
import com.sdl.delivery.content.model.core.schema.XoTrigger;
import com.sdl.delivery.content.model.core.schema.Action;
import com.sdl.delivery.content.model.core.utils.ServiceClientUtil;
import graphql.schema.DataFetchingEnvironment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class GetXoDetailsDataFetcher extends AbstractDataFetcher<XoPromotion, XoPromotion> {


    public String getName() {
        return "";
    }

    @Override
    public Class<XoPromotion> getModelClass() {
        return XoPromotion.class;
    }

    @Override
    public XoPromotion get(DataFetchingEnvironment environment) throws Exception {

        String promotionId = environment.getArgument("promotionId");

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
            promotion.setScopePublication(source.path("scopePublication").asText(""));
            promotion.setInstanceId(source.path("instanceId").asText(""));
            promotion.setCreatedDate(source.path("createdDate").asText(""));
            promotion.setModifiedDate(source.path("modifiedDate").asText(""));
            promotion.setState(source.path("state").asText(""));

            List<XoTrigger> triggers = new ArrayList<>();
            for (JsonNode triggerNode : source.get("triggers")) {
                XoTrigger trigger = new XoTrigger();
                trigger.setName(triggerNode.get("name").asText());
                trigger.setOperator(triggerNode.get("operator").asText());
                trigger.setScopeTrigger(triggerNode.get("isScopeTrigger").asBoolean());

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

            JsonNode actionNode = source.get("action");
            if (actionNode != null) {
                Action action = new Action();

                JsonNode actionSortNode = actionNode.get("actionSort");
                if (actionSortNode != null) {
                    ActionSort actionSort = new ActionSort();
                    actionSort.setName(actionSortNode.get("name").asText());
                    actionSort.setSortDirection(actionSortNode.get("sortDirection").asText());
                    actionSort.setDynamic(actionSortNode.get("isDynamic").asBoolean());
                    action.setActionSort(actionSort);
                }

                JsonNode actionItemsNode = actionNode.get("actionItems");
                if (actionItemsNode != null && actionItemsNode.isArray()) {
                    List<ActionItem> actionItems = new ArrayList<>();
                    for (JsonNode itemNode : actionItemsNode) {
                        ActionItem actionItem = new ActionItem();
                        actionItem.setComponentId(itemNode.get("componentId").asText());
                        actionItem.setComponentTemplateId(itemNode.get("componentTemplateId").asText());
                        actionItems.add(actionItem);
                    }
                    action.setActionItems(actionItems);
                }
                action.setOriginalQuery(
                        actionNode.has("originalQuery") ? actionNode.get("originalQuery").asText() : ""
                );

                promotion.setAction(action);
            }

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
