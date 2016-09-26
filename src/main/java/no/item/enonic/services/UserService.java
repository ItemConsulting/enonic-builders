package no.item.enonic.services;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.model.UpdateUserParams;
import no.item.enonic.models.User;
import no.item.enonic.builders.*;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class UserService {
    private final Provider<Client> client;

    @Inject
    public UserService(Provider<Client> client) {
        this.client = client;
    }

    public GetUserQueryBuilder get(String userstore, String username) {
        return GetUserQueryBuilder.of(client.get(), userstore, username);
    }

    public GetUsersQueryBuilder list(String userstore){
        return GetUsersQueryBuilder.of(client.get(), userstore);
    }
    
    public GetUsersQueryBuilder listPersons(String userstore){
        return GetUsersQueryBuilder.of(client.get(), userstore);
    }

    public ChangeUserPasswordBuilder changePassword(String userstore, String username, String password){
        return ChangeUserPasswordBuilder.of(client.get(), userstore, username, password);
    }

    public CreateUserQueryBuilder create(User user, String userstore, String password){
        return CreateUserQueryBuilder.of(client.get(), user, userstore, password);
    }

    public UpdateUserQueryBuilder update(User user, String userstore){
        return UpdateUserQueryBuilder.of(client.get(), user, UpdateUserParams.UpdateStrategy.MODIFY, userstore);
    }

    public DeleteUserQueryBuilder delete(String key){
        return DeleteUserQueryBuilder.of(client.get(), key);
    }

    public UpdateUserQueryBuilder updateBasicData(String firstName, String lastName, String phoneMobile, String email, Integer memberId, String userstore){
        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
        user.phoneMobile = phoneMobile;
        user.email = email;
        user.key = String.valueOf(memberId);
        return update(user, userstore);
    }
}