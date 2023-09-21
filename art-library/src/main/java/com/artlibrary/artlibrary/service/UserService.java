package com.artlibrary.artlibrary.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.artlibrary.artlibrary.requests.UserRequest;
import com.artlibrary.artlibrary.enums.ERole;
import com.artlibrary.artlibrary.interfaces.IRoleRepository;
import com.artlibrary.artlibrary.interfaces.IUserRepository;
import com.artlibrary.artlibrary.model.Role;
import com.artlibrary.artlibrary.model.User;
import com.artlibrary.artlibrary.requests.LoginRequest;
import com.artlibrary.artlibrary.responses.UserResponse;
import com.artlibrary.artlibrary.security.jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<UserResponse> login(LoginRequest loginRequest){
        Authentication authentication;

        authentication = authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtCookie(userDetails);
		ResponseCookie jwtCookie = jwtUtils.generateCookie(jwt);
		List<String> roles = userDetails.getAuthorities().stream()
			.map(item -> item.getAuthority())
            .collect(Collectors.toList());
        UserResponse response = new UserResponse(userDetails.getId(),userDetails.getUsername(), userDetails.getEmail(), roles, jwt);
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
			.body(response);
    }

    /**
     * Checks if user exists
     * 
     * @param user
     * @return boolean true if user exists
     */
    public boolean userExists(UserRequest user) {
        boolean existByUsername = userRepository.existsByUsername(user.getUserName().toLowerCase());
        boolean existByEmail = userRepository.existsByEmail(user.getUserName().toLowerCase());

        if (existByUsername || existByEmail) {
            return true;
        }
        return false;
    }

    /**
     * Register user
     * 
     * @param user
     * @throws RuntimeException
     */
    public void register(UserRequest user, Set<ERole> roles) throws RuntimeException {
        boolean userExists = this.userExists(user);
        Set<Role> userRoles = new HashSet<Role>();

        if (userExists) {
            throw new RuntimeException("Username or email already exists.");
        }

        if (roles.isEmpty()) {
            throw new RuntimeException("Choose a role for user.");
        }

        for (ERole roleName : roles) {
            Optional<Role> r = roleRepository.findByName(roleName);
            boolean found = r.isPresent();
            if (found) {
                userRoles.add(r.get());
            }
        }

        if (userRoles.size() == 0) {
            throw new RuntimeException("User roles not found.");
        }

        User newUser = new User(user.getUserName().toLowerCase(), user.getEmail().toLowerCase(), encoder.encode(user.getPassword()),
                user.getFirstName(), user.getLastName(), userRoles);

        this.userRepository.save(newUser);
    }
}