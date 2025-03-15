package com.pada.safe_sphere.repository;

import com.pada.safe_sphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByNationalId(String nationalId);


}
