package com.inovoseltsev.lightdev.service.impl;


import com.inovoseltsev.lightdev.domain.entity.AppUser;
import com.inovoseltsev.lightdev.repository.AppUserRepository;
import com.inovoseltsev.lightdev.service.AppUserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void create(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(AppUser user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(AppUser user) {
        userRepository.delete(user);
    }

    @Override
    public AppUser findById(Long id) {
        Optional<AppUser> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findByLogin(String login) {
        return userRepository.findAppUserByLogin(login);
    }

    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findAppUserByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppUser> findAll() {
        return (List<AppUser>) userRepository.findAll();
    }
}
