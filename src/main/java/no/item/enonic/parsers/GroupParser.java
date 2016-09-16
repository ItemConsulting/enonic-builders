package no.item.enonic.parsers;

import no.item.enonic.models.Group;
import org.jdom.Element;

public class GroupParser implements XmlParser<Group> {
    @Override
    public Group apply(Element element) {
        Group group = new Group();
        group.id = element.getAttribute("key").getValue();
        group.name = element.getChildText("name");
        group.description = element.getChildText("description");
        group.userstore = element.getChildText("userstore");
        group.restricted = Boolean.valueOf(element.getChildText("restricted"));

        return group;
    }
}
