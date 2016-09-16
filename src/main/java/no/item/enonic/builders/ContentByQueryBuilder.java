package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.GetContentByQueryParams;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import org.jdom.JDOMException;
import org.jdom.output.DOMOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import javax.inject.Inject;
import javax.inject.Provider;

public class ContentByQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(ContentByCategoryQueryBuilder.class);
    private final Provider<Client> client;
    private final GetContentByQueryParams params;
    private final DOMOutputter domOutputter;

    @Inject
    public ContentByQueryBuilder(Provider<Client> client){
        this.params = new GetContentByQueryParams();
        this.client = client;
        this.domOutputter = new DOMOutputter();
    }

    /*
    private ContentByQueryBuilder(Client client, String query){
        params = new GetContentByQueryParams();
        params.query = query;
        this.client = client;
    }*/

    public ContentByQueryBuilder query(String query){
        params.query = query;
        return this;
    }

    public ContentByQueryBuilder orderBy(String orderBy){
        params.orderBy = orderBy;
        return this;
    }

    public ContentByQueryBuilder childrenLevel(int levels){
        params.childrenLevel = levels;
        return this;
    }

    public ContentByQueryBuilder parentLevel(int levels){
        params.parentLevel = levels;
        return this;
    }

    public ContentByQueryBuilder from(int index){
        params.index = index;
        return this;
    }

    public ContentByQueryBuilder size(int count){
        params.count = count;
        return this;
    }

    public ContentByQueryBuilder includeData(){
        params.includeData = true;
        return this;
    }

    public ContentByQueryBuilder includeUserRights(){
        params.includeUserRights = true;
        return this;
    }

    public ContentByQueryBuilder includeVersionsInfo(){
        params.includeVersionsInfo = true;
        return this;
    }

    public ContentByQueryBuilder includeOfflineContent(){
        params.includeOfflineContent = true;
        return this;
    }

    public Document get(){
        try {
            Preconditions.checkNotNull(params.query, "Content By Query expects [params.query]");
            return domOutputter.output(client.get().getContentByQuery(params));
        } catch(ClientException e){
            logger.error("Can't get content by query [{}]", params.query, e);
            throw e;
        } catch (JDOMException e) {
            throw Throwables.propagate(e);
        }
    }
}
