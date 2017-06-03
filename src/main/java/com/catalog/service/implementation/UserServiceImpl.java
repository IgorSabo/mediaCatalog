package com.catalog.service.implementation;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.catalog.business.repository.UserRepository;
//import com.catalog.business.service.PasswordTokenService;
import com.catalog.service.UserService;
import com.catalog.model.entities.User;
import com.catalog.model.entities.UserType;
//import rs.gecko.app.presentation.forms.UserCreateForm;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	PasswordTokenService tokenService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see rs.gecko.app.business.service.UserService#findById(int)
	 */
	@Override
	public Optional<User> findById(int id) {
		LOGGER.info("Getting user by username={}.", id);
		return userRepository.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rs.gecko.app.business.service.UserService#findByUsername(java.lang.String)
	 */
	@Override
	public Optional<User> findByUsername(String username) {
		LOGGER.info("Getting user by username={}.", username);
		return userRepository.findByUsername(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * rs.gecko.app.business.service.UserService#findByEmail(java.lang.String)
	 */
	@Override
	public Optional<User> findByEmail(String email) {
		LOGGER.info("Getting user by email={}.", email.replaceFirst("@.*", "@***"));
		return userRepository.findByEmail(email);
	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see rs.gecko.app.business.service.UserService#createUser(rs.gecko.app.
//	 * presentation.forms.UserCreateForm)
//	 */
//	@Override
//	public User createUser(UserCreateForm userCreateForm) {
//		LOGGER.info("Creating user[{}].", userCreateForm.getUsername());
//		User user = new User();
//		user.setUsername(userCreateForm.getUsername());
//		user.setPassword(new BCryptPasswordEncoder().encode(userCreateForm.getPassword()));
//		user.setEmail(userCreateForm.getEmail());
//		user.setUserType(UserType.User);
//		user.setActive(false);
//		user.setRegistrationDate(new Date());
//		return userRepository.save(user);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see rs.gecko.app.business.service.UserService#getUserForToken(java.lang.
//	 * String)
//	 */
//	@Override
//	public Optional<User> getUserForToken(String token) {
//		LOGGER.info("Getting user by token={}.", token);
//		return tokenService.getUserByToken(token);
//	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see
//	 * rs.gecko.app.business.service.UserService#changeUserPassword(rs.gecko.app
//	 * .model.entities.User, java.lang.String)
//	 */
//	@Override
//	public void changeUserPassword(User user, String password) {
//		LOGGER.info("Changing password for user={}.", user.getUsername());
//		user.setPassword(passwordEncoder.encode(password));
//		userRepository.save(user);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see
//	 * rs.gecko.app.business.service.UserService#saveChanges(rs.gecko.app.model.
//	 * entities.User)
//	 */
//	@Override
//	public void saveChanges(User user) {
//		LOGGER.info("Updating/Saving user={}.", user.getUsername());
//		userRepository.save(user);
//	}

	/* (non-Javadoc)
	 * @see rs.gecko.app.business.service.UserService#findAllUsers()
	 */
	@Override
	public Iterable<User> findAllUsers() {
		LOGGER.info("Getting all users");
		return userRepository.findAll();
	}

}
