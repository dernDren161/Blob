package com.writings.service;

import com.writings.model.SiteUser;
import com.writings.model.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// UserDetailsService is a custom Spring class used to retrieve user-desired/specific data. It arranges data as desired such that it makes data available for the future authentication process.
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(SiteUser user){
        user.setRole("ROLE_USER");
        //user.setPassword(passwordEncoder.encode(user.getPassword())); --- this done inside the SiteUser Entity.
        userDao.save(user);
    }


    @Override  // loadUserByUsername is a method of UserDetailsService interface. UserDetailsService basically is a DAO.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SiteUser user = userDao.findByEmail(email);

        if(user == null){
            return null;
        }

        // Grant Authority if "username" is found.
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());

        String password = user.getPassword();
        return new User(email,password,auth);
    }
}
