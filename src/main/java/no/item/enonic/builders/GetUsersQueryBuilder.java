package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.GetUsersParams;
import no.item.enonic.models.User;
import no.item.enonic.models.BasicUser;
import no.item.enonic.parsers.BasicUserParser;
import no.item.enonic.parsers.Parsers;
import no.item.enonic.parsers.UserParser;
import no.item.enonic.parsers.XmlParser;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class GetUsersQueryBuilder {
    private final Logger logger = LoggerFactory.getLogger(GetUsersQueryBuilder.class);
    private final Client client;
    private final GetUsersParams params;
    private final BasicUserParser basicUserParser;
    private final UserParser userParser;

    private GetUsersQueryBuilder(Client client, String userstore){
        this.client = client;
        this.basicUserParser = new BasicUserParser();
        this.userParser = new UserParser();
        params = new GetUsersParams();
        params.userStore = userstore;
    }

    public static GetUsersQueryBuilder of(Client client, String userstore){
        return new GetUsersQueryBuilder(client, userstore);
    }

    public GetUsersQueryBuilder from(int index){
        params.index = index;
        return this;
    }

    public GetUsersQueryBuilder size(int count){
        params.count = count;
        return this;
    }

    public GetUsersQueryBuilder includeMemberships(){
        params.includeMemberships = true;
        return this;
    }
    
    public GetUsersQueryBuilder normalizeGroups(){
        params.normalizeGroups = true;
        return this;
    }

    public GetUsersQueryBuilder includeCustomUserFields(){
        params.includeCustomUserFields = true;
        return this;
    }

    public List<BasicUser> get(){
        return get(basicUserParser);
    }

    public List<User> getPersons(){
        includeCustomUserFields();
        return get(userParser);
    }

    private <R extends BasicUser> List<R> get(XmlParser<R> mapper){
        try {
            Element root = client.getUsers(params).getRootElement();
            return Parsers.forEach(root, "user", mapper);
        } catch(ClientException e){
            logger.error("Can't get users in user store [{}]", params.userStore, e);
            throw e;
        }
    }

}
