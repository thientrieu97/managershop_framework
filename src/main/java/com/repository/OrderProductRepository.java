package com.repository;

import com.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<ProductOrder, Integer>, BaseRepository<ProductOrder, Integer>, InsertUpdateRepository<ProductOrder> {
    @Query(value = "select * from product_orders po where po.order_id = ?1 and po.delete_at IS NULL" , nativeQuery = true)
    List<ProductOrder> findByOrderId(Integer id);

}