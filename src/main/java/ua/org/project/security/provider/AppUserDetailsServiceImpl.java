package ua.org.project.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.org.project.domain.AppUser;
import ua.org.project.domain.Role;
import ua.org.project.service.AppUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public class AppUserDetailsServiceImpl implements UserDetailsService {

    final Logger logger = LoggerFactory.getLogger(AppUserDetailsServiceImpl.class);

    @Autowired
    private AppUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user record for user name: {}", username);
        UserDetails userDetails = null;

        AppUser user = userService.findByUserName(username);

        if (user != null){
            String password = user.getPassword();
            Set<Role> roles = user.getRoles();
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            for (Role role: roles) {
                String roleName = role.getRoleId();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
                authorities.add(grantedAuthority);
            }
            userDetails = new User(username, password, authorities);
        } else {
            throw new UsernameNotFoundException("User "+ username + " not found");
        }

        return userDetails;
    }
}
