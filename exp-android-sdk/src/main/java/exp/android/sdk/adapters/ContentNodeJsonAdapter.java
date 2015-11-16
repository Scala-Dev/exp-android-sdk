package exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import exp.android.sdk.AppSingleton;
import exp.android.sdk.Utils;
import exp.android.sdk.model.ContentNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ContentNodeJsonAdapter implements JsonDeserializer<ContentNode> {

    public static final String SUBTYPE = "subtype";
    public static final String CHILDREN = "children";

    @Override
    public ContentNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {

        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        String subtype = (String) treeMap.get(SUBTYPE);
        ContentNode contentNode = new ContentNode(Utils.getContentTypeEnum(subtype));
        contentNode.setProperties(treeMap);
        List<LinkedTreeMap> children = (List<LinkedTreeMap>) treeMap.get(CHILDREN);
        if(!children.isEmpty()){
            List<ContentNode> childrenList = new ArrayList<ContentNode>();
            for (LinkedTreeMap child : children) {
                String subtypeChild = (String) child.get(SUBTYPE);
                ContentNode childContentNode = new ContentNode(Utils.getContentTypeEnum(subtypeChild));
                LinkedTreeMap treeMapChild = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
                childContentNode.setProperties(treeMapChild);
                childrenList.add(childContentNode);
            }
            contentNode.setChildren(childrenList);
        }
        return contentNode;
    }
}