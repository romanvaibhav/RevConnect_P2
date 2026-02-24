package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
//    List<Product> findByBusinessProfile_Id(Long businessProfileId);
List<Product> findByBusinessProfile_UserId(Long userId);
}
