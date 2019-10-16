package com.sapient.gateway.service.impl;

import com.sapient.gateway.repo.UserRepo;
import com.sapient.gateway.payload.UserDto;
import com.sapient.gateway.model.UserDetails;
import com.sapient.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userRepo.findByUsername(username);
		if(userDetails == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(userDetails.getUsername(), userDetails.getPassword(), getAuthority(userDetails));
	}

	private Set<SimpleGrantedAuthority> getAuthority(UserDetails userDetails) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		userDetails.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	public List<UserDetails> findAll() {
		List<UserDetails> list = new ArrayList<>();
		userRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userRepo.deleteById(id);
	}

	@Override
	public UserDetails findOne(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public UserDetails findById(Long id) {
		return userRepo.findById(id).get();
	}

	@Override
    public UserDetails save(UserDto user) {
	    UserDetails newUserDetails = new UserDetails();
	    newUserDetails.setUsername(user.getUsername());
	    newUserDetails.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepo.save(newUserDetails);
    }
}
