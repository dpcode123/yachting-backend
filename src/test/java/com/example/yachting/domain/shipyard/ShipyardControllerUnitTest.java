package com.example.yachting.domain.shipyard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


class ShipyardControllerUnitTest {

    ShipyardService shipyardService = Mockito.mock(ShipyardService.class);
    ShipyardController shipyardController = new ShipyardController(shipyardService);

    private ShipyardDTO shipyardDTO;
    private List<ShipyardDTO> shipyardDTOList;

    @BeforeEach
    void setUp() {
        shipyardDTO = new ShipyardDTO();
        shipyardDTO.setId(1L);

        shipyardDTOList = List.of(
                new ShipyardDTO(),
                new ShipyardDTO()
        );
    }


    @Test
    void shouldFindAll() {
        when(shipyardService.findAllShipyards()).thenReturn(shipyardDTOList);

        assertNotNull(shipyardController.findAll());
        assertEquals(shipyardController.findAll().getBody(), shipyardDTOList);
    }

    @Test
    void shouldFindShipyardById() {
        when(shipyardService.findShipyardById(anyLong())).thenReturn(shipyardDTO);

        assertNotNull(shipyardController.findShipyardById(anyLong()));
        assertEquals(shipyardController.findShipyardById(anyLong()).getBody(), shipyardDTO);
    }
}