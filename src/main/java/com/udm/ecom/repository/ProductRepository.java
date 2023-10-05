package com.udm.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udm.ecom.model.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long>{

}
