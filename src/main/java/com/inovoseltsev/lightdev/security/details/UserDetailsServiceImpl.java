package com.inovoseltsev.lightdev.security.details;

import com.inovoseltsev.lightdev.domain.entity.AppUser;
import com.inovoseltsev.lightdev.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AppUser user = this.userRepository.findAppUserByLogin(login);
        if (user == null) {
            throw new IllegalArgumentException("User is not found!");
        }
        return new UserDetailsImpl(user);
    }
}
