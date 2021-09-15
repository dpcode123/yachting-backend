package com.example.yachting.domain.yacht;

import com.example.yachting.domain.shipyard.ShipyardRepository;
import com.example.yachting.exception.exceptions.NoContentFoundException;
import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import com.example.yachting.exception.exceptions.TransactionFailedException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Yacht service implementation.
 * @author dp
 */
@Service
@RequiredArgsConstructor
public class YachtServiceImpl implements YachtService {

    private final YachtRepository yachtRepository;
    private final ShipyardRepository shipyardRepository;
    private final ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     * @throws NoContentFoundException if no yachts are found
     */
    @Override
    @Transactional(readOnly = true)
    public List<YachtDTO> findAllYachts() {
        return yachtRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(this::mapYachtToDTO)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Long countAllYachts() {
        return yachtRepository.countAllBy();
    }

    /**
     * {@inheritDoc}
     * @throws NoContentFoundException if yacht is not found
     */
    @Override
    @Transactional(readOnly = true)
    public YachtDTO findYachtById(Long id) {
        return yachtRepository.findById(id)
                .map(this::mapYachtToDTO)
                .orElseThrow(() -> new NoContentFoundException("Yacht not found."));
    }

    /**
     * {@inheritDoc}
     * @throws TransactionFailedException if yacht saving transaction failed
     */
    @Override
    @Transactional
    public YachtDTO addYacht(YachtCommand yachtCommand) {
        Yacht yacht = mapCommandToYacht(yachtCommand);

        YachtDTO yachtDTO = Optional.of(yachtRepository.save(yacht))
                .map(this::mapYachtToDTO)
                .orElseThrow(() -> new TransactionFailedException("Yacht not added."));
        return yachtDTO;
    }

    /**
     * {@inheritDoc}
     * @throws NoContentFoundException if yacht is not found
     * @throws TransactionFailedException if yacht editing transaction failed
     */
    @Override
    @Transactional
    public YachtDTO editYacht(Long yachtId, YachtCommand yachtCommand) {
        if (!yachtRepository.existsById(yachtId)) {
            throw new NoContentFoundException("Yacht not found.");
        }

        Yacht yacht = mapCommandToYacht(yachtCommand);
        yacht.setId(yachtId);

        YachtDTO yachtDTO = Optional.of(yachtRepository.save(yacht))
                .map(this::mapYachtToDTO)
                .orElseThrow(() -> new TransactionFailedException("Yacht not edited."));
        return yachtDTO;
    }

    /**
     * {@inheritDoc}
     * @throws TransactionFailedException if yacht deleting transaction failed
     */
    @Override
    @Transactional
    public Long deleteYacht(Long yachtId) {
        Long removedYachtsCount = yachtRepository.removeById(yachtId);
        if (removedYachtsCount.equals(0L)) {
            throw new TransactionFailedException("Yacht not deleted.");
        }
        return removedYachtsCount;
    }

    /**
     * Maps command to object.
     * First checks if yacht's shipyard exists.
     * @throws NoContentFoundException if shipyard is not found
     * @param yachtCommand
     * @return Yacht object
     */
    private Yacht mapCommandToYacht(final YachtCommand yachtCommand) {
        if (!shipyardRepository.existsById(yachtCommand.getShipyardId())) {
            throw new NoContentFoundException("Yacht's Shipyard not found.");
        }
        return modelMapper.map(yachtCommand, Yacht.class);
    }

    /**
     * Map yacht object to DTO.
     * @param yacht
     * @return YachtDTO object
     */
    private YachtDTO mapYachtToDTO(final Yacht yacht) {
        return modelMapper.map(yacht, YachtDTO.class);
    }
}
