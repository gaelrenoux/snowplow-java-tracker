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

public class Parameter {
    // General
    public static final String SCHEMA = "$schema";
    public static final String DATA = "data";
    public static final String EVENT = "e";
    public static final String TID = "tid";
    public static final String EID = "eid";
    public static final String TIMESTAMP = "dtm";
    public static final String TRACKER_VERSION = "tv";

    public static final String UID = "uid";
    public static final String CONTEXT = "co";
    public static final String CONTEXT_ENCODED = "cx";
    public static final String UNSTRUCTURED = "ue_pr";
    public static final String UNSTRUCTURED_ENCODED = "ue_px";

    // Transaction Item
    public static final String ITEM_ID = "ti_id";
    public static final String ITEM_SKU = "ti_sk";
    public static final String ITEM_NAME = "ti_nm";
    public static final String ITEM_CATEGORY = "ti_ca";
    public static final String ITEM_PRICE = "ti_pr";
    public static final String ITEM_QUANTITY = "ti_qu";
    public static final String ITEM_CURRENCY = "ti_cu";

    // Subject class
    public static final String PLATFORM = "p";
    public static final String RESOLUTION = "res";
    public static final String VIEWPORT = "vp";
    public static final String COLOR_DEPTH = "cd";
    public static final String TIMEZONE = "tz";
    public static final String LANGUAGE = "lang";

    // Page View
    public static final String PAGE_URL = "url";
    public static final String PAGE_TITLE = "page";
    public static final String PAGE_REFR = "refr";

    // Structured Event
    public static final String SE_CATEGORY = "se_ca";
    public static final String SE_ACTION = "se_ac";
    public static final String SE_LABEL = "se_la";
    public static final String SE_PROPERTY = "se_pr";
    public static final String SE_VALUE = "se_va";
}
