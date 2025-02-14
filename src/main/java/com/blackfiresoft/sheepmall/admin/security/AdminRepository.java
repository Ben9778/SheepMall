package com.blackfiresoft.sheepmall.admin.security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Long> {

    Optional<Administrator> findByUsernameAndPassword(String username, String password);

    Optional<Administrator> findByEmailAndPassword(String email,String password);

    Optional<Administrator> findByEmail(String email);

    @Modifying
    @Query(value = "update Administrator A set A.password = ?1 where A.email = ?2")
    int setAdminPasswordFor(String password,String email);
}
