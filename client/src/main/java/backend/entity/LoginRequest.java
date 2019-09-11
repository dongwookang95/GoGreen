package backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class defining LoginRequest that is sent to the server.
 */
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    /**
     * Username.
     */
    private String username;

    /**
     * Password of the user.
     */
    private String password;

}
