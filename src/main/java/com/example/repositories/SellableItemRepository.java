package com.example.repositories;

import com.example.entities.SellableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellableItemRepository extends JpaRepository<SellableItem, Long> {
    @Query("SELECT si FROM SellableItem si WHERE si.user.username != :username AND si.sold = false")
    List<SellableItem> findAllExceptCurrentUserItems(@Param("username") String username);
}
