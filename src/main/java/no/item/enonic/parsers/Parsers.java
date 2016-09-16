package no.item.enonic.parsers;

import org.jdom.Element;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Parsers {
	@SuppressWarnings("unchecked")
    public static <R> List<R> forEach(Element root, String name, Function<Element, ? extends R> mapper){
        return ((List<Element>) root.getChildren(name)).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}