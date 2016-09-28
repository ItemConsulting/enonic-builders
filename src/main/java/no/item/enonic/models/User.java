package no.item.enonic.models;

import java.time.LocalDate;

public class User extends BasicUser {
    public String firstName;
    public String lastName;
    public String address1;
    public String address2;
    public String address3;
    public String zipCode; // PostCode
    public String countryId;
    public String privateAddress;
    public String organization;
    public String phoneWork;
    public String phoneMobile;
    public String email;
    public String city; //PostOffice
    public LocalDate birthday;
    public String memberId;
    public String phonePrivate;
}
