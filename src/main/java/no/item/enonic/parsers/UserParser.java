package no.item.enonic.parsers;

import no.item.enonic.formatters.LocalDateFormatter;
import no.item.enonic.models.User;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Locale;

public class UserParser implements XmlParser<User> {
    private static final Logger logger = LoggerFactory.getLogger(BasicUserParser.class);
    private static final LocalDateFormatter localDateFormatter = new LocalDateFormatter();

    @Override
    public User apply(Element root) {
        User user = new User();
        user.id = root.getAttributeValue("key");
        user.firstName = root.getChildText("first-name");
        user.lastName = root.getChildText("last-name");
        user.email = root.getChildText("email");
        user.enonicUsername = root.getChildText("name");
        user.username = root.getChildText("member-id");

        try {
            Element address = root.getChild("addresses").getChild("address");

            user.address2 = address.getChildText("street");
            user.zipCode = address.getChildText("postal-code");
            user.city = address.getChildText("postal-address");
        } catch(Exception e){
            logger.warn("Can't parse address for email=[{}]", user.email);
        }

        try {
            user.lastModified = localDateFormatter.parse(root.getChildText("last-modified"), Locale.getDefault());
        } catch (ParseException e) {
            logger.warn("Can't parse lastModified date", e);
        }

        return user;
    }
}
