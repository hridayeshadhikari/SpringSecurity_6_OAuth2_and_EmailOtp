package org.webapp.oauth2security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.webapp.oauth2security.entity.User;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

}
