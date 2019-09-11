package backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class defining ResponseConnection from the server after a user has logged in.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConnectionResponse {

    private String username;
    private String token;
}
