package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.GetGroupsParams;
import no.item.enonic.models.Group;
import no.item.enonic.parsers.GroupParser;
import no.item.enonic.parsers.Parsers;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class GroupsQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(GroupsQueryBuilder.class);
    private final Client client;
    private final GetGroupsParams params;
    private final GroupParser groupParser;

    private GroupsQueryBuilder(Client client, String userStore){
        this.client = client;
        this.groupParser = new GroupParser();
        params = new GetGroupsParams();
        params.userStore = userStore;
    }

    public static GroupsQueryBuilder of(Client client, String groupName){
        return new GroupsQueryBuilder(client, groupName);
    }

    public GroupsQueryBuilder groupTypes(String[] groupTypes){
        params.groupTypes = groupTypes;
        return this;
    }

    public GroupsQueryBuilder from(int index){
        params.index = index;
        return this;
    }

    public GroupsQueryBuilder size(int count){
        params.count = count;
        return this;
    }

    public GroupsQueryBuilder includeMembers(){
        params.includeMembers = true;
        return this;
    }

    public GroupsQueryBuilder includeBuiltInGroups(){
        params.includeMembers = true;
        return this;
    }

    public GroupsQueryBuilder includeMemberships(){
        params.includeMemberships = true;
        return this;
    }

    public GroupsQueryBuilder includeDeletedGroups(){
        params.includeDeletedGroups = true;
        return this;
    }

    public List<Group> get(){
        try {
            Document doc = client.getGroups(params);
            return Parsers.forEach(doc.getRootElement(), "group", groupParser);
        } catch(ClientException e){
            logger.error("Couldn't get groups", e);
            return Collections.emptyList();
        }
    }
}
