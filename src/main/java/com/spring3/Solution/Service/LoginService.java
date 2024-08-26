package com.spring3.Solution.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring3.Solution.Model.UserModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoginService implements UserDetailsService {
      /*
   Implement the business logic for the LoginService  operations in this class
   Make sure to add required annotations
    */
	@Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	UserModel u = this.userService.getUserByEmail(email);
    	if (u==null) {
			throw new UsernameNotFoundException("User not found");
		}
        return new org.springframework.security.core.userdetails.User(email, u.getPassword(),buildUserAuthority(u.getRole().getRolename()));
    }

    private List<GrantedAuthority> buildUserAuthority(String userRole) {
    	List<GrantedAuthority> list = new ArrayList<>();
        GrantedAuthority auth = new SimpleGrantedAuthority(userRole);
        list.add(auth);
        return list;
    }

    private UserDetails buildUserForAuthentication(UserModel user, List<GrantedAuthority> authorities) {
        return null;
    }

}

