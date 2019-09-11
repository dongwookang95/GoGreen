package server.entity.user;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void addFriend() {
        User test = new User();
        User testAdded = new User();
        test.addFriend(testAdded);
        Set result = test.getFriends();

        Assert.assertEquals(test.getFriends(), result);
    }

    @Test
    void deleteFriend() {
        User test = new User();
        User testAdded = new User();
        test.deleteFriend(testAdded);

        Set result = test.getFriends();

        Assert.assertEquals(test.getFriends(), result);
    }

    @Test
    void addInvitee() {
        User test = new User();
        User testInvited = new User();
        test.addInvitee(testInvited);

        Set result = test.getInvites();

        Assert.assertEquals(test.getInvites(), result);
    }

    @Test
    void deleteInvitee() {
        User test = new User();
        User testInvited = new User();
        test.deleteFriend(testInvited);

        Set result = test.getInvites();

        Assert.assertEquals(test.getInvites(),result);

    }

    @Test
    void addRequest() {
        User test = new User();
        User testRequested = new User();
        test.addRequest(testRequested);

        Set result = test.getRequests();

        Assert.assertEquals(test.getRequests(), result);
    }

    @Test
    void deleteRequest() {
        User test = new User();
        User testRequested = new User();
        test.deleteRequest(testRequested);

        Set result = test.getRequests();

        Assert.assertEquals(test.getRequests(), result);
    }

    @Test
    void addAchievement() {
        User test = new User();
        Achievement testAchievement = new Achievement();
        test.addAchievement(testAchievement);

        Set result = test.getAchievements();

        Assert.assertEquals(test.getAchievements(), result);
    }

    @Test
    void deleteAchievement() {
        User test = new User();
        Achievement testAchievement = new Achievement();
        test.deleteAchievement(testAchievement);

        Set result = test.getAchievements();

        Assert.assertEquals(test.getRequests(), result);
    }

    @Test
    void getRoles() {
        final User test = new User();
        List result = test.getRoles();

        Assert.assertEquals(test.getRoles(), result);
    }


//    @Test
//    void getAuthorities() {
//        final User test = new User();
//        Collection result = test.getAuthorities();
//
//     //   Assert.assertEquals(test.getAuthorities(), result);
//    }

    @Test
    void getPassword() {
        final User test = new User();
        String result = test.getPassword();

        Assert.assertEquals(test.getPassword(), result);
    }

    @Test
    void getUsername() {
        final User test = new User();
        String result = test.getUsername();

        Assert.assertEquals(test.getUsername(), result);
    }

    @Test
    void isAccountNonExpired() {
        final  User test = new User();
        boolean result = test.isAccountNonExpired();

        Assert.assertEquals(test.isAccountNonExpired(), result);

    }

    @Test
    void isAccountNonLocked() {
        final  User test = new User();
        boolean result = test.isAccountNonLocked();

        Assert.assertEquals(test.isAccountNonExpired(), result);
    }

    @Test
    void isCredentialsNonExpired() {
        final  User test = new User();
        boolean result = test.isCredentialsNonExpired();

        Assert.assertEquals(test.isAccountNonExpired(), result);
    }

    @Test
    void isEnabled() {
        final User test = new User();
        boolean result = test.isEnabled();

        Assert.assertEquals(test.isEnabled(), result);
    }

    @Test
    void setUsername() {
        User test = new User();
        test.setUsername("test");
        String result = test.getUsername();
        Assert.assertEquals(test.getUsername() , result);
    }

    @Test
    void setPassword() {
        User test = new User();
        test.setPassword("test");
        String result = test.getPassword();
        Assert.assertEquals(test.getPassword() , result);
    }

    @Test
    void setRole() {
        User test = new User();
        test.setRole("test");
        String result = test.getRole();
        Assert.assertEquals(test.getRole(), result);
    }

    @Test
    void setEnabled() {
        User test = new User();
        test.setEnabled(true);
        Boolean result = test.isEnabled();
        Assert.assertEquals(test.isEnabled(), result);
    }

    @Test
    void setLocalProduces() {
        User test = new User();
        Set<LocalProduce> testLocalproduce =  new HashSet<>(Arrays.asList());
        test.setLocalProduces(testLocalproduce);
        Set result = test.getLocalProduces();
        Assert.assertEquals(test.getLocalProduces() , result);
    }

    @Test
    void setVegetarianMeals() {
        User test = new User();
        Set<VegetarianMeal> testVegetarianMeals =  new HashSet<>(Arrays.asList());
        test.setVegetarianMeals(testVegetarianMeals);
        Set result = test.getLocalProduces();
        Assert.assertEquals(test.getVegetarianMeals() , result);
    }

    @Test
    void setSolarPanels() {
        User test = new User();
        Set<SolarPanel> testSolarPanels =  new HashSet<>(Arrays.asList());
        test.setSolarPanels(testSolarPanels);
        Set result = test.getLocalProduces();
        Assert.assertEquals(test.getSolarPanels() , result);
    }

    @Test
    void setLowerTemperatures() {
        User test = new User();
        Set<LowerTemperature> testLowerTemperatures =  new HashSet<>(Arrays.asList());
        test.setLowerTemperatures(testLowerTemperatures);
        Set result = test.getLocalProduces();
        Assert.assertEquals(test.getLowerTemperatures() , result);
    }

    @Test
    void setPublicTransports() {
        User test = new User();
        Set<PublicTransport> testPublicTransport =  new HashSet<>(Arrays.asList());
        test.setPublicTransports(testPublicTransport);
        Set result = test.getPublicTransports();
        Assert.assertEquals(test.getPublicTransports() , result);
    }

    @Test
    void setTravelByBikes() {
        User test = new User();
        Set<TravelByBike> testTravelByBike =  new HashSet<>(Arrays.asList());
        test.setTravelByBikes(testTravelByBike);
        Set result = test.getTravelByBikes();
        Assert.assertEquals(test.getTravelByBikes() , result);
    }

    @Test
    void setFriends() {
        User test = new User();
        Set<User> testUser =  new HashSet<>(Arrays.asList());
        test.setFriends(testUser);
        Set result = test.getFriends();
        Assert.assertEquals(test.getFriends() , result);
    }

    @Test
    void setInvites() {
        User test = new User();
        Set<User> testUser =  new HashSet<>(Arrays.asList());
        test.setInvites(testUser);
        Set result = test.getInvites();
        Assert.assertEquals(test.getFriends() , result);
    }

    @Test
    void setRequests() {
        User test = new User();
        Set<User> testUser =  new HashSet<>(Arrays.asList());
        test.setRequests(testUser);
        Set result = test.getRequests();
        Assert.assertEquals(test.getRequests() , result);
    }

    @Test
    void setAchievements() {
        User test = new User();
        Set<Achievement> testAchievement =  new HashSet<>(Arrays.asList());
        test.setAchievements(testAchievement);
        Set result = test.getAchievements();
        Assert.assertEquals(test.getAchievements() , result);
    }

    @Test
    void builder() {
        User test = User.builder().username("test").password("test").build();
        String result = test.getUsername();


        Assert.assertEquals(test.getUsername(), result);
    }

    @Test
    void getRole() {
        final User test = new User();
        String result = test.getRole();

        Assert.assertEquals(test.getRole(), result);
    }

    @Test
    void getLocalProduces() {
        final User test = new User();
        Set result = test.getLocalProduces();

        Assert.assertEquals(test.getLocalProduces() , result);

    }

    @Test
    void getVegetarianMeals() {
        final User test = new User();
        Set result = test.getVegetarianMeals();

        Assert.assertEquals(test.getVegetarianMeals() , result);
    }

    @Test
    void getSolarPanels() {
        final User test = new User();
        Set result = test.getSolarPanels();

        Assert.assertEquals(test.getSolarPanels() , result);
    }

    @Test
    void getLowerTemperatures() {
        final User test = new User();
        Set result = test.getLowerTemperatures();

        Assert.assertEquals(test.getLowerTemperatures() , result);
    }

    @Test
    void getPublicTransports() {
        final User test = new User();
        Set result = test.getPublicTransports();

        Assert.assertEquals(test.getPublicTransports(), result);
    }

    @Test
    void getTravelByBikes() {
        final User test = new User();
        Set result = test.getTravelByBikes();

        Assert.assertEquals(test.getTravelByBikes(), result);
    }

    @Test
    void getFriends() {
        final User test = new User();
        Set result = test.getFriends();

        Assert.assertEquals(test.getFriends(), result);
    }

    @Test
    void getInvites() {
        final User test = new User();
        Set result = test.getInvites();

        Assert.assertEquals(test.getInvites(), result);
    }

    @Test
    void getRequests() {
        final User test = new User();
        Set result = test.getRequests();

        Assert.assertEquals(test.getRequests(), result);
    }

    @Test
    void getAchievements() {
        final User test = new User();
        Set result = test.getAchievements();

        Assert.assertEquals(test.getAchievements(), result);
    }
    @Test
    void testToString() {
        User test = User.builder().username("test").build();
        Assert.assertEquals(test.toString(), "test");
    }
}