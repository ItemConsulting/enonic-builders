package no.item.enonic.builders.mappers;

import com.enonic.cms.api.client.model.user.Address;
import com.enonic.cms.api.client.model.user.UserInfo;
import com.google.common.base.Strings;
import no.item.enonic.models.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserInfoMapper implements Function<User, UserInfo> {
    @Override
    public UserInfo apply(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setMemberId(String.valueOf(user.key));
        userInfo.setFirstName(user.firstName);
        userInfo.setLastName(user.lastName);
        userInfo.setBirthday(oldDateFormat(user.birthday));
        userInfo.setOrganization(user.organization);
        userInfo.setPhone(user.phonePrivate);
        userInfo.setMobile(user.phoneMobile);
        userInfo.setFax(user.phoneWork);
        userInfo.setAddresses(address(user));
        return userInfo;
    }

    private Address address(User user) {
        if(user.address1 != null || user.address2 != null || user.address3 != null) {
            Address address = new Address();
            String[] addrArr = { user.address1, user.address2, user.address3 };

            String addrStr = Arrays.stream(addrArr)
                    .filter(str -> !Strings.isNullOrEmpty(str))
                    .collect(Collectors.joining(" "));

            address.setStreet(addrStr);
            address.setPostalCode(user.zipCode);
            address.setPostalAddress(user.city);
            address.setIsoCountry("NO");
            return address;
        } else {
            return null;
        }
    }

    private Date oldDateFormat(LocalDate date){
        if(Objects.nonNull(date)){
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }
}