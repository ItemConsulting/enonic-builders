package no.item.enonic.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class LocalDateParser implements Function<String, LocalDate> {
    @Override
    public LocalDate apply(String text) {
        return LocalDate.parse(text.split("T")[0], DateTimeFormatter.ISO_LOCAL_DATE);
    }
}