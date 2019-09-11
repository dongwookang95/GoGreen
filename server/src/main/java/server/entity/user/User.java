package server.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User entity which corresponds to 'users' table in DB.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {

    /**
     * Id of User object, used as primary key in DB,
     * since username is unique.
     */
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * Password of user, is stored encoded here.
     */
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Role of user, determines what user can do in API and DB.
     */
    @JsonIgnore
    @Column(name = "role")
    private String role;

    /**
     * Tells you if this user is enabled or not.
     */
    @JsonIgnore
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * One to many relation with LocalProduce, since an User can
     * have multiple LocalProduce objects.
     * Ignoring this property avoids infinite recursion problem.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private Set<LocalProduce> localProduces = new HashSet<>();

    /**
     * One to many relation with VegetarianMeal, since an User can
     * have multiple VegetarianMeal objects.
     * Ignoring this property avoids infinite recursion problem.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private Set<VegetarianMeal> vegetarianMeals = new HashSet<>();

    /**
     * One to many relation with SolarPanel, since an User can have
     * multiple SolarPanel objects.
     * Ignoring this property avoids infinite recursion problem.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private Set<SolarPanel> solarPanels = new HashSet<>();

    /**
     * One to many relation with LowerTemperature, since an User can
     * have multiple LowerTemperature objects.
     * Ignoring this property avoids infinite recursion problem.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private Set<LowerTemperature> lowerTemperatures = new HashSet<>();

    /**
     * One to many relation with LocalProduce, since an User can
     * have multiple PublicTransport objects.
     * Ignoring this property avoids infinite recursion problem.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private Set<PublicTransport> publicTransports = new HashSet<>();

    /**
     * One to many relation with TravelByBike, since an User can
     * have multiple TravelByBike objects.
     * Ignoring this property avoids infinite recursion problem.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private Set<TravelByBike> travelByBikes = new HashSet<>();

    /**
     * Many to many relation with User for having friends, since
     * this a self to self relation.
     * Ignoring this property avoids infinite recursion problem.
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "friends",
            joinColumns = { @JoinColumn(name = "my_username") },
            inverseJoinColumns = { @JoinColumn(name = "friend_username") })
    @JsonIgnoreProperties({"friends", "invites", "requests"})
    private Set<User> friends = new HashSet<>();

    /**
     * Many to many relation with User for having invites, since
     * this a self to self relation.
     * Ignoring this property avoids infinite recursion problem.
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "friend_requests",
            joinColumns = { @JoinColumn(name = "friend_username") },
            inverseJoinColumns = { @JoinColumn(name = "my_username") })
    @JsonIgnoreProperties({"friends", "invites", "requests"})
    private Set<User> invites = new HashSet<>();

    /**
     * Many to many relation with User for having requests, since this
     * a self to self relation.
     * Ignoring this property avoids infinite recursion problem.
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "friend_requests",
            joinColumns = { @JoinColumn(name = "my_username") },
            inverseJoinColumns = { @JoinColumn(name = "friend_username") })
    @JsonIgnoreProperties({"friends", "invites", "requests"})
    private Set<User> requests = new HashSet<>();

    /**
     * One to many relation with Achievement.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private Set<Achievement> achievements = new HashSet<>();

    /**
     * Add an User to friends set.
     * @param friend User that needs to be added
     */
    public void addFriend(final User friend) {
        friends.add(friend);
    }

    /**
     * Delete an User from friends set.
     * @param friend User that needs to be removed
     */
    public void deleteFriend(final User friend) {

        friends.remove(friend);
    }

    /**
     * Add an User to invites set.
     * @param invitee User that needs to be added
     */
    public void addInvitee(final User invitee) {
        invites.add(invitee);
    }

    /**
     * Delete an User from invites set.
     * @param invitee User that needs to be removed
     */
    public void deleteInvitee(final User invitee) {
        invites.remove(invitee);
    }

    /**
     * Add an User to requests set.
     * @param requesting User that needs to be added
     */
    public void addRequest(final User requesting) {
        requests.add(requesting);
    }

    /**
     * Delete an User from requests set.
     * @param requesting User that needs to be removed
     */
    public void deleteRequest(final User requesting) {
        requests.remove(requesting);
    }

    /**
     * Add an Achievement to achievements set.
     * @param achievement Achievement that needs to be added
     */
    public void addAchievement(final Achievement achievement) {
        achievements.add(achievement);
    }

    /**
     * Delete an Achievement from achievements set.
     * @param achievement Achievement that needs to be removed
     */
    public void deleteAchievement(final Achievement achievement) {
        achievements.remove(achievement);
    }

    /**
     * Get users roles.
     * @return List of roles.
     */
    @JsonIgnore
    public List<String> getRoles() {
        List<String> list = new ArrayList<>();
        list.add(role);
        return list;
    }

    /**
     * Override function from UserDetails to get users role.
     * @return Collection of roles
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(role));

        return list;
    }

    /**
     * Get users password.
     * @return password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Get users username.
     * @return username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Check if account is not expired.
     * @return default true
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if account is not locked.
     * @return default true
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if credentials are not expired.
     * @return default true
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if account is enabled.
     * @return default true
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "test";
    }
}
