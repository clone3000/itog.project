package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByTitle(String title);
    // Поиск по части наименования товара в не зависимости от регистра
    List<Product> findByTitleContainingIgnoreCase(String name);

    // Поиск по части наименования товара и фильтрация по диапазону цен
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1') and (price >= ?2 and price <= ?3))", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThan(String title, float ot, float Do);

    // Поиск по части наименования товара и фильтрация по диапазону цен, сортировка по возрастанию
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price)", nativeQuery = true)
    List<Product> findByTitleOrderByPrice(String title, float ot, float Do);

    // Поиск по части наименования товара и фильтрация по диапазону цен, сортировка по убыванию
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price desc)", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title, float ot, float Do);


    // Поиск по части наименования товара и фильтрация по диапазону цен, сортировка по возрастанию, фильтрация по категории
    @Query(value = "select * from product where category_id=?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPrice(String title, float ot, float Do, int category);


    /**
     * Метод по поиску вхождений при любом наборе параметров. Присутствие параметра (is null) исключает его из
     * поискового запроса. Присутствие параметра соответсвенно включает его в запрос.
     * Т.е указав цену только ДО без цены ОТ, найдем все дешевле порога до 0, аналогично и наоборот.
     * Имя ищется не по полному совпадению, а частичному вхождению игнорируя регистр в записях
     *
     * @param title название товара который ищем
     * @param category id искомой категории
     * @param ot минимальный ценовой порог
     * @param Do максимальный ценовой порог
     */
    @Query(value ="" +
        "FROM Product p WHERE " +
        "(:title is null or lower(p.title) LIKE %:title%) and " +
        "(:category is null or p.category.id = :category) and" +
        "(:ot is null or p.price >= :ot ) and " +
        "(:do is null or p.price <= :do) order by p.price ASC")
    List<Product> findAsc(
        @Param("title") @Nullable String title,
        @Param("category") @Nullable Integer category,
        @Param("ot") @Nullable Float ot,
        @Param("do") @Nullable Float Do);

    @Query(value ="" +
        "FROM Product p WHERE " +
        "(:title is null or lower(p.title) LIKE %:title%) and " +
        "(:category is null or p.category.id = :category) and" +
        "(:ot is null or p.price >= :ot ) and " +
        "(:do is null or p.price <= :do) order by p.price DESC")
    List<Product> findDesc(
        @Param("title") @Nullable String title,
        @Param("category") @Nullable Integer category,
        @Param("ot") @Nullable Float ot,
        @Param("do") @Nullable Float Do);

}
