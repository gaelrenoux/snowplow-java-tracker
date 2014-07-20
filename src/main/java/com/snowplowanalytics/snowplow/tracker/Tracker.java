/*
 * Copyright (c) 2014 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */

package com.snowplowanalytics.snowplow.tracker;

import com.sun.xml.internal.rngom.digested.DDataPattern;

import java.awt.PageAttributes;
import java.util.Map;

public class Tracker {

    private boolean base64Encoded = true;
    private Emitter emitter;
    private String appId;
    private String namespace;
    private String contextSchema;
    private String unstructSchema;

    public Tracker(Emitter emitter, String appId, boolean base64Encoded, String namespace) {
        this.emitter = emitter;
        this.appId = appId;
        this.base64Encoded = base64Encoded;
        this.namespace = namespace;
        this.setSchema(Constants.DEFAULT_VENDOR, Constants.DEFAULT_SCHEMA_TAG,
                Constants.DEFAULT_SCHEMA_VERSION);
    }

    private Payload completePayload(Payload payload, Map context, double timestamp) {
        payload.add(Parameter.TID, Util.getTransactionId());
        payload.add(Parameter.TIMESTAMP, Util.getTimestamp());

        payload.add(Parameter.TRACKER_VERSION, Version.VERSION);

        // If timestamp is set to 0, generate one
        payload.add(Parameter.TIMESTAMP, (timestamp == 0 ? Util.getTimestamp() : timestamp));

        // Encodes context data
        payload.addMap(context, this.base64Encoded, Parameter.CONTEXT_ENCODED, Parameter.CONTEXT);

        return payload;
    }

    private void addTrackerPayload(Payload payload) {
        this.emitter.addToBuffer(payload);
    }

    public void setSchema(String vendor, String schemaTag, String version) {
        this.contextSchema = vendor + "/contexts/" + schemaTag + version;
        this.unstructSchema = vendor + "/unstruct_event/" + schemaTag + version;
    }

    public void trackPageView(String pageUrl, String pageTitle, String referrer) {
        trackPageView(pageUrl, pageTitle, referrer, null, 0);
    }

    public void trackPageView(String pageUrl, String pageTitle, String referrer, Map context) {
        trackPageView(pageUrl,pageTitle, referrer, context, 0);
    }

    public void trackPageView(String pageUrl, String pageTitle, String referrer, double timestamp) {
        trackPageView(pageUrl, pageTitle, referrer, null, timestamp);
    }

    public void trackPageView(String pageUrl, String pageTitle, String referrer,
                              Map context, double timestamp) {
        Payload payload = new TrackerPayload();
        payload.add(Parameter.EVENT, Constants.EVENT_PAGE_VIEW);
        payload.add(Parameter.PAGE_URL, pageUrl);
        payload.add(Parameter.PAGE_TITLE, pageTitle);
        payload.add(Parameter.PAGE_REFR, referrer);

        completePayload(payload, context, timestamp);

        addTrackerPayload(payload);
    }

    public void trackStructuredEvent(String category, String action, String label, String property,
                                     int value) {
        trackStructuredEvent(category, action, label, property, value, null, 0);
    }

    public void trackStructuredEvent(String category, String action, String label, String property,
                                     int value, Map context) {
        trackStructuredEvent(category, action, label, property, value, context, 0);
    }

    public void trackStructuredEvent(String category, String action, String label, String property,
                                     int value, long timestamp) {
        trackStructuredEvent(category, action, label, property, value, null, timestamp);
    }

    public void trackStructuredEvent(String category, String action, String label, String property,
                                     int value, Map context, long timestamp) {
        Payload payload = new TrackerPayload();
        payload.add(Parameter.EVENT, Constants.EVENT_STRUCTURED);
        payload.add(Parameter.SE_CATEGORY, category);
        payload.add(Parameter.SE_ACTION, action);
        payload.add(Parameter.SE_LABEL, label);
        payload.add(Parameter.SE_PROPERTY, property);
        payload.add(Parameter.SE_VALUE, value);

        completePayload(payload, context, timestamp);

        addTrackerPayload(payload);
    }

    public void trackUnstructuredEvent(Map<String, Object> eventData) {
        trackUnstructuredEvent(eventData, null, 0);
    }

    public void trackUnstructuredEvent(Map<String, Object> eventData, Map context) {
        trackUnstructuredEvent(eventData, context, 0);
    }

    public void trackUnstructuredEvent(Map<String, Object> eventData, long timestamp) {
        trackUnstructuredEvent(eventData, null, timestamp);
    }

    public void trackUnstructuredEvent(Map<String, Object> eventData, Map context, long timestamp) {
        Payload payload = new TrackerPayload();
        Payload envelope = new TrackerPayload();

        envelope.setSchema(unstructSchema);
        envelope.setData(eventData);

        payload.add(Parameter.EVENT, Constants.EVENT_UNSTRUCTURED);
        payload.addMap(envelope.getMap(), base64Encoded, Parameter.UNSTRUCTURED_ENCODED, Parameter.UNSTRUCTURED);

        completePayload(payload, context, timestamp);

        addTrackerPayload(payload);
    }
}
