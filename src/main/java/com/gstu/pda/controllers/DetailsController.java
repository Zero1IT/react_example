package com.gstu.pda.controllers;

import com.gstu.pda.database.repository.DetailsRepository;
import com.gstu.pda.entities.DetailsEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/details")
public class DetailsController extends BasicController<DetailsEntity> {
    public DetailsController(DetailsRepository detailsRepository) {
        super(detailsRepository);
    }
}
