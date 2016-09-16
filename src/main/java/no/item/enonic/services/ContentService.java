package no.item.enonic.services;

import com.enonic.cms.api.client.Client;
import no.item.enonic.builders.ContentByCategoryQueryBuilder;
import no.item.enonic.builders.ContentBySectionQueryBuilder;

import javax.inject.Inject;
import javax.inject.Provider;

public class ContentService {
    private final Provider<Client> client;

    @Inject
    public ContentService(Provider<Client> client) {
        this.client = client;
    }

    public ContentBySectionQueryBuilder section(int[] ids){
        return ContentBySectionQueryBuilder.of(client.get(), ids);
    }

    public ContentByCategoryQueryBuilder category(int[] categoryKeys){
        return ContentByCategoryQueryBuilder.of(client.get(), categoryKeys);
    }

}
