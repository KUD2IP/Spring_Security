package com.example.function_module.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.function_module.dto.UserRegisterDto;
import com.example.function_module.entity.Role;
import com.example.function_module.entity.User;
import com.example.function_module.repository.RoleRepository;
import com.example.function_module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepo,
                           RoleRepository roleRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);
        if (user == null){
            throw new UsernameNotFoundException("Invalid");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    public List<User> allUsers() {
        return userRepo.findAll();
    }

    public User findByLogin(String login){
        return userRepo.findByLogin(login);
    }

    @Override
    public void save(UserRegisterDto userRegDto) {
        User user = new User();
        user.setNickname(userRegDto.getNickname());
        user.setLogin(userRegDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRegDto.getPassword()));
        Role role= roleRepo.findByName("ROLE_USER");
        user.setRoles(Arrays.asList(role));
        userRepo.save(user);
    }
    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection < Role > roles) {
        return roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}