package com.example.yachting.domain.shipyard;

import com.example.yachting.exception.exceptions.NoContentFoundException;
import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import com.example.yachting.exception.exceptions.TransactionFailedException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Shipyard service implementation.
 * @author dp
 */
@Service
@RequiredArgsConstructor
public class ShipyardServiceImpl implements ShipyardService {

    private final ShipyardRepository shipyardRepository;
    private final ModelMapper modelMapper;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipyardDTO> findAllShipyards() {
        return shipyardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(this::mapShipyardToDTO)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Long countAllShipyards() {
        Long count = shipyardRepository.countAllBy();
        return count;
    }

    /**
     * {@inheritDoc}
     * @throws NoContentFoundException if shipyard is not found
     */
    @Override
    @Transactional(readOnly = true)
    public ShipyardDTO findShipyardById(Long shipyardId) {
        return shipyardRepository.findById(shipyardId)
                .map(this::mapShipyardToDTO)
                .orElseThrow(() -> new NoContentFoundException("Shipyard not found."));
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
     * @throws NoContentFoundException if shipyard is not found
     * @throws TransactionFailedException if shipyard editing transaction failed
     */
    @Override
    @Transactional
    public ShipyardDTO editShipyard(Long shipyardId, ShipyardCommand shipyardCommand) {
        if (!shipyardRepository.existsById(shipyardId)) {
            throw new NoContentFoundException("Shipyard not found.");
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
     * @throws TransactionFailedException if shipyard deleting transaction failed
     */
    @Override
    @Transactional
    public Long deleteShipyard(Long shipyardId) {
        Long removedShipyardsCount = shipyardRepository.removeById(shipyardId);
        if (removedShipyardsCount.equals(0L)) {
            throw new TransactionFailedException("Shipyard not deleted.");
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
    private ShipyardDTO mapShipyardToDTO(final Shipyard shipyard) {
        return modelMapper.map(shipyard, ShipyardDTO.class);
    }
}
