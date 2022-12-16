package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :newStatus WHERE o.id = :id")
    void updateStatus(@Param("id") int id, @Param("newStatus")Status newStatus);

    /**
     * Метод поиска заказа по последним символам.
     */
    @Query("FROM Order o WHERE o.number LIKE %:number ")
    List<Order> findByLastOrderSymbols(@Param("number") String number );
}
