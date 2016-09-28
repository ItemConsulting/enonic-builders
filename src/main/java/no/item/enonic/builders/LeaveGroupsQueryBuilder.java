package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.LeaveGroupsParams;
import no.item.enonic.models.BasicUser;
import no.item.enonic.models.Group;
import no.item.enonic.parsers.GroupParser;
import no.item.enonic.parsers.Parsers;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/*
* TODO Support both versions of ids, both #123412341234, and store:memberId
* */
public class LeaveGroupsQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(LeaveGroupsQueryBuilder.class);
    private final Client client;
    private final LeaveGroupsParams params;
    private final String leaversName;
    private final GroupParser groupParser;

    private LeaveGroupsQueryBuilder(Client client, BasicUser user, List<Group> groups){
        this.client = client;
        this.leaversName = String.format("BasicUser [%s %s]", user.firstName, user.lastName);
        this.groupParser = new GroupParser();
        params = new LeaveGroupsParams();
        params.user = user.key;
        params.groupsToLeave = groupIds(groups);
    }

    private LeaveGroupsQueryBuilder(Client client, Group group, List<Group> groups){
        this.client = client;
        this.leaversName = String.format("Group [%s]", group.name);
        this.groupParser = new GroupParser();
        params = new LeaveGroupsParams();
        params.group = group.id;
        params.groupsToLeave = groupIds(groups);
    }

    public static LeaveGroupsQueryBuilder of(Client client, BasicUser user, List<Group> groups){
        return new LeaveGroupsQueryBuilder(client, user, groups);
    }

    public static LeaveGroupsQueryBuilder of(Client client, Group group, List<Group> groups){
        return new LeaveGroupsQueryBuilder(client, group, groups);
    }

    public void execute(){
        try {
            Document doc = client.leaveGroups(params);
            List<Group> groups = Parsers.forEach(doc.getRootElement(), "group", groupParser);
            groups.stream().forEach(group -> logger.info("{} left group [{}]", leaversName, group.name));
        } catch(ClientException e){
            logger.error("{} can't leave groups {}", leaversName, params.groupsToLeave, e);
            throw e;
        }
    }

    private String[] groupIds(List<Group> groups){
        return groups.stream()
                .map(group -> group.id)
                .toArray(String[]::new);
    }
}
