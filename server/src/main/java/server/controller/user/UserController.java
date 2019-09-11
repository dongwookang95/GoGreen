package server.controller.user;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import server.entity.user.Achievement;
import server.entity.user.User;
import server.entity.user.UserForm;
import server.exception.DataBaseException;
import server.repository.user.AchievementRepository;
import server.security.jwt.JwtTokenProvider;
import server.service.calculator.CalculatorService;
import server.service.habit.CategoryService;
import server.service.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles all the requests concerning User objects.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final long FOOD_CATEGORY_ID = 1;
    private static final long TRANSPORT_CATEGORY_ID = 2;
    private static final long ENERGY_CATEGORY_ID = 3;

    /**
     * Autowired UserService to use its functionality.
     */
    private final UserService userService;

    /**
     * Autowired CategoryService to use its functionality.
     */
    private final CategoryService categoryService;

    /**
     * Autowired CalculatorService to use its functionality.
     */
    private final CalculatorService calculatorService;

    /**
     * Autowired AchievementRepository to use its functionality.
     */
    private final AchievementRepository achievementRepository;

    /**
     * Autowired PasswordEncoder to encode passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Autowired AuthenticationManager for authenticating users.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Autowired UserService to use its functionality.
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Injecting repositories into service.
     * @param userService UserService
     * @param categoryService CategoryService
     * @param calculatorService CalculatorService
     * @param achievementRepository achievement repository
     * @param passwordEncoder PasswordEncoder
     * @param authenticationManager AuthenticationManager
     * @param jwtTokenProvider JwtTokenProvider
     */
    @Autowired
    public UserController(UserService userService,
                          CategoryService categoryService,
                          CalculatorService calculatorService,
                          AchievementRepository achievementRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.calculatorService = calculatorService;
        this.achievementRepository = achievementRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    //  [GET MAPPING]

    /**
     * Find all users in DB.
     * @return List of all users names currently in DB
     */
    @GetMapping("/all")
    public ResponseEntity allUsers() {
        List<User> users = userService.findAll();
        List<String> usernameList = new ArrayList<>();

        for (User user: users) {
            usernameList.add(user.getUsername());
        }

        return ok(usernameList);
    }

    /**
     * Getting information about current user.
     * @param userDetails automatically gets sent along by JWT token header
     * @return User object of current user
     */
    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal
                                      final UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ok(user);
    }

    @GetMapping("/total")
    public ResponseEntity currentUserTotal(@AuthenticationPrincipal
                                      final UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ok(calculatorService.totalScoreForCategoryForUser(user, "Food"));
    }

    /**
     * Search for a user in DB by id.
     * @param username Path variable which corresponds to User username
     * @return User if an user exists with that id
     */
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity findByUsername(@PathVariable("username")
                                             final String username) {
        User user = userService.findByUsername(username);
        return ok(user);
    }


    // [POST MAPPING]

    /**
     * Register and create new user in DB.
     * @param form User object to be created.
     * @return new user
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody final UserForm form,
                                   final HttpServletRequest request) {

        User user;

        try {
            user = userService.createUser(User.builder()
                    .username(form.getUsername())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .role("ROLE_USER")
                    .achievements(new HashSet<>())
                    .vegetarianMeals(new HashSet<>())
                    .localProduces(new HashSet<>())
                    .solarPanels(new HashSet<>())
                    .lowerTemperatures(new HashSet<>())
                    .travelByBikes(new HashSet<>())
                    .publicTransports(new HashSet<>())
                    .enabled(true)
                    .build()
            );

        } catch (DataBaseException e) {
            throw new DataBaseException("Register went wrong.");
        }

        user = userService.findByUsername(form.getUsername());

        Achievement foodAchievement = Achievement.builder().category(
                categoryService.findCategoryById(FOOD_CATEGORY_ID)).build();
        Achievement energyAchievement = Achievement.builder().category(
                categoryService.findCategoryById(ENERGY_CATEGORY_ID)).build();
        Achievement transportAchievement = Achievement.builder().category(
                categoryService.findCategoryById(TRANSPORT_CATEGORY_ID)).build();

        foodAchievement.setUser(user);
        energyAchievement.setUser(user);
        transportAchievement.setUser(user);

        achievementRepository.save(foodAchievement);
        achievementRepository.save(energyAchievement);
        achievementRepository.save(transportAchievement);

        calculatorService.checkForBadgesForUser(user);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/user/register")
                        .buildAndExpand(user.getUsername())
                        .toUri())
                .build();
    }

    /**
     * Login by username and password.
     * @param form UserForm containing user credentials
     * @return Object containing username and token
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody final UserForm form) {

        try {
            String username = form.getUsername();
            String password = form.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password));
            String token = jwtTokenProvider.createToken(username, userService
                    .findByUsername(username).getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    // [PUT MAPPING]

    /**
     * Update current user properties in DB.
     * @param form User object containing the new properties
     * @param userDetails automatically gets sent along by JWT token header
     * @return no content
     */
    @PutMapping
    public ResponseEntity updateUser(@RequestBody final UserForm form,
                 @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());

        user.setPassword(form.getPassword());

        userService.updateUser(user);
        return noContent().build();
    }

    /**
     * Inviting an user to be your friend, adding an invite to their invites list.
     * @param username username of User that will be invited to be your friend.
     * @param userDetails automatically gets sent along by JWT token header
     * @return no content
     */
    @PutMapping("/invite/{username}")
    public ResponseEntity inviteFriend(@PathVariable("username") final String username,
                           @AuthenticationPrincipal final UserDetails userDetails) {

        User myself = userService.findByUsername(userDetails.getUsername());
        User friend = userService.findByUsername(username);

        if (myself.getFriends().contains(friend)) {
            throw new DataBaseException("Can not invite user which is already your friend.");
        }

        myself.addRequest(friend);

        userService.updateUser(myself);
        return noContent().build();
    }

    /**
     * Accepting an invite from an user and adding them to friends list and vice versa.
     * @param username username of User that needs to be added to friend list
     * @param userDetails automatically gets sent along by JWT token header
     * @return no content
     */
    @PutMapping("/accept/{username}")
    public ResponseEntity acceptFriend(@PathVariable("username") final String username,
                       @AuthenticationPrincipal final UserDetails userDetails) {

        User myself = userService.findByUsername(userDetails.getUsername());
        User friend = userService.findByUsername(username);

        myself.deleteInvitee(friend);

        myself.addFriend(friend);
        friend.addFriend(myself);

        userService.updateUser(myself);
        return ok(friend);
    }

    /**
     * Declining an invite from an user.
     * @param username username of User that needs to be declined
     * @param userDetails automatically gets sent along by JWT token header
     * @return no content
     */
    @PutMapping("/decline/{username}")
    public ResponseEntity declineFriend(@PathVariable("username") final String username,
                                       @AuthenticationPrincipal final UserDetails userDetails) {

        User myself = userService.findByUsername(userDetails.getUsername());
        User friend = userService.findByUsername(username);

        myself.deleteInvitee(friend);

        userService.updateUser(myself);
        return noContent().build();
    }

    // [DELETE MAPPING]

    /**
     * Delete an user in DB.
     * @param username username referring to user that needs to be deleted
     * @return no content
     */
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteUserByUsername(@PathVariable("username")
                                               final String username) {
        User user = userService.findByUsername(username);
        userService.deleteUser(user);

        return noContent().build();
    }

    /**
     * Deleting a friend from an users friends list.
     * @param username username of User that needs to be removed from friend list
     * @param userDetails automatically gets sent along by JWT token header
     * @return no content
     */
    @DeleteMapping("/friend/{username}")
    public ResponseEntity deleteFriend(@PathVariable("username") final String username,
                       @AuthenticationPrincipal final UserDetails userDetails) {

        User myself = userService.findByUsername(userDetails.getUsername());
        User friend = userService.findByUsername(username);

        myself.deleteFriend(friend);
        friend.deleteFriend(myself);

        userService.updateUser(myself);
        return noContent().build();
    }
}
