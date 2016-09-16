package no.item.enonic.services;

import com.enonic.cms.api.client.Client;
import no.item.enonic.models.BasicUser;
import no.item.enonic.models.Group;
import no.item.enonic.builders.*;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class GroupService {
    private final Provider<Client> client;

    @Inject
    public GroupService(Provider<Client> client) {
        this.client = client;
    }

    public GroupQueryBuilder byName(String userstore, String name){
        return GroupQueryBuilder.of(client.get(), userstore, name);
    }

    public GroupsQueryBuilder list(String userstore){
        return GroupsQueryBuilder.of(client.get(), userstore);
    }

    public CreateGroupQueryBuilder create(String userstore, String name){
        return CreateGroupQueryBuilder.of(client.get(), userstore, name);
    }

    public JoinGroupsQueryBuilder join(BasicUser user, List<Group> groups) {
        return JoinGroupsQueryBuilder.of(client.get(), user, groups);
    }

    public LeaveGroupsQueryBuilder leave(BasicUser user, List<Group> groups) {
        return LeaveGroupsQueryBuilder.of(client.get(), user, groups);
    }
}
