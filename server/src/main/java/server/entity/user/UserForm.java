package server.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Object used to retrieve data from client requests.
 * This object contains all attributes to create an User entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForm implements Serializable {

    /**
     * Username of user, should be unique.
     */
    private String username;

    /**
     * Password of user, should be delivered hashed.
     */
    private String password;
}
