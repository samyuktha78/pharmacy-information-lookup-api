package com.pharmacy.controller;

import com.pharmacy.model.Drug;
import com.pharmacy.service.DrugService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/drugs")
public class DrugController {

  private final DrugService drugService;

  public DrugController(DrugService drugService) {
    this.drugService = drugService;
  }

  @GetMapping
  public ResponseEntity<List<Drug>> getAllDrugs() {
    return ResponseEntity.ok(drugService.getAllDrugs());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Drug> getDrugById(@PathVariable Long id) {
    return ResponseEntity.ok(drugService.getDrugById(id));
  }

  @GetMapping("/ndc/{ndcCode}")
  public ResponseEntity<Drug> getDrugByNdcCode(@PathVariable String ndcCode) {
    return ResponseEntity.ok(drugService.getDrugByNdcCode(ndcCode));
  }

  @GetMapping("/search")
  public ResponseEntity<List<Drug>> searchByName(@RequestParam String name) {
    return ResponseEntity.ok(drugService.searchByName(name));
  }

  @GetMapping("/search/generic")
  public ResponseEntity<List<Drug>> searchByGenericName(@RequestParam String genericName) {
    return ResponseEntity.ok(drugService.searchByGenericName(genericName));
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<List<Drug>> getByStatus(@PathVariable Drug.DrugStatus status) {
    return ResponseEntity.ok(drugService.getByStatus(status));
  }

  @GetMapping("/price-range")
  public ResponseEntity<List<Drug>> getByPriceRange(@RequestParam BigDecimal minPrice,
                                                     @RequestParam BigDecimal maxPrice) {
    return ResponseEntity.ok(drugService.getByPriceRange(minPrice, maxPrice));
  }

  @PostMapping
  public ResponseEntity<Drug> createDrug(@Valid @RequestBody Drug drug) {
    Drug created = drugService.createDrug(drug);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Drug> updateDrug(@PathVariable Long id, @Valid @RequestBody Drug drug) {
    return ResponseEntity.ok(drugService.updateDrug(id, drug));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
    drugService.deleteDrug(id);
    return ResponseEntity.noContent().build();
  }
}
