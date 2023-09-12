package com.colonb.naafa.user;

import com.colonb.naafa.user.entity.User;
import com.colonb.naafa.user.enums.SocialProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Optional;

@Mapper
public interface UserMapper {
    int insertUserRole(HashMap<String,Object> param);
    int insertUserMarketing(HashMap<String,Object> param);
    int insertUserCreatedAt(HashMap<String,Object> param);
    int insertDefaultUser(HashMap<String,Object> param);
    int insertSocialUser(HashMap<String,Object> param);
    Optional<User> findByUsername(String username);
    Optional<User> findBySeq(long seq);
    Optional<User> findByProviderAndSocialId(SocialProvider provider, String socialId);
    boolean existByUsername(String username);
}
