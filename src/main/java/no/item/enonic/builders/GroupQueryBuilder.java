package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.GetGroupParams;
import no.item.enonic.models.Group;
import no.item.enonic.parsers.GroupParser;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GroupQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(GroupQueryBuilder.class);
    private final Client client;
    private final GetGroupParams params;
    private final GroupParser groupParser;

    private GroupQueryBuilder(Client client, String userstore, String group){
        this.client = client;
        params = new GetGroupParams();
        params.group = userstore + ":" + group;
        this.groupParser = new GroupParser();
    }

    public static GroupQueryBuilder of(Client client, String userstore, String groupName){
        return new GroupQueryBuilder(client, userstore, groupName);
    }

    public GroupQueryBuilder includeMembers(){
        params.includeMembers = true;
        return this;
    }

    public GroupQueryBuilder includeMemberships(){
        params.includeMemberships = true;
        return this;
    }

    public GroupQueryBuilder normalizeGroups(){
        params.normalizeGroups = true;
        return this;
    }

    public Optional<Group> get(){
        try {
            Document doc = client.getGroup(params);
            return Optional.of(groupParser.apply(doc.getRootElement()));
        } catch(ClientException e){
            logger.warn("Couldn't get group [{}]", params.group);
            return Optional.empty();
        }
    }
}
