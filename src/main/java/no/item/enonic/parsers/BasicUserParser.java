package no.item.enonic.parsers;

import no.item.enonic.formatters.LocalDateFormatter;
import no.item.enonic.models.BasicUser;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Locale;

public class BasicUserParser implements XmlParser<BasicUser> {
    private static final Logger logger = LoggerFactory.getLogger(BasicUserParser.class);
    private static final LocalDateFormatter localDateFormatter = new LocalDateFormatter();

    @Override
    public BasicUser apply(Element element) {
        BasicUser user = new BasicUser();
        user.id = element.getAttributeValue("key");
        user.firstName = element.getChildText("first-name");
        user.lastName = element.getChildText("last-name");
        user.email = element.getChildText("email");
        user.enonicUsername = element.getChildText("name");

        try {
            user.lastModified = localDateFormatter.parse(element.getChildText("last-modified"), Locale.getDefault());
        } catch (ParseException e) {
            logger.warn("Can't parse lastModified date", e);
        }

        return user;
    }
}
