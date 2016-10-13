package com.scala.exp.android.sdk.model;

import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class Content extends AbstractModel {

    private static final String LOG_TAG = Content.class.getSimpleName();

    private static final String API_DELIVERY = "/api/delivery";
    private static final String INDEX_HTML = "/index.html";
    private static final String NAME = "name";
    private static final String PATH = "path";
    private static final String RT = "_rt=";
    private static final String URL = "url";
    private static final String VARIANTS = "variants";
    private static final String VARIANT = "variant=";
    public static final String PARENT = "parent";
    private Utils.CONTENT_TYPES subtype = null;
    private List<Content> children = null;

    public Content(Utils.CONTENT_TYPES subtype){
        this.subtype = subtype;
    }

    public ExpObservable<SearchResults<Content>> getChildren() {
        Map options = new HashMap();
        final String uuid = getString(Utils.UUID);
        options.put(PARENT, uuid);
        return Exp.findContent(options);
    }

        public ExpObservable<SearchResults<Content>> getChildren(Map options) {
        final String uuid = getString(Utils.UUID);
        options.put(PARENT, uuid);
        return Exp.findContent(options);
    }

    public String getUrl(){
        String host = AppSingleton.getInstance().getHost();
        String rt = AppSingleton.getInstance().getAuth().getRestrictedToken();
        StringBuilder builder = new StringBuilder(host).append(API_DELIVERY);
        String path;

        try {
            switch (this.subtype) {
                case APP:
                    path = escapePath(getString(PATH));
                    return builder.append(path).append(INDEX_HTML).append("?").append(RT).append(rt).toString();
                case FILE:
                    path = escapePath(getString(PATH));
                    return builder.append(path).append("?").append(RT).append(rt).toString();
                case FOLDER:
                    break;
                case URL:
                    return getString(URL);
                case UNKNOW:
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Error encoding url", e);
        }

        throw new IllegalStateException("Cannot get url for this subtype.");
    }

    private String escapePath(String path) throws UnsupportedEncodingException {
        return URLEncoder.encode(path, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%2F", "/");

    }


    /**
     * Get Variant URL
     * @param name
     * @return
     */
    public String getVariantUrl(String name){
        if(Utils.CONTENT_TYPES.FILE != this.subtype) {
            throw new IllegalStateException("Cannot get variant url for this subtype.");
        }

        if (hasVariant(name)){
            String rt = AppSingleton.getInstance().getAuth().getRestrictedToken();
            return new StringBuilder(getUrl())
                    .append("?").append(VARIANT).append(name)
                    .append("&").append(RT).append(rt).toString();
        }

        throw new IllegalArgumentException("Variant not found.");
    }

    /**
     * Check for variants
     * @param name
     * @return
     */
    public boolean hasVariant(String name) {
        boolean hasVariant = false;
        if(get(VARIANTS)!= null){
            List<LinkedTreeMap> variants = (List<LinkedTreeMap>) get(VARIANTS);
            for(LinkedTreeMap variantsList: variants){
                String variantName = (String) variantsList.get(NAME);
                if(variantName.equalsIgnoreCase(name)){
                    hasVariant = true;
                    break;
                }
            }
        }
        return hasVariant;
    }

    public void setChildren(List<Content> children){
        this.children = children;
    }


}
