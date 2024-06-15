package com.example.function_module.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.function_module.dto.LoginDto;
import com.example.function_module.dto.UserRegisterDto;
import com.example.function_module.entity.Role;
import com.example.function_module.entity.User;
import com.example.function_module.repository.RoleRepository;
import com.example.function_module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * This method loads a user by their username.
     * If the user is not found, it throws a UsernameNotFoundException.
     *
     * @param username The username of the user to load
     * @return The UserDetails object representing the loaded user
     * @throws UsernameNotFoundException If the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by their username
        User user = userRepo.findByUsername(username);

        // If the user is not found, throw an exception
        if (user == null){
            throw new UsernameNotFoundException("Invalid");
        }

        // Create a new UserDetails object with the user's username, password, and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }


    public List<User> allUsers() {
        return userRepo.findAll();
    }


    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }


    /**
     * Saves a user registration DTO into the database.
     *
     * @param userRegDto The user registration DTO containing the user's information.
     */
    @Override
    public void save(UserRegisterDto userRegDto) {
        // Create a new User object with the provided information
        User user = new User();
        user.setNickname(userRegDto.getNickname());
        user.setUsername(userRegDto.getUsername());

        // Encode the password using the password encoder
        String encodedPassword = passwordEncoder.encode(userRegDto.getPassword());
        user.setPassword(encodedPassword);

        // Find the role with the name "ROLE_USER"
        Role role = roleRepo.findByName("ROLE_USER");

        // Set the user's roles to a list containing the found role
        user.setRoles(Arrays.asList(role));

        // Save the user object to the database
        userRepo.save(user);
    }


    /**
     * This method maps a collection of Role objects to a collection of GrantedAuthority objects.
     * It uses the role names to create SimpleGrantedAuthority objects.
     *
     * @param roles A collection of Role objects.
     * @return A collection of GrantedAuthority objects.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        // Use the stream API to map each role to a SimpleGrantedAuthority object.
        // The role name is used as the authority name.
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                // Collect the mapped authorities into a list.
                .collect(Collectors.toList());
    }



    public boolean isUserLoggedIn(){
        return SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()
                instanceof UserDetails;
    }

    public User authenticate(LoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return findByUsername(input.getUsername());
    }



}