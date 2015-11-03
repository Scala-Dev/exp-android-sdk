package com.scala.expandroidsdk.model;

import com.google.gson.internal.LinkedTreeMap;
import com.scala.expandroidsdk.AppSingleton;

import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ContentNode extends AbsModel{

    private CONTENT_TYPES subtype = null;
    private List<ContentNode> children = null;

    public enum CONTENT_TYPES {
        APP ("scala:content:app"),
        FILE ("scala:content:file"),
        FOLDER ("scala:content:folder"),
        URL ("scala:content:url"),
        UNKNOW ("")
        ;
        private final String subtype;
        CONTENT_TYPES(String subtype) {
            this.subtype = subtype;
        }
        private String contentType() { return subtype; }
    }



    public ContentNode(CONTENT_TYPES subtype){
        this.subtype = subtype;
    }

    public List<ContentNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<ContentNode> children){
        this.children = children;
    }


    public String getUrl(){
        String url = "";
        switch (this.subtype){
            case APP:
                String pathApp = (String) this.get("path");
                url = AppSingleton.getInstance().getHost() + "/api/delivery" + pathApp + "/index.html";
                break;
            case FILE:
                String pathFile = (String) this.get("path");
                url = AppSingleton.getInstance().getHost() + "/api/delivery" + pathFile;
                break;
            case FOLDER:
                break;
            case URL:
                url = (String) this.get("path");
                break;
            case UNKNOW:
                break;
        }
        return url;
    }


    public String getVariantUrl(String name){
        if(CONTENT_TYPES.FILE == this.subtype && hasVariant(name)){
            return getUrl() + "?variant=" + name;
        }
        return "";
    }

    public boolean hasVariant(String name) {
        boolean hasVariant = false;
        if(get("variants")!= null){
            List<LinkedTreeMap> variants = (List<LinkedTreeMap>) get("variants");
            for(LinkedTreeMap variantsList: variants){
              String variantName = (String) variantsList.get("name");
                if(variantName.equalsIgnoreCase(name)){
                    hasVariant = true;
                    break;
                }
            }
        }
        return hasVariant;
    }

    public static CONTENT_TYPES fromString(String text) {
        if (text != null) {
            for (CONTENT_TYPES type : CONTENT_TYPES.values()) {
                if (text.equalsIgnoreCase(type.subtype)) {
                    return type;
                }
            }
        }
        return null;
    }
}
