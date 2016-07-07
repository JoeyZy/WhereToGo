package com.luxoft.wheretogo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    //get user from the database, via Hibernate
    @Autowired
    @Qualifier("userService")
    private UsersService userDao;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String userEmail)
            throws UsernameNotFoundException {

        com.luxoft.wheretogo.models.User user = userDao.findByEmail(userEmail);
        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getRole());

        return buildUserForAuthentication(user, authorities);

    }

    // Converts com.luxoft.wheretogo.models.User user to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(com.luxoft.wheretogo.models.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getFirstName() + " " + user.getLastName(), user.getPassword(),
                user.isActive(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String userRole) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        setAuths.add(new SimpleGrantedAuthority(userRole));


        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}