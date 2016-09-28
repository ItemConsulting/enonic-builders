package no.item.enonic.parsers;

import no.item.enonic.models.BasicUser;
import org.jdom.Element;

public class BasicUserParser implements XmlParser<BasicUser> {
    private static final LocalDateParser localDateFormatter = new LocalDateParser();

    @Override
    public BasicUser apply(Element element) {
        BasicUser user = new BasicUser();
        user.key = element.getAttributeValue("key");
        user.firstName = element.getChildText("first-name");
        user.lastName = element.getChildText("last-name");
        user.email = element.getChildText("email");
        user.enonicUserName = element.getChildText("name");
        user.lastModified = localDateFormatter.apply(element.getChildText("last-modified"));
        return user;
    }
}
