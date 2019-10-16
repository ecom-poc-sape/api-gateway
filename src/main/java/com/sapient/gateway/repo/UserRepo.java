package com.sapient.gateway.repo;

import com.sapient.gateway.model.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserDetails, Long> {
    UserDetails findByUsername(String username);
}
