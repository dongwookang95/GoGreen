package backend.service.tab;


public class FriendRequestHandler {

    private FriendRequestHandler() {
    }

    public static FriendRequestHandler getInstance() {
        return SearchRequestsHelper.INSTANCE;
    }

    private static class SearchRequestsHelper {
        private static final FriendRequestHandler INSTANCE = new FriendRequestHandler();
    }

    /*
    Friend requests coming soon
     */
}
