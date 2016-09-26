package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.JoinGroupsParams;
import no.item.enonic.models.Group;
import no.item.enonic.models.BasicUser;
import no.item.enonic.parsers.GroupParser;
import no.item.enonic.parsers.Parsers;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class JoinGroupsQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(GroupsQueryBuilder.class);
    private final Client client;
    private final JoinGroupsParams params;
    private final String joinersName;
    private final GroupParser groupParser;

    private JoinGroupsQueryBuilder(Client client, BasicUser user, List<Group> groups){
        this.client = client;
        this.joinersName = String.format("BasicUser [%s %s]", user.firstName, user.lastName);
        this.groupParser = new GroupParser();
        params = new JoinGroupsParams();
        params.user = "#" + user.key;
        params.groupsToJoin = groupIds(groups);
    }

    private JoinGroupsQueryBuilder(Client client, Group group, List<Group> groups){
        this.client = client;
        this.joinersName = String.format("Group [%s]", group.name);
        this.groupParser = new GroupParser();
        params = new JoinGroupsParams();
        params.group = "#" + group.id;
        params.groupsToJoin = groupIds(groups);
    }

    public static JoinGroupsQueryBuilder of(Client client, BasicUser user, List<Group> groups){
        return new JoinGroupsQueryBuilder(client, user, groups);
    }

    public static JoinGroupsQueryBuilder of(Client client, Group group, List<Group> groups){
        return new JoinGroupsQueryBuilder(client, group, groups);
    }

    public void execute(){
        try {
            Document doc = client.joinGroups(params);
            List<Group> groups = Parsers.forEach(doc.getRootElement(), "group", groupParser);
            groups.stream().forEach(group -> logger.info("{} joined group [{}]", joinersName, group.name));
        } catch(ClientException e){
            logger.error("{} can't join groups {}", joinersName, params.groupsToJoin, e);
        }
    }

    private String[] groupIds(List<Group> groups){
        return groups.stream()
                .map(group -> "#" + group.id)
                .toArray(String[]::new);
    }
}
