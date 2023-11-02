package dev.me.price.controllers;

import dev.me.price.models.ProductPrice;
import dev.me.price.services.PriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
class PriceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PriceService service;

    @Test
    void getPrice_givenProductId_fetchPriceFromService() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        var expected = new ProductPrice(100, 80);
        doReturn(Optional.of(expected)).when(service).getPrice(uuid, 1);
        // when
        mockMvc.perform(get("/v1/price/{productUUID}", uuid))
        // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stockPrice").value(100))
                .andExpect(jsonPath("$.finalPrice").value(80));
        verify(service).getPrice(uuid, 1);
    }

    @Test
    void getPrice_givenUnknownProductId_return404() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        doReturn(Optional.empty()).when(service).getPrice(uuid, 1);
        // when
        mockMvc.perform(get("/v1/price/{productUUID}", uuid))
                // then
                .andExpect(status().isNotFound());
        verify(service).getPrice(uuid, 1);
    }

    @Test
    void getPrice_givenProductIdAndQuantity_fetchPriceFromService() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        var quantity = 2;
        var expected = new ProductPrice(100, 80);
        doReturn(Optional.of(expected)).when(service).getPrice(uuid, quantity);
        // when
        mockMvc.perform(get("/v1/price/{productUUID}?quantity={quantity}", uuid, quantity))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stockPrice").value(100))
                .andExpect(jsonPath("$.finalPrice").value(80));
        verify(service).getPrice(uuid, quantity);
    }

    @Test
    void getPrice_givenInvalidQuantity_return400() throws Exception {
        // given
        var uuid = UUID.randomUUID();
        var quantity = -1;
        // when
        mockMvc.perform(get("/v1/price/{productUUID}?quantity={quantity}", uuid, quantity))
                // then
                .andExpect(status().isBadRequest());
        verify(service, never()).getPrice(uuid, quantity);
    }
}