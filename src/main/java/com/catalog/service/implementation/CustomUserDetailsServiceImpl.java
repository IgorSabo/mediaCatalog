package com.catalog.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalog.service.UserService;
import com.catalog.model.entities.User;

@Service("customUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	List<GrantedAuthority> authorities;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userService.findByUsername(username);
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException("User not found");
		}

		User user = optional.get();
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isActive(), true, true, true, getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {

		authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));

		System.out.print("authorities :" + authorities);
		return authorities;
	}
}
