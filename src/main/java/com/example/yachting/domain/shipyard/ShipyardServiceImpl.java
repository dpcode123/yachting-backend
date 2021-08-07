package com.example.yachting.domain.shipyard;

import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import com.example.yachting.exception.exceptions.TransactionFailedException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Shipyard service implementation.
 * @author dp
 */
@Service
public class ShipyardServiceImpl implements ShipyardService {

    private final ShipyardRepository shipyardRepository;
    private final ModelMapper modelMapper;

    public ShipyardServiceImpl(ShipyardRepository shipyardRepository, ModelMapper modelMapper) {
        this.shipyardRepository = shipyardRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if no shipyards are found
     */
    @Override
    public ResponseEntity<List<ShipyardDTO>> findAllShipyards() {
        List<Shipyard> shipyards = shipyardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (shipyards.isEmpty()) {
            throw new ResourceNotFoundException("No shipyards found.");
        }

        List<ShipyardDTO> shipyardDTOS = shipyards.stream()
                .map(this::mapShipyardToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(shipyardDTOS);
    }

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Long> countAllShipyards() {
        Long count = shipyardRepository.countAllBy();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if shipyard is not found
     */
    @Override
    public ResponseEntity<ShipyardDTO> findShipyardById(Long shipyardId) {
        ShipyardDTO shipyardDTO = shipyardRepository.findById(shipyardId)
                .map(this::mapShipyardToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Shipyard not found."));

        return ResponseEntity.status(HttpStatus.OK).body(shipyardDTO);
    }

    /**
     * {@inheritDoc}
     * @throws TransactionFailedException if shipyard saving transaction failed
     */
    @Override
    @Transactional
    public ResponseEntity<ShipyardDTO> addShipyard(ShipyardCommand shipyardCommand) {
        Shipyard shipyard = mapCommandToShipyard(shipyardCommand);

        ShipyardDTO shipyardDTO = Optional.of(shipyardRepository.save(shipyard))
                .map(this::mapShipyardToDTO)
                .orElseThrow(() -> new TransactionFailedException("Shipyard not added."));

        return ResponseEntity.status(HttpStatus.CREATED).body(shipyardDTO);
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if shipyard is not found
     * @throws TransactionFailedException if shipyard editing transaction failed
     */
    @Override
    @Transactional
    public ResponseEntity<ShipyardDTO> editShipyard(Long shipyardId, ShipyardCommand shipyardCommand) {
        if (!shipyardRepository.existsById(shipyardId)) {
            throw new ResourceNotFoundException("Shipyard not found.");
        }

        Shipyard shipyard = mapCommandToShipyard(shipyardCommand);
        shipyard.setId(shipyardId);

        ShipyardDTO shipyardDTO = Optional.of(shipyardRepository.save(shipyard))
                .map(this::mapShipyardToDTO)
                .orElseThrow(() -> new TransactionFailedException("Shipyard not edited."));

        return ResponseEntity.status(HttpStatus.OK).body(shipyardDTO);
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if shipyard deleting transaction failed
     */
    @Override
    @Transactional
    public ResponseEntity<Long> deleteShipyard(Long shipyardId) {
        Long removedShipyardsCount = shipyardRepository.removeById(shipyardId);
        if (removedShipyardsCount.equals(0L)) {
            throw new ResourceNotFoundException("Shipyard not deleted.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(removedShipyardsCount);
    }

    /**
     * Maps shipyard command to object.
     * @param shipyardCommand
     * @return Shipyard
     */
    private Shipyard mapCommandToShipyard(final ShipyardCommand shipyardCommand) {
        return modelMapper.map(shipyardCommand, Shipyard.class);
    }

    /**
     * Maps shipyard object to DTO.
     * @param shipyard
     * @return ShipyardDTO
     */
    private ShipyardDTO mapShipyardToDTO(Shipyard shipyard) {
        return modelMapper.map(shipyard, ShipyardDTO.class);
    }
}
