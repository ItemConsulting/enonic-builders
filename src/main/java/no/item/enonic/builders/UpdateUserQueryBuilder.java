package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.UpdateUserParams;
import com.enonic.cms.api.client.model.UpdateUserParams.UpdateStrategy;
import com.google.common.base.Strings;
import no.item.enonic.models.User;
import no.item.enonic.builders.mappers.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class UpdateUserQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(MoveContentByQueryBuilder.class);
    private final Client client;
    private final UpdateUserParams params;
    private final User user;

    private UpdateUserQueryBuilder(Client client, User user, UpdateStrategy updateStrategy, String userstore){
        this.client = client;
        this.user = user;
        params = new UpdateUserParams();
        params.userstore = userstore;
        params.email = user.email;
        params.username = username(user.phoneMobile, user.phonePrivate, user.email);
        params.userInfo = new UserInfoMapper().apply(user);
        params.updateStrategy = updateStrategy;
    }

    public static UpdateUserQueryBuilder of(Client client, User user, UpdateStrategy updateStrategy, String userstore){
        return new UpdateUserQueryBuilder(client, user, updateStrategy, userstore);
    }

    public void execute(){
        try {
            client.updateUser(params);
            logger.info("Updated user [{} {}] with id [{}]", user.firstName, user.lastName, user.id);
        } catch(ClientException e){
            logger.error("Can't update user with email [{}]", user.email, e);
            throw e;
        }
    }

    private String username(String... alternatives){
        return Arrays.stream(alternatives)
                .filter(a -> !Strings.isNullOrEmpty(a))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}