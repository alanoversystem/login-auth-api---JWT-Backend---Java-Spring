package com.oversystem.login_auth_api.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.oversystem.login_auth_api.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    

    java.util.Optional<User> findByEmail(String email);


}
