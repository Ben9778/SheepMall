package com.blackfiresoft.sheepmall.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    Users findByEmail(String email);

    Users findByUsernameAndPassword(String username, String password);

    Users findByEmailAndPassword(String email, String password);

    @Query(value = "select u from Users u join fetch u.receiptList where u.id = ?1")
    Optional<Users> findByIdFetchReceipt(Long id);

    @Query(value = "select u from Users u join fetch u.cartList where u.id = ?1")
    Optional<Users> findByIdFetchCart(Long id);

    @Query(value = "select u.* from user_tb u where date(u.created_at)=curdate()", nativeQuery = true)
    List<Users> findByToday();

    @Query(value = "select u.* from user_tb u where curdate() - date(u.created_at)=1",nativeQuery = true)
    List<Users> findByYesterday();

}
