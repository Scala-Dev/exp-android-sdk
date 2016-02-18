package com.scala.exp.android.sdk.model;

import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.BlockingObservable;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ContentNode extends AbstractModel {

    private static final String LOG_TAG = ContentNode.class.getSimpleName();

    private static final String API_DELIVERY = "/api/delivery";
    private static final String INDEX_HTML = "/index.html";
    private static final String NAME = "name";
    private static final String PATH = "path";
    private static final String RT = "_rt=";
    private static final String URL = "url";
    private static final String VARIANTS = "variants";
    private static final String VARIANT = "variant=";


    private Utils.CONTENT_TYPES subtype = null;
    private List<ContentNode> children = null;

    public ContentNode(Utils.CONTENT_TYPES subtype){
        this.subtype = subtype;
    }

    public ExpObservable<List<ContentNode>> getChildren() {
        if (this.children == null) {
            final String uuid = getString(Utils.UUID);
            final ExpObservable<ContentNode> observable = Exp.getContentNode(uuid);
            return new ExpObservable<List<ContentNode>>(observable.<List<ContentNode>>flatMap(new Func1<ContentNode, Observable<List<ContentNode>>>() {
                @Override
                public Observable<List<ContentNode>> call(ContentNode content) {
                    ContentNode.this.children = content.children;
                    return Observable.just(content.children);
                }
            }));
        }

        return new ExpObservable<List<ContentNode>>(Observable.just(this.children));
    }

        public void setChildren(List<ContentNode> children){
        this.children = children;
    }


    public String getUrl(){
        String url = null;

        String host = AppSingleton.getInstance().getHost();
        String rt = AppSingleton.getInstance().getUser().getRestrictedToken();
        StringBuilder builder = new StringBuilder(host).append(API_DELIVERY);
        String path;

        try {
            switch (this.subtype) {
                case APP:
                    path = escapePath(getString(PATH));
                    url = builder.append(path).append(INDEX_HTML).append("?").append(RT).append(rt).toString();
                    break;
                case FILE:
                    path = escapePath(getString(PATH));
                    url = builder.append(path).append("?").append(RT).append(rt).toString();
                    break;
                case FOLDER:
                    break;
                case URL:
                    url = getString(URL);
                    break;
                case UNKNOW:
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Error encoding url", e);
        }

        return url;
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
        if(Utils.CONTENT_TYPES.FILE == this.subtype && hasVariant(name)){
            String rt = AppSingleton.getInstance().getUser().getRestrictedToken();
            return new StringBuilder(getUrl())
                    .append("?").append(VARIANT).append(name)
                    .append("&").append(RT).append(rt).toString();
        }
        return null;
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


}
