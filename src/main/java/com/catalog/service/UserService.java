package com.catalog.service;

import java.util.Optional;

import com.catalog.model.entities.User;
//import rs.gecko.app.presentation.forms.UserCreateForm;

public interface UserService {

	/**
	 * @param id <code>int</code> - this is id from db table User.
	 * @return a {@link Optional <code>Optional &ltUser&gt</code>} containing the user if such user exists.
	 */
	public Optional<User> findById(int id);

	/**
	 * @param username <code>String</code> - this is username from db table User.
	 * @return a {@link Optional <code>Optional &ltUser&gt</code>} containing the user if such user exists.
	 */
	public Optional<User> findByUsername(String username);

	/**
	 * @param email <code>String</code> - this is email from db table User.
	 * @return a {@link Optional <code>Optional &ltUser&gt</code>} containing the user if such user exists.
	 */
	public Optional<User> findByEmail(String email);

	
//	/** Persists/merges given user.
//	 *
//	 * @param user a {@link rs.gecko.app.model.entities.User <code>User</code>} to be updated/saved.
//	 */
//	public void saveChanges(User user);
//
//	/** Creates new <code>User</code> object and saves the record in db.
//	 * @param userCreateForm {@link rs.gecko.app.presentation.forms.UserCreateForm <code>UserCreateForm</code>} - validated transfer object.
//	 * @return
//	 */
//	public User createUser(UserCreateForm userCreateForm);
//
//	/**
//	 * @param token <code>String</code> - this is token from db.
//	 * @return a {@link Optional <code>Optional &ltUser&gt</code>} containing the user if such user exists.
//	 */
//	public Optional<User> getUserForToken(String token);
//
//	/** Changes password for given user.
//	 * @param user
//	 * @param password
//	 */
//	public void changeUserPassword(User user, String password);

	public Iterable<User> findAllUsers();

}
