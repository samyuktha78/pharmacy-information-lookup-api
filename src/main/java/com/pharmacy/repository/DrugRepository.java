package com.pharmacy.repository;

import com.pharmacy.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {

  Optional<Drug> findByNdcCode(String ndcCode);

  List<Drug> findByNameContainingIgnoreCase(String name);

  List<Drug> findByGenericNameContainingIgnoreCase(String genericName);

  List<Drug> findByManufacturerContainingIgnoreCase(String manufacturer);

  List<Drug> findByStatus(Drug.DrugStatus status);

  List<Drug> findByRequiresPrescription(boolean requiresPrescription);

  @Query("SELECT d FROM Drug d WHERE d.price BETWEEN :minPrice AND :maxPrice")
  List<Drug> findByPriceRange(@Param("minPrice") java.math.BigDecimal minPrice,
                               @Param("maxPrice") java.math.BigDecimal maxPrice);
}
