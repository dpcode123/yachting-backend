package com.example.yachting.domain.shipyard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipyardServiceImplTest {

    @InjectMocks
    private ShipyardServiceImpl underTest;

    @Mock
    private ShipyardRepository shipyardRepository;

    @Mock
    private ModelMapper modelMapper;

    private Shipyard shipyard = new Shipyard();

    @BeforeEach
    void setUp() {
        underTest = new ShipyardServiceImpl(shipyardRepository, modelMapper);
        shipyard.setId(1L);
        shipyard.setName("Shipyard Name");
    }


    @Test
    @Disabled
    void canFindAllShipyards() {
        // when
        underTest.findAllShipyards();
        // then
        verify(shipyardRepository).findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    void canCountAllShipyards() {
        // when
        underTest.countAllShipyards();
        // then
        verify(shipyardRepository).countAllBy();
    }

    @Test
    void canFindShipyardById() throws Exception {
        /*// given
        Long shipyardId = 508L;

        // when
        underTest.findShipyardById(shipyardId);

        // then
        ArgumentCaptor<Long> shipyardIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(shipyardRepository).findById(shipyardIdArgumentCaptor.capture());
        Long capturedId = shipyardIdArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(shipyardId);*/

        /*when(shipyardRepository.findById(1L))
                .thenReturn(
                        new ResponseEntity<>("Hello World!", HttpStatus.OK)
                );*/

        underTest.findShipyardById(1L);
    }

    @Test
    @Disabled
    void addShipyard() {
    }

    @Test
    @Disabled
    void editShipyard() {
    }

    @Test
    @Disabled
    void deleteShipyard() {
    }
}