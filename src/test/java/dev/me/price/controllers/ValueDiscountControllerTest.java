package dev.me.price.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.me.price.entities.ValueDiscount;
import dev.me.price.models.NewValueDiscount;
import dev.me.price.services.ValueDiscountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ValueDiscountController.class)
class ValueDiscountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ValueDiscountService mockService;

    @Test
    void findAll_given1DiscountPolicy_ReturnsListOfValueDiscounts() throws Exception {
        // given
        var expectedId = UUID.randomUUID();
        var expectedProductId = UUID.randomUUID();
        var mockEntities = List.of(
                new ValueDiscount(expectedId, expectedProductId, 1, 10F)
        );
        when(mockService.findAll()).thenReturn(mockEntities);
        // when
        mockMvc.perform(get("/v1/value-discount"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].productId").value(expectedProductId.toString()))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].amount").value(10));

        verify(mockService).findAll();
    }

    @Test
    void findAll_givenNoDiscountPolicy_ReturnsEmptyArray() throws Exception {
        // given
        when(mockService.findAll()).thenReturn(List.of());
        // when
        mockMvc.perform(get("/v1/value-discount"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(mockService).findAll();
    }

    @Test
    void findById_givenExistingUUID_ReturnsValueDiscount() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        var mockDTO = new ValueDiscount(uuid, null, 1, 20F);
        when(mockService.findById(uuid)).thenReturn(java.util.Optional.of(mockDTO));
        //when
        mockMvc.perform(get("/v1/value-discount/{uuid}", uuid))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").doesNotExist())
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.amount").value(20));

        verify(mockService, times(1)).findById(uuid);
    }

    @Test
    void findById_givenUnknownUUID_Return404() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        when(mockService.findById(uuid)).thenReturn(java.util.Optional.empty());
        //when
        mockMvc.perform(get("/v1/value-discount/{uuid}", uuid))
                // then
                .andExpect(status().isNotFound());

        verify(mockService, times(1)).findById(uuid);
    }

    @Test
    void create_givenValidDiscount_CreatesNewValueDiscount() throws Exception {
        // given
        var newValueDiscount = new NewValueDiscount(null, 1, 10);
        // when
        mockMvc.perform(post("/v1/value-discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newValueDiscount)))
                // then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
        verify(mockService, times(1)).create(any(ValueDiscount.class));
    }

    @Test
    void create_givenEmptyDiscount_Return400() throws Exception {
        // when
        mockMvc.perform(post("/v1/value-discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_givenInvalidDiscount_Return400() throws Exception {
        // given
        var newValueDiscount = new NewValueDiscount(null, -1, -10);
        // when
        mockMvc.perform(post("/v1/value-discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newValueDiscount)))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateById_givenIDAndValidDiscount_UpdatesValueDiscount() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        var newValueDiscount = new NewValueDiscount(null, 1, 10);
        // when
        mockMvc.perform(put("/v1/value-discount/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newValueDiscount)))
                // then
                .andExpect(status().isNoContent());
        verify(mockService, times(1)).updateById(eq(uuid), any(ValueDiscount.class));
    }

    @Test
    void updateById_givenEmptyDiscount_Return400() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        // when
        mockMvc.perform(put("/v1/value-discount/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateById_givenInvalidDiscount_Return400() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        var newValueDiscount = new NewValueDiscount(null, -1, -10);
        // when
        mockMvc.perform(put("/v1/value-discount/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteById_givenId_DeletesValueDiscount() throws Exception {
        var uuid = UUID.randomUUID();

        mockMvc.perform(delete("/v1/value-discount/{uuid}", uuid))
                .andExpect(status().isNoContent());

        verify(mockService, times(1)).deleteById(uuid);
    }
}