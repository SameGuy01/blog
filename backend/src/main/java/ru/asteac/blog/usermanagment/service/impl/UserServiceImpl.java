package ru.asteac.blog.usermanagment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.asteac.blog.domain.dto.request.PasswordChangeRequest;
import ru.asteac.blog.domain.dto.request.UserInfoEditRequest;
import ru.asteac.blog.domain.dto.response.MessageResponse;
import ru.asteac.blog.domain.mapper.UserMapper;
import ru.asteac.blog.domain.model.entity.Role;
import ru.asteac.blog.domain.model.entity.User;
import ru.asteac.blog.domain.model.enums.ERole;
import ru.asteac.blog.exception.RoleNotFoundException;
import ru.asteac.blog.exception.UserNotFoundException;
import ru.asteac.blog.security.jwt.JwtUtils;
import ru.asteac.blog.security.service.UserDetailsImpl;
import ru.asteac.blog.domain.dto.request.LogInRequest;
import ru.asteac.blog.domain.dto.request.SignUpRequest;
import ru.asteac.blog.domain.dto.response.JwtResponse;
import ru.asteac.blog.usermanagment.repository.RoleRepository;
import ru.asteac.blog.usermanagment.repository.UserRepository;
import ru.asteac.blog.usermanagment.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final static String REGISTRATION_SUCCESSFUL = "The user registered successfully.";
    private final static String UPDATE_SUCCESSFUL = "The user was updated successfully.";
    private final static String SUBSCRIPTION_SUCCESSFUL = "User subscribed successfully";
    private static final String UNSUBSCRIPTION_SUCCESSFUL = "User unsubscribed successfully.";

    private final static String UPDATE_ERROR = "The user can only update their own data.";
    private final static String USER_BAD_REQUEST = "Invalid user.";
    private final static String UNSUBSCRIBE_BAD_REQUEST = "User is not subscribed to this channel.";

    private final static String USERNAME_INVALID = "The username is already taken.";
    private final static String EMAIL_INVALID = "The email is already taken.";

    private final static String PASSWORD_CHANGE_SUCCESSFUL = "The user password was updated successfully.";
    private final static String PASSWORD_NEW_MATCH_INVALID = "New passwords doesn't match";
    private final static String PASSWORD_OLD_EQUALS_INVALID = "Wrong old password";
    private final static String PASSWORD_PREVIOUS_EQUALS_INVALID = "New password can't be equal to old one";

    public UserServiceImpl(JwtUtils jwtUtils, UserMapper userMapper, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LogInRequest logInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInRequest.getUsername(), logInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = JwtResponse.builder()
                .id(String.valueOf(userDetails.getId()))
                .token(jwtToken)
                .email(userDetails.getEmail())
                .username(userDetails.getUsername())
                .roles(roles)
                .isActive(userDetails.isEnabled())
                .build();

        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(USERNAME_INVALID));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(EMAIL_INVALID));
        }

        User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(),
                signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = getByRole(ERole.ROLE_USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = getByRole(ERole.ROLE_ADMIN);
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = getByRole(ERole.ROLE_MODERATOR);
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = getByRole(ERole.ROLE_USER);
                        roles.add(userRole);
                }
            });
        }

        user.setRegisteredAt(LocalDateTime.now());
        user.setIsActive(true);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(REGISTRATION_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> updateUser(Long userId, String username, UserInfoEditRequest userInfoEditRequest) {

        User userFromDb = userRepository.getById(userId)
                .orElseThrow(UserNotFoundException::new);

        if(!userFromDb.getUsername().equals(username) || !userFromDb.getId().equals(Long.valueOf(userInfoEditRequest.getId()))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse(UPDATE_ERROR));
        }
        User updateUser = userMapper.toEntity(userInfoEditRequest);

        if(!updateUser.getUsername().equals(userFromDb.getUsername())){
            if(userRepository.existsByUsername(updateUser.getUsername())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(USERNAME_INVALID));
            }
        }

        if(!updateUser.getEmail().equals(userFromDb.getEmail())){
            if(userRepository.existsByEmail(updateUser.getEmail())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(EMAIL_INVALID));
            }
        }

        BeanUtils.copyProperties(updateUser,userFromDb, "id", "postList", "password", "registeredAt", "roles", "isActive");
        userRepository.save(userFromDb);

        return ResponseEntity.ok().body(new MessageResponse(UPDATE_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> passwordChange(Long userId, String username, PasswordChangeRequest passwordChangeRequest) {
        User userFromDb = userRepository.getById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), userFromDb.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(PASSWORD_OLD_EQUALS_INVALID));

        } else if (!passwordChangeRequest.getNewPassword()
                .equals(passwordChangeRequest.getMatchingPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(PASSWORD_NEW_MATCH_INVALID));

        } else if (passwordEncoder.matches(passwordChangeRequest.getNewPassword(), userFromDb.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(PASSWORD_PREVIOUS_EQUALS_INVALID));

        } else {
            userFromDb.setPassword(passwordEncoder
                    .encode(passwordChangeRequest.getNewPassword()));
            userRepository.save(userFromDb);

            return ResponseEntity
                    .ok()
                    .body(new MessageResponse(PASSWORD_CHANGE_SUCCESSFUL));
        }
    }



    @Override
    public ResponseEntity<?> getById(Long id) {
        User user = userRepository.getById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok().body(userMapper.toDto(user));
    }

    @Override
    public ResponseEntity<?> subscribe(Long userId, Long channelId, String username) {
        User user = userRepository.getById(userId)
                .orElseThrow(UserNotFoundException::new);

        if(!user.getUsername().equals(username)){
            return ResponseEntity.badRequest().body(new MessageResponse(USER_BAD_REQUEST));
        }

        User channel = userRepository.getById(channelId)
                .orElseThrow(UserNotFoundException::new);

        user.subscribe(channel);
        channel.addSubscriber(user);
        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageResponse(SUBSCRIPTION_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> unsubscribe(Long userId, Long channelId, String username) {
        User user = getUserById(userId);

        if(!user.getUsername().equals(username)){
            return ResponseEntity.badRequest().body(new MessageResponse(USER_BAD_REQUEST));
        }

        User channel = getUserById(channelId);
        if(!user.getSubscriptions().contains(channel)){
            return ResponseEntity.badRequest().body(new MessageResponse(UNSUBSCRIBE_BAD_REQUEST));
        }

        user.unsubscribe(channel);
        channel.removeSubscriber(user);
        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageResponse(UNSUBSCRIPTION_SUCCESSFUL));
    }

    private Role getByRole(ERole erole){
        return roleRepository.getByRole(erole)
                .orElseThrow(RoleNotFoundException::new);
    }

    private User getUserById(Long id){
        return userRepository.getById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
