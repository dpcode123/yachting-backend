package com.example.yachting.domain.yacht;

import com.example.yachting.domain.shipyard.Shipyard;
import com.example.yachting.domain.shipyard.ShipyardRepository;
import com.example.yachting.exception.exceptions.ResourceAlreadyExistsException;
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
 * Yacht service implementation.
 * @author dp
 */
@Service
public class YachtServiceImpl implements YachtService {

    private final YachtRepository yachtRepository;
    private final ShipyardRepository shipyardRepository;
    private final ModelMapper modelMapper;

    public YachtServiceImpl(YachtRepository yachtRepository, ShipyardRepository shipyardRepository, ModelMapper modelMapper) {
        this.yachtRepository = yachtRepository;
        this.shipyardRepository = shipyardRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if no yachts are found
     */
    @Override
    public ResponseEntity<List<YachtDTO>> findAllYachts() {
        List<Yacht> yachts = yachtRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if(yachts.isEmpty()) {
            throw new ResourceNotFoundException("No yachts found.");
        }

        List<YachtDTO> yachtDTOS = yachts.stream()
                .map(this::mapYachtToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(yachtDTOS);
    }

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Long> countAllYachts() {
        Long count = yachtRepository.countAllBy();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if yacht is not found
     */
    @Override
    public ResponseEntity<YachtDTO> findYachtById(Long id) {
        YachtDTO yachtDTO = yachtRepository.findById(id)
                .map(this::mapYachtToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Yacht not found."));

        return ResponseEntity.status(HttpStatus.OK).body(yachtDTO);
    }

    /**
     * {@inheritDoc}
     * @throws TransactionFailedException if yacht saving transaction failed
     */
    @Override
    @Transactional
    public ResponseEntity<YachtDTO> addYacht(YachtCommand yachtCommand) {
        Yacht yacht = mapCommandToYacht(yachtCommand);

        YachtDTO yachtDTO = Optional.of(yachtRepository.save(yacht))
                .map(this::mapYachtToDTO)
                .orElseThrow(() -> new TransactionFailedException("Yacht not added."));

        return ResponseEntity.status(HttpStatus.CREATED).body(yachtDTO);
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if yacht is not found
     * @throws TransactionFailedException if yacht editing transaction failed
     */
    @Override
    @Transactional
    public ResponseEntity<YachtDTO> editYacht(Long yachtId, YachtCommand yachtCommand) {
        if (!yachtRepository.existsById(yachtId)) {
            throw new ResourceNotFoundException("Yacht not found.");
        }

        Yacht yacht = mapCommandToYacht(yachtCommand);
        yacht.setId(yachtId);

        YachtDTO yachtDTO = Optional.of(yachtRepository.save(yacht))
                .map(this::mapYachtToDTO)
                .orElseThrow(() -> new TransactionFailedException("Yacht not edited."));

        return ResponseEntity.status(HttpStatus.OK).body(yachtDTO);
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if yacht deleting transaction failed
     */
    @Override
    @Transactional
    public ResponseEntity<Long> deleteYacht(Long yachtId) {
        Long removedYachtsCount = yachtRepository.removeById(yachtId);
        if (removedYachtsCount.equals(0L)) {
            throw new ResourceNotFoundException("Yacht not deleted.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(removedYachtsCount);
    }

    /**
     * Maps command to object.
     * First checks if yacht's shipyard exists.
     * @param yachtCommand
     * @return Yacht object
     */
    private Yacht mapCommandToYacht(final YachtCommand yachtCommand) {
        if (!shipyardRepository.existsById(yachtCommand.getShipyardId())) {
            throw new ResourceNotFoundException("Yacht's Shipyard not found.");
        }
        return modelMapper.map(yachtCommand, Yacht.class);
    }

    /**
     * Map yacht object to DTO.
     * @param yacht
     * @return YachtDTO object
     */
    private YachtDTO mapYachtToDTO(Yacht yacht) {
        return modelMapper.map(yacht, YachtDTO.class);
    }
}
