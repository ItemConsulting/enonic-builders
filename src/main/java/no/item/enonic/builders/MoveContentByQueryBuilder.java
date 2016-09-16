package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.MoveContentParams;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MoveContentByQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(MoveContentByQueryBuilder.class);
    private final Client client;
    private final MoveContentParams params;

    @Inject
    private MoveContentByQueryBuilder(Client client){
        this.client = client;
        params = new MoveContentParams();
    }

    public MoveContentByQueryBuilder contentKey(int contentKey){
        params.contentKey = contentKey;
        return this;
    }

    public MoveContentByQueryBuilder categoryKey(int categoryKey){
        params.categoryKey = categoryKey;
        return this;
    }

    public void move(){
        try {
            Preconditions.checkNotNull(params.contentKey);
            Preconditions.checkNotNull(params.categoryKey);

            client.moveContent(params);
        } catch(ClientException e){
            logger.error("Can't get move content [{}]", params.contentKey, e);
            throw e;
        }
    }
}
