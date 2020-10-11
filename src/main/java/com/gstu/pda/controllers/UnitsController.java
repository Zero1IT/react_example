package com.gstu.pda.controllers;

import com.gstu.pda.database.repository.UnitsRepository;
import com.gstu.pda.entities.UnitsEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * createdAt: 10/11/2020
 * project: One
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/units")
public class UnitsController extends BasicController<UnitsEntity> {
    public UnitsController(UnitsRepository repository) {
        super(repository);
    }
}
