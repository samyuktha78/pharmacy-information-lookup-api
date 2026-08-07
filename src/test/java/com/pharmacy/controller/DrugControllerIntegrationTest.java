package com.pharmacy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacy.model.Drug;
import com.pharmacy.service.DrugService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DrugControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private DrugService drugService;

  private Drug sampleDrug() {
    Drug drug = new Drug("NDC900", "Metformin", "Metformin", "HealthCorp",
        "tablet", "850mg", new BigDecimal("12.50"), Drug.DrugStatus.AVAILABLE, true);
    drug.setId(1L);
    return drug;
  }

  @Test
  void getDrugById_found_returnsDrug() throws Exception {
    when(drugService.getDrugById(1L)).thenReturn(sampleDrug());

    mockMvc.perform(get("/api/drugs/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Metformin"));
  }

  @Test
  void getAllDrugs_returnsList() throws Exception {
    when(drugService.getAllDrugs()).thenReturn(List.of(sampleDrug()));

    mockMvc.perform(get("/api/drugs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].ndcCode").value("NDC900"));
  }

  @Test
  void createDrug_validPayload_returnsCreated() throws Exception {
    Drug drug = sampleDrug();
    when(drugService.createDrug(any())).thenReturn(drug);

    mockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(drug)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Metformin"));
  }

  @Test
  void updateDrug_validPayload_returnsUpdated() throws Exception {
    Drug drug = sampleDrug();
    when(drugService.updateDrug(any(), any())).thenReturn(drug);

    mockMvc.perform(put("/api/drugs/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(drug)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Metformin"));
  }

  @Test
  void deleteDrug_valid_returnsNoContent() throws Exception {
    mockMvc.perform(delete("/api/drugs/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void getDrugById_notFound_returns404() throws Exception {
    when(drugService.getDrugById(999L))
        .thenThrow(new com.pharmacy.exception.DrugNotFoundException("Drug not found: 999"));

    mockMvc.perform(get("/api/drugs/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void searchByName_returnsMatchingDrugs() throws Exception {
    when(drugService.searchByName("Metf")).thenReturn(List.of(sampleDrug()));

    mockMvc.perform(get("/api/drugs/search").param("name", "Metf"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Metformin"));
  }
}
