package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.CreateGroupParams;
import no.item.enonic.models.Group;
import no.item.enonic.parsers.GroupParser;
import no.item.enonic.parsers.Parsers;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateGroupQueryBuilder {

    private final Logger logger = LoggerFactory.getLogger(ContentBySectionQueryBuilder.class);
    private final Client client;
    private final CreateGroupParams params;
    private final GroupParser groupParser;

    private CreateGroupQueryBuilder(Client client, String userStore, String name){
        params = new CreateGroupParams();
        this.groupParser = new GroupParser();
        params.userStore = userStore;
        params.name = name;
        params.description = name;
        this.client = client;
    }

    public static CreateGroupQueryBuilder of(Client client, String userStore, String name){
        return new CreateGroupQueryBuilder(client, userStore, name);
    }

    public CreateGroupQueryBuilder description(String description){
        params.description = description;
        return this;
    }

    public CreateGroupQueryBuilder restricted(boolean restricted){
        params.restricted = restricted;
        return this;
    }

    public Group execute(){
        try {
            Document doc = client.createGroup(params);
            Group group = groupParser.apply(doc.getRootElement());
            logger.info("Created group [{}]", group.name);
            return group;
        } catch(ClientException e){
            logger.error("Couldn't create group [{}]", params.name);
            throw e;
        }
    }
}
