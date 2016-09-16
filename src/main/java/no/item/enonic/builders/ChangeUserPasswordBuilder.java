package no.item.enonic.builders;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientException;
import com.enonic.cms.api.client.model.ChangeUserPasswordParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeUserPasswordBuilder {
    private final Logger logger = LoggerFactory.getLogger(ChangeUserPasswordBuilder.class);
    private final Client client;
    private final ChangeUserPasswordParams params;

    private ChangeUserPasswordBuilder(Client client, String userstore, String email, String password){
        this.client = client;
        this.params = new ChangeUserPasswordParams();
        params.userstore = userstore;
        params.username = email;
        params.password = password;
    }

    /**
     * Creates a change password builder
     * @param client A preconfigured Enonic client, already logged in
     * @param userstore Name of userstore
     * @param username Username in the store
     * @param password New password of the user
     * @return A builder
     */
    public static ChangeUserPasswordBuilder of(Client client, String userstore, String username, String password){
        return new ChangeUserPasswordBuilder(client, userstore, username, password);
    }

    public void execute(){
        try {
            client.changeUserPassword(params);
            logger.info("Password changed for user=[{}]", params.username);
        } catch(ClientException e){
            logger.error("Can't change password for user email=[{}]", params.username, e);
            throw e;
        }
    }
}
