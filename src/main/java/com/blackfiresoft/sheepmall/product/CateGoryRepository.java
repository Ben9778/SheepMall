package com.blackfiresoft.sheepmall.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateGoryRepository extends JpaRepository<CateGory, Long> {

}
