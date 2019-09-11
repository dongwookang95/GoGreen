package backend.service.tab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendRequestHandlerTest {

    @Test
    void getInstance() {
        assertEquals(FriendRequestHandler.class, FriendRequestHandler.getInstance().getClass());
    }
}