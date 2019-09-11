package server.entity.user;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserFormTest {

    @Test
    void builder() {
            User a = User.builder().username("test").password("test").build();

            String resultName = a.getUsername();
            String resultPassword = a.getPassword();
            assertEquals(a.getUsername() , resultName);
            assertEquals(a.getPassword(), resultPassword);





        }
    }
