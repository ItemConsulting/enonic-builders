package no.item.enonic;

import com.enonic.cms.api.client.Client;
import com.google.inject.PrivateModule;
import no.item.enonic.services.*;
import org.jdom.output.DOMOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnonicModule extends PrivateModule {
    private final Logger logger = LoggerFactory.getLogger(EnonicModule.class);

    private final EnonicCredentials credentials;

    public EnonicModule(EnonicCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    protected void configure() {
        // Enonic client
        bind(EnonicCredentials.class).toInstance(credentials);
        bind(Client.class).toProvider(EnonicClientProvider.class);

        // Dom outputter
        bind(DOMOutputter.class).toInstance(new DOMOutputter());

        // Expose services
        bind(ContentService.class);
        expose(ContentService.class);

        bind(GroupService.class);
        expose(GroupService.class);

        bind(UserService.class);
        expose(UserService.class);
    }
}
