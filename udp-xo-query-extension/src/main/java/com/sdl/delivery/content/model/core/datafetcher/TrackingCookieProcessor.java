package com.sdl.delivery.content.model.core.datafetcher;

import com.tridion.ambientdata.AmbientDataException;
import com.tridion.ambientdata.claimstore.ClaimStore;
import com.tridion.ambientdata.processing.AbstractClaimProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class TrackingCookieProcessor extends AbstractClaimProcessor {

    private static final Logger LOG =
            LoggerFactory.getLogger(TrackingCookieProcessor.class);

    @Override
    public void onSessionStart(ClaimStore cs) throws AmbientDataException {
        LOG.error("ADF onSessionStart() called.");
    }

    @Override
    public void onRequestStart(ClaimStore claimStore) throws AmbientDataException {
        URI location = (URI) claimStore.get(URI.create("taf:claim:visitor:location"));
        LOG.error("Visitor location from ADF: {}", location);
    }
}
