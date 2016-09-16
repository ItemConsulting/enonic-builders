package no.item.enonic.formatters;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter  {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public LocalDate parse(String text, Locale locale) throws ParseException {
        String date = text.split("T")[0];
        return LocalDate.parse(date, formatter);
    }

    public String print(LocalDate localDate, Locale locale) {
        return localDate.format(formatter);
    }
}


