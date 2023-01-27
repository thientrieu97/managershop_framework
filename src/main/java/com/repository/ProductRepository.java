package com.repository;


import com.domain.Product;
import com.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,BaseRepository<Product,Integer>,InsertUpdateRepository<Product> {
    @Query("SELECT NEW com.model.dto.ProductDTO(p.id,p.name,p.price, p.quantity, p.weight, p.address) " +
            "FROM Product p " +
            "WHERE (:name IS NULL OR (LOWER(p.name) LIKE %:name%)) "+
            "AND p.deletedAt IS NULL")
    Page<ProductDTO> getAllProduct(@Param("name") String name , Pageable pageable);

}
