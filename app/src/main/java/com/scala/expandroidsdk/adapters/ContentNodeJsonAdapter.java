package com.scala.expandroidsdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.expandroidsdk.AppSingleton;
import com.scala.expandroidsdk.model.ContentNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ContentNodeJsonAdapter implements JsonDeserializer<ContentNode> {
    @Override
    public ContentNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {

        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        String subtype = (String) treeMap.get("subtype");
        ContentNode contentNode = new ContentNode(ContentNode.fromString(subtype));
        contentNode.setProperties(treeMap);
        List<LinkedTreeMap> children = (List<LinkedTreeMap>) treeMap.get("children");
        if(!children.isEmpty()){
            List<ContentNode> childrenList = new ArrayList<ContentNode>();
            for (LinkedTreeMap child : children) {
                String subtypeChild = (String) child.get("subtype");
                ContentNode childContentNode = new ContentNode(ContentNode.fromString(subtypeChild));
                LinkedTreeMap treeMapChild = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
                childContentNode.setProperties(treeMapChild);
                childrenList.add(childContentNode);
            }
            contentNode.setChildren(childrenList);
        }
            return contentNode;
    }
}