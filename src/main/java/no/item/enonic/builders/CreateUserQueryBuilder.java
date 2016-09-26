package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.CreateUserParams;
import com.google.common.base.Strings;
import no.item.enonic.models.User;
import no.item.enonic.builders.mappers.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CreateUserQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(CreateUserQueryBuilder.class);
    private final Client client;
    private final CreateUserParams params;
    private final User user;

    private CreateUserQueryBuilder(Client client, User user, String userstore, String password){
        this.client = client;
        this.user = user;
        params = new CreateUserParams();
        params.userstore = userstore;
        params.password = password;
        params.email = user.email.trim().toLowerCase();
        params.username = this.user.enonicUsername = username(user.phoneMobile, user.phonePrivate, user.email);
        params.userInfo = new UserInfoMapper().apply(user);
    }

    public static CreateUserQueryBuilder of(Client client, User user, String userstore, String password){
        return new CreateUserQueryBuilder(client, user, userstore, password);
    }

    public User execute(){
        try {
            user.key = client.createUser(params);
            logger.info("Created new user [{} {}] with key [{}]", user.firstName, user.lastName, user.key);
            return user;
        } catch(ClientException e){
            logger.error("Can't create user with email [{}]", user.email, e);
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
