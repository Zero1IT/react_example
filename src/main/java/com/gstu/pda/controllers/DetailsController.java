package com.gstu.pda.controllers;

import com.gstu.pda.database.repository.*;
import com.gstu.pda.entities.DetailsEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/details")
public class DetailsController extends BasicController<DetailsEntity> {

    private final SortsRepository sortsRepository;
    private final TagsRepository tagsRepository;
    private final TypesRepository typesRepository;
    private final UnitsRepository unitsRepository;

    public DetailsController(DetailsRepository detailsRepository, SortsRepository sortsRepository, TagsRepository tagsRepository, TypesRepository typesRepository, UnitsRepository unitsRepository) {
        super(detailsRepository);
        this.sortsRepository = sortsRepository;
        this.tagsRepository = tagsRepository;
        this.typesRepository = typesRepository;
        this.unitsRepository = unitsRepository;
    }

    @Override
    public DetailsEntity update(@RequestBody DetailsEntity entity) {
        persist(entity);
        return super.update(entity);
    }

    private void persist(DetailsEntity entity) {
        if (entity.getSorts().getId() == 0) {
            entity.setSorts(sortsRepository.save(entity.getSorts()));
        }
        if (entity.getTags().getId() == 0) {
            entity.setTags(tagsRepository.save(entity.getTags()));
        }
        if (entity.getTypes().getId() == 0) {
            entity.setTypes(typesRepository.save(entity.getTypes()));
        }
        if (entity.getUnits().getId() == 0) {
            entity.setUnits(unitsRepository.save(entity.getUnits()));
        }
    }

    @Override
    public DetailsEntity create(@RequestBody DetailsEntity entity) {
        persist(entity);
        return super.create(entity);
    }
}
