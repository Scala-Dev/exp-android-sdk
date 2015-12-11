package com.scala.exp.android.sdk.model;

import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.BlockingObservable;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ContentNode extends AbstractModel {

    private static final String UUID = "uuid";
    private static final String PATH = "path";
    private static final String API_DELIVERY = "/api/delivery";
    private static final String VARIANTS = "variants";
    private static final String NAME = "name";
    private static final String INDEX_HTML = "/index.html";
    private static final String VARIANT = "?variant=";

    private Utils.CONTENT_TYPES subtype = null;
    private List<ContentNode> children = null;

    public ContentNode(Utils.CONTENT_TYPES subtype){
        this.subtype = subtype;
    }

    public ExpObservable<List<ContentNode>> getChildren() {
        if (this.children == null) {
            final String uuid = String.valueOf(this.get(UUID));
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
        String url = "";
        switch (this.subtype){
            case APP:
                String pathApp = (String) this.get(PATH);
                url = AppSingleton.getInstance().getHost() + API_DELIVERY + pathApp + INDEX_HTML;
                break;
            case FILE:
                String pathFile = (String) this.get(PATH);
                url = AppSingleton.getInstance().getHost() + API_DELIVERY + pathFile;
                break;
            case FOLDER:
                break;
            case URL:
                url = (String) this.get(PATH);
                break;
            case UNKNOW:
                break;
        }
        return url;
    }


    /**
     * Get Variant URL
     * @param name
     * @return
     */
    public String getVariantUrl(String name){
        if(Utils.CONTENT_TYPES.FILE == this.subtype && hasVariant(name)){
            return getUrl() + VARIANT + name;
        }
        return "";
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
