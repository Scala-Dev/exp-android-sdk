package exp.android.sdk.model;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public abstract class AbsModel {

    protected LinkedTreeMap properties = null;

    public Object get(String path) {
        Object o = null;
        String[] keys = path.split("\\.");
        LinkedTreeMap subdoc = this.properties;
        for (int i = 0; i < keys.length; i++) {
            if(i == keys.length -1){
                o = subdoc.get(keys[i]);
            }
            if(subdoc.get(keys[i]) instanceof LinkedTreeMap){
                subdoc = (LinkedTreeMap) subdoc.get(keys[i]);
            }
        }
        return o;
    }

    public void setProperties(LinkedTreeMap properties) {
        this.properties = properties;
    }
}
