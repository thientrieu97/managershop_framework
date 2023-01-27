package com.repository;

import com.domain.Order;
import com.domain.Product;
import com.model.dto.OrderDTO;
import com.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, BaseRepository<Order, Integer>, InsertUpdateRepository<Order> {
    @Query("SELECT NEW com.model.dto.OrderDTO(o.id,o.name,o.totalPrice) " +
            "FROM Order o " +
            "WHERE (:name IS NULL OR (LOWER(o.name) LIKE %:name%)) "+
            "AND o.deletedAt IS NULL")
    Page<OrderDTO> findAllOrder(@Param("name") String name, Pageable pageable);

}
