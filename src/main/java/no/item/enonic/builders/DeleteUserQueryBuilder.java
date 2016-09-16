package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.DeleteUserParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteUserQueryBuilder {

    private final Logger logger = LoggerFactory.getLogger(DeleteUserQueryBuilder.class);
    private final Client client;
    private final DeleteUserParams params;

    private DeleteUserQueryBuilder(Client client, String key){
        this.client = client;
        params = new DeleteUserParams();
        params.user = "#" + key;
    }

    public static DeleteUserQueryBuilder of(Client client, String key){
        return new DeleteUserQueryBuilder(client, key);
    }


    public void execute(){
        try {
            client.deleteUser(params);
        } catch(ClientException e){
            logger.warn("Can't delete user [{}]", params.user, e);
        }
    }
}
