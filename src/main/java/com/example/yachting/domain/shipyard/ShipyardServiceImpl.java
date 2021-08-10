package com.example.yachting.domain.shipyard;

import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import com.example.yachting.exception.exceptions.TransactionFailedException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
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
    public List<ShipyardDTO> findAllShipyards() {
        List<Shipyard> shipyards = shipyardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (shipyards.isEmpty()) {
            throw new ResourceNotFoundException("No shipyards found.");
        }

        List<ShipyardDTO> shipyardDTOS = shipyards.stream()
                .map(this::mapShipyardToDTO)
                .collect(Collectors.toList());
        return shipyardDTOS;
    }

    /** {@inheritDoc} */
    @Override
    public Long countAllShipyards() {
        Long count = shipyardRepository.countAllBy();
        return count;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if shipyard is not found
     */
    @Override
    public ShipyardDTO findShipyardById(Long shipyardId) {
        ShipyardDTO shipyardDTO = shipyardRepository.findById(shipyardId)
                .map(this::mapShipyardToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Shipyard not found."));
        return shipyardDTO;
    }

    /**
     * {@inheritDoc}
     * @throws TransactionFailedException if shipyard saving transaction failed
     */
    @Override
    @Transactional
    public ShipyardDTO addShipyard(ShipyardCommand shipyardCommand) {
        Shipyard shipyard = mapCommandToShipyard(shipyardCommand);

        ShipyardDTO shipyardDTO = Optional.of(shipyardRepository.save(shipyard))
                .map(this::mapShipyardToDTO)
                .orElseThrow(() -> new TransactionFailedException("Shipyard not added."));
        return shipyardDTO;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if shipyard is not found
     * @throws TransactionFailedException if shipyard editing transaction failed
     */
    @Override
    @Transactional
    public ShipyardDTO editShipyard(Long shipyardId, ShipyardCommand shipyardCommand) {
        if (!shipyardRepository.existsById(shipyardId)) {
            throw new ResourceNotFoundException("Shipyard not found.");
        }

        Shipyard shipyard = mapCommandToShipyard(shipyardCommand);
        shipyard.setId(shipyardId);

        ShipyardDTO shipyardDTO = Optional.of(shipyardRepository.save(shipyard))
                .map(this::mapShipyardToDTO)
                .orElseThrow(() -> new TransactionFailedException("Shipyard not edited."));
        return shipyardDTO;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if shipyard deleting transaction failed
     */
    @Override
    @Transactional
    public Long deleteShipyard(Long shipyardId) {
        Long removedShipyardsCount = shipyardRepository.removeById(shipyardId);
        if (removedShipyardsCount.equals(0L)) {
            throw new ResourceNotFoundException("Shipyard not deleted.");
        }
        return removedShipyardsCount;
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
