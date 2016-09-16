package no.item.enonic;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientFactory;
import com.enonic.cms.api.client.RemoteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Objects;

public class EnonicClientProvider implements Provider<Client> {
    private final Logger logger = LoggerFactory.getLogger(EnonicClientProvider.class);

    @Inject
    private EnonicCredentials credentials;

    @Override
    public Client get() {
        RemoteClient client = ClientFactory.getRemoteClient(credentials.serverUrl, true);
        String enonicUser = client.login(credentials.username, credentials.password);

        if(!Objects.equals(enonicUser, "admin")){
            logger.info("Logged into enonic as [{}]", enonicUser);
        }

        return client;
    }
}
