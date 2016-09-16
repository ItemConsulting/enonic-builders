package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.GetContentByCategoryParams;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import org.jdom.JDOMException;
import org.jdom.output.DOMOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class ContentByCategoryQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(ContentByCategoryQueryBuilder.class);
    private final Client client;
    private final GetContentByCategoryParams params;
    private final DOMOutputter domOutputter;

    private ContentByCategoryQueryBuilder(Client client, int[] categoryKeys){
        this.client = client;
        this.params = new GetContentByCategoryParams();
        params.categoryKeys = categoryKeys;
        this.domOutputter = new DOMOutputter();
    }

    public static ContentByCategoryQueryBuilder of(Client client, int[] categoryKeys){
        return new ContentByCategoryQueryBuilder(client, categoryKeys);
    }

    public ContentByCategoryQueryBuilder query(String query){
        params.query = query;
        return this;
    }

    public ContentByCategoryQueryBuilder orderBy(String orderBy){
        params.orderBy = orderBy;
        return this;
    }

    public ContentByCategoryQueryBuilder levels(int levels){
        params.levels = levels;
        return this;
    }

    public ContentByCategoryQueryBuilder childrenLevel(int levels){
        params.childrenLevel = levels;
        return this;
    }

    public ContentByCategoryQueryBuilder parentLevel(int levels){
        params.parentLevel = levels;
        return this;
    }

    public ContentByCategoryQueryBuilder from(int index){
        params.index = index;
        return this;
    }

    public ContentByCategoryQueryBuilder size(int count){
        params.count = count;
        return this;
    }

    public ContentByCategoryQueryBuilder includeData(){
        params.includeData = true;
        return this;
    }

    public ContentByCategoryQueryBuilder includeUserRights(){
        params.includeUserRights = true;
        return this;
    }

    public ContentByCategoryQueryBuilder includeVersionsInfo(){
        params.includeVersionsInfo = true;
        return this;
    }

    public ContentByCategoryQueryBuilder includeOfflineContent(){
        params.includeOfflineContent = true;
        return this;
    }

    public Document get(){
        try {
            Preconditions.checkNotNull(params.categoryKeys, "Content By Category expects category keys");
            return domOutputter.output(client.getContentByCategory(params));
        } catch(ClientException e){
            logger.error("Can't get content by section [{}]", params.categoryKeys, e);
            throw e;
        } catch (JDOMException e) {
            throw Throwables.propagate(e);
        }
    }
}
