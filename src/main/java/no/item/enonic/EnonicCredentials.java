package no.item.enonic;

public class EnonicCredentials {
    public final String username;
    public final String password;
    public final String serverUrl;

    public EnonicCredentials(String username, String password, String serverUrl) {
        this.username = username;
        this.password = password;
        this.serverUrl = serverUrl;
    }
}
