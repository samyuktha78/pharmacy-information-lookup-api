package com.pharmacy.service;

import com.pharmacy.exception.DrugNotFoundException;
import com.pharmacy.model.Drug;
import com.pharmacy.repository.DrugRepository;
import com.pharmacy.validation.DrugValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrugServiceTest {

  @Mock
  private DrugRepository drugRepository;

  @Mock
  private DrugValidator drugValidator;

  @InjectMocks
  private DrugService drugService;

  private Drug sampleDrug;

  @BeforeEach
  void setUp() {
    sampleDrug = new Drug("NDC500", "Ibuprofen", "Ibuprofen", "GenericCo",
        "tablet", "200mg", new BigDecimal("5.00"), Drug.DrugStatus.AVAILABLE, false);
    sampleDrug.setId(1L);
  }

  @Test
  void getDrugById_found_returnsDrug() {
    when(drugRepository.findById(1L)).thenReturn(Optional.of(sampleDrug));
    Drug result = drugService.getDrugById(1L);
    assertEquals("Ibuprofen", result.getName());
  }

  @Test
  void getDrugById_notFound_throwsException() {
    when(drugRepository.findById(99L)).thenReturn(Optional.empty());
    assertThrows(DrugNotFoundException.class, () -> drugService.getDrugById(99L));
  }

  @Test
  void getDrugByNdcCode_found_returnsDrug() {
    when(drugRepository.findByNdcCode("NDC500")).thenReturn(Optional.of(sampleDrug));
    Drug result = drugService.getDrugByNdcCode("NDC500");
    assertEquals("NDC500", result.getNdcCode());
  }

  @Test
  void getAllDrugs_returnsList() {
    when(drugRepository.findAll()).thenReturn(List.of(sampleDrug));
    List<Drug> result = drugService.getAllDrugs();
    assertEquals(1, result.size());
  }

  @Test
  void createDrug_valid_savesAndReturns() {
    when(drugValidator.validate(any(Drug.class))).thenReturn(List.of());
    when(drugRepository.save(any(Drug.class))).thenReturn(sampleDrug);

    Drug result = drugService.createDrug(sampleDrug);

    assertEquals("Ibuprofen", result.getName());
    verify(drugRepository, times(1)).save(sampleDrug);
  }

  @Test
  void createDrug_invalid_throwsException() {
    when(drugValidator.validate(any(Drug.class))).thenReturn(List.of("Drug name is required"));

    assertThrows(IllegalArgumentException.class, () -> drugService.createDrug(sampleDrug));
    verify(drugRepository, never()).save(any());
  }

  @Test
  void updateDrug_valid_updatesFields() {
    Drug updated = new Drug("NDC501", "Naproxen", "Naproxen", "OtherCo",
        "tablet", "250mg", new BigDecimal("8.00"), Drug.DrugStatus.AVAILABLE, false);

    when(drugRepository.findById(1L)).thenReturn(Optional.of(sampleDrug));
    when(drugValidator.validate(any(Drug.class))).thenReturn(List.of());
    when(drugRepository.save(any(Drug.class))).thenAnswer(inv -> inv.getArgument(0));

    Drug result = drugService.updateDrug(1L, updated);

    assertEquals("Naproxen", result.getName());
    assertEquals("NDC501", result.getNdcCode());
  }

  @Test
  void deleteDrug_valid_callsRepositoryDelete() {
    when(drugRepository.findById(1L)).thenReturn(Optional.of(sampleDrug));

    drugService.deleteDrug(1L);

    verify(drugRepository, times(1)).delete(sampleDrug);
  }

  @Test
  void deleteDrug_notFound_throwsException() {
    when(drugRepository.findById(99L)).thenReturn(Optional.empty());
    assertThrows(DrugNotFoundException.class, () -> drugService.deleteDrug(99L));
  }
}
