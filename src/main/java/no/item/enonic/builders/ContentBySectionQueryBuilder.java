package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.GetContentBySectionParams;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentBySectionQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(ContentBySectionQueryBuilder.class);
    private final Client client;
    private final GetContentBySectionParams params;

    private ContentBySectionQueryBuilder(Client client, int[] menuItemKeys){
        params = new GetContentBySectionParams();
        params.menuItemKeys = menuItemKeys;
        this.client = client;

    }

    public static ContentBySectionQueryBuilder of(Client client, int[] menuItemKeys){
        return new ContentBySectionQueryBuilder(client, menuItemKeys);
    }

    public ContentBySectionQueryBuilder query(String query){
        params.query = query;
        return this;
    }

    public ContentBySectionQueryBuilder orderBy(String orderBy){
        params.orderBy = orderBy;
        return this;
    }

    public ContentBySectionQueryBuilder levels(int levels){
        params.levels = levels;
        return this;
    }

    public ContentBySectionQueryBuilder childrenLevel(int levels){
        params.childrenLevel = levels;
        return this;
    }

    public ContentBySectionQueryBuilder parentLevel(int levels){
        params.parentLevel = levels;
        return this;
    }

    public ContentBySectionQueryBuilder from(int index){
        params.index = index;
        return this;
    }

    public ContentBySectionQueryBuilder size(int count){
        params.count = count;
        return this;
    }

    public ContentBySectionQueryBuilder includeData(){
        params.includeData = true;
        return this;
    }

    public ContentBySectionQueryBuilder includeUserRights(){
        params.includeUserRights = true;
        return this;
    }

    public ContentBySectionQueryBuilder includeVersionsInfo(){
        params.includeVersionsInfo = true;
        return this;
    }

    public ContentBySectionQueryBuilder includeOfflineContent(){
        params.includeOfflineContent = true;
        return this;
    }

    public Element get(){
        try {
            Element root = client.getContentBySection(params).getRootElement();
            //return Optional.of(UserParser.parse(root));
            return root;
        } catch(ClientException e){
            logger.error("Can't get content by section [{}]", params.menuItemKeys, e);
            throw e;
        }
    }
}
