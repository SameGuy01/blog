package ru.andreev.blog.usermanagment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.blog.domain.dto.request.UserEditRequest;
import ru.andreev.blog.domain.dto.response.MessageResponse;
import ru.andreev.blog.domain.mapper.UserMapper;
import ru.andreev.blog.domain.model.entity.Role;
import ru.andreev.blog.domain.model.entity.User;
import ru.andreev.blog.domain.model.enums.ERole;
import ru.andreev.blog.postmanagment.exception.RoleNotFoundException;
import ru.andreev.blog.security.jwt.JwtUtils;
import ru.andreev.blog.security.service.UserDetailsImpl;
import ru.andreev.blog.domain.dto.request.LogInRequest;
import ru.andreev.blog.domain.dto.request.SignUpRequest;
import ru.andreev.blog.domain.dto.response.JwtResponse;
import ru.andreev.blog.usermanagment.repository.RoleRepository;
import ru.andreev.blog.usermanagment.repository.UserRepository;
import ru.andreev.blog.usermanagment.service.UserService;

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

    private final static String UPDATE_ERROR = "The user can only update their own data.";

    private final static String USERNAME_INVALID = "The username is already taken.";
    private final static String EMAIL_INVALID = "The email is already taken.";

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
        UserDetailsImpl      userDetails = (UserDetailsImpl) authentication.getPrincipal();
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
    public ResponseEntity<?> updateUser(Long userId, String username, UserEditRequest userEditRequest) {

        User userFromDb = userRepository.getById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(userId)));

        if(!userFromDb.getUsername().equals(username) || !userFromDb.getId().equals(Long.valueOf(userEditRequest.getId()))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse(UPDATE_ERROR));
        }
        User updateUser = userMapper.toDto(userEditRequest);

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

    private Role getByRole(ERole erole){
        return roleRepository.findByRole(erole)
                .orElseThrow(() -> new RoleNotFoundException(erole.name()));
    }

}
