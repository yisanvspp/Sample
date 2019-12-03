package com.yisan.http.request.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.yisan.http.request.bean.BaseBean;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/5/10
 */
public class BaseBeanDeserializer implements JsonDeserializer<BaseBean> {
    @Override
    public BaseBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        JsonObject jsonObj = json.getAsJsonObject();
        try {
            Class clazz = (Class) typeOfT;
            BaseBean baseBean = (BaseBean) clazz.newInstance();
            for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
                String itemKey = entry.getKey();
                JsonElement itemElement = entry.getValue();
                try {
                    Field field = clazz.getDeclaredField(itemKey);
                    field.setAccessible(true);
                    Object item = context.deserialize(itemElement, field.getType());
                    field.set(baseBean, item);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return baseBean;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
