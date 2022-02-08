package com.dinamica.test.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dinamica.test.models.Role;
import com.dinamica.test.models.User;
import com.dinamica.test.repo.RoleRepository;
import com.dinamica.test.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}
	
	public void saveUser(User user) {
		encodePassword(user);
		userRepo.save(user);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		return userByEmail == null;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			System.out.println("User found in the database");
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> { 
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
	
}
