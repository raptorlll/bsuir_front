package com.timbuchalka.top10downloader.convertors;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Vera on 14.01.2018.
 */

public interface ContertorInterface<T, Type> {
    public T convert(Type data) throws JSONException;
}
