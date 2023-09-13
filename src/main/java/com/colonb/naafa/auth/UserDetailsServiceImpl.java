package com.colonb.naafa.auth;

import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> data = userMapper.findByUsername(username);
        if (data.isEmpty()) {
            throw new UsernameNotFoundException("이메일 " + username + " 을 찾을수 없음");
        }
        return new UserDetailsImpl(data.get());
    }
}
