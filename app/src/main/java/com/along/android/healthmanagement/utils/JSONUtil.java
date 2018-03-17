package com.along.android.healthmanagement.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 666 on 18/3/16.
 */

public class JSONUtil {

    private static Gson gson;

    public static Gson getGson() {
        return new Gson();
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type type) {
        return getGson().fromJson(json, type);
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        try {
            JSONArray array = new JSONArray(json);
            List<T> list = new ArrayList<>();
            for (int i = 0, len = array.length(); i < len; i++) {
                list.add(fromJson(array.getJSONObject(i).toString(), clazz));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> fromJsonToMap(String json) {
        Type mapType = new TypeToken<Map<String, String>>() {} .getType();
        return fromJson(json, mapType);
    }

    public static String toJson(Object src) {
        return getGson().toJson(src);
    }

    public static String toJson(Object src, Type type) {
        return getGson().toJson(src, type);
    }

    public static JSONObject toJSONObject(Object src) {
        JSONObject object = null;
        try {
            if (src == null) {
                return null;
            }
            object = new JSONObject(toJson(src));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONObject toJSONObject(Object src, Type type) {
        JSONObject object = null;
        try {
            object = new JSONObject(toJson(src, type));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONArray toJSONArray(List list) {
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (Object item : list) {
                array.put(toJSONObject(item));
            }
        }
        return array;
    }

    public static JsonElement toJsonTree(Object src) {
        return getGson().toJsonTree(src);
    }

    public static JsonElement toJsonTree(Object src, Type type) {
        return getGson().toJsonTree(src, type);
    }

    public static Type canonicalize(Type type) {
        return $Gson$Types.canonicalize(type);
    }

    public static <T> T getValue(JSONObject json, String key) {
        if (key == null) {
            return null;
        }
        try {
            String[] attrs = key.split("\\.");

            for (int i = 0, len = attrs.length; i < len; i ++) {
                String attr = attrs[i];
                if (json.has(attr)) {
                    Object value = json.get(attr);
                    if (i == len - 1) {
                        //noinspection unchecked
                        return (T)value;
                    } else {
                        json = (JSONObject)value;
                    }
                } else {
                    Matcher matcher = Pattern.compile("(.+?)\\[(\\d+)\\]").matcher(attr);
                    if (matcher.find()) {
                        attr = matcher.group(1);
                        String indexStr = matcher.group(2);
                        int index = Integer.parseInt(indexStr);
                        JSONArray jsonArray = json.getJSONArray(attr);
                        Object value = jsonArray.get(index);

                        if (i == len - 1) {
                            //noinspection unchecked
                            return (T)value;
                        } else {
                            json = (JSONObject) value;
                        }
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> T getValue(JSONObject json, String key, T defaultValue) {
        T result = getValue(json, key);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    public static Map<String, Object> convertJSONObjectToMap(JSONObject jsonObject) {
        try {
            Map<String, Object> map = new HashMap<>();
            Iterator<String> it = jsonObject.keys();
            while(it.hasNext()) {
                String key = it.next();
                Object value = jsonObject.get(key);
                if (value instanceof JSONArray) {
                    map.put(key, convertJSONArrayToList((JSONArray) value));
                } else if (value instanceof JSONObject) {
                    map.put(key, convertJSONObjectToMap((JSONObject) value));
                } else {
                    map.put(key, value);
                }
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> convertJSONArrayToList(JSONArray array) {
        List<Object> list = new ArrayList<>();
        try {
            for (int i = 0, len = array.length(); i < len; i ++) {
                Object value = array.get(i);
                if (value instanceof JSONArray) {
                    list.add(convertJSONArrayToList((JSONArray) value));
                } else if (value instanceof JSONObject) {
                    list.add(convertJSONObjectToMap((JSONObject) value));
                } else {
                    list.add(value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String putParamToJSONString(String json, String key, Object value) {
        JSONObject object;
        try {
            if (TextUtils.isEmpty(json)) {
                object = new JSONObject();
            } else {
                object = new JSONObject(json);
            }
            object.put(key, value);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
