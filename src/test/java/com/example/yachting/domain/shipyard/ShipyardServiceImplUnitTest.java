package com.example.yachting.domain.shipyard;

import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShipyardServiceImplUnitTest {

    private ShipyardServiceImpl underTest;
    @Mock
    private ShipyardRepository shipyardRepository;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        underTest = new ShipyardServiceImpl(shipyardRepository, modelMapper);
    }


    @Test
    void shouldFindAllShipyards() throws ResourceNotFoundException {
        // when
        try {
            underTest.findAllShipyards();
        } catch (Exception e) { }

        // then
        verify(shipyardRepository).findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    void shouldCountAllShipyards() {
        // when
        underTest.countAllShipyards();
        // then
        verify(shipyardRepository).countAllBy();
    }

}