package com.catalog.business.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.catalog.model.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findByUsername(String userName);

	public Optional<User> findByEmail(String email);

	public Optional<User> findById(int id);
}
