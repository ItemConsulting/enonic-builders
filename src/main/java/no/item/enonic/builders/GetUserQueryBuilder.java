package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.GetUserParams;
import no.item.enonic.models.User;
import no.item.enonic.parsers.UserParser;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GetUserQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(GetUserQueryBuilder.class);
    private final Client client;
    private final GetUserParams params;
    private final UserParser userParser;

    private GetUserQueryBuilder(Client client, String userstore, String username){
        this.client = client;
        this.userParser = new UserParser();
        params = new GetUserParams();
        params.user = userstore + "\\" + username;
    }

    public static GetUserQueryBuilder of(Client client, String userstore, String username){
        return new GetUserQueryBuilder(client, userstore, username);
    }

    public GetUserQueryBuilder includeMemberships(){
        params.includeMemberships = true;
        return this;
    }

    public GetUserQueryBuilder normalizeGroups(){
        params.normalizeGroups = true;
        return this;
    }

    public GetUserQueryBuilder includeCustomUserFields(){
        params.includeCustomUserFields = true;
        return this;
    }

    public Optional<User> get(){
        try {
            Element root = client.getUser(params).getRootElement();
            return Optional.of(userParser.apply(root));
        } catch(ClientException e){
            logger.warn("Can't get user [{}]", params.user, e);
            return Optional.empty();
        }
    }
}
