package no.item.enonic.parsers;

import org.jdom.Element;

import java.util.function.Function;

public interface XmlParser <R> extends Function<Element, R> { }