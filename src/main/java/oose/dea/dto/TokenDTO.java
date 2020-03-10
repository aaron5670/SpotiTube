package oose.dea.dto;

public class TokenDTO {
    private String user;
    private String token;

    public TokenDTO(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
