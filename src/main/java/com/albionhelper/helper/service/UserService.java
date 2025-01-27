package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.user.User;
import com.albionhelper.helper.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("입력받은 데이터 : "  + userName);
        User user = userRepository.findByUserName(userName);
        if(user == null) throw new UsernameNotFoundException("User not found : " + userName);

        System.out.println("DB로부터 받아온 pw : " + user.getPassword());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()))
                .accountLocked(!user.isEnabled())
                .build();
    }
}
