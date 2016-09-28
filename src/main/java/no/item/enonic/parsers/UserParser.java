package no.item.enonic.parsers;

import no.item.enonic.models.User;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserParser implements XmlParser<User> {
    private static final Logger logger = LoggerFactory.getLogger(BasicUserParser.class);
    private static final LocalDateParser localDateFormatter = new LocalDateParser();

    @Override
    public User apply(Element root) {
        User user = new User();
        user.key = root.getAttributeValue("key");
        user.firstName = root.getChildText("first-name");
        user.lastName = root.getChildText("last-name");
        user.email = root.getChildText("email");
        user.enonicUserName = root.getChildText("name");
        user.memberId = root.getChildText("member-id");

        try {
            Element address = root.getChild("addresses").getChild("address");
            user.address2 = address.getChildText("street");
            user.zipCode = address.getChildText("postal-code");
            user.city = address.getChildText("postal-address");
        } catch(Exception e){
            logger.warn("Can't parse address for email=[{}]", user.email);
        }

        user.lastModified = localDateFormatter.apply(root.getChildText("last-modified"));

        return user;
    }
}
