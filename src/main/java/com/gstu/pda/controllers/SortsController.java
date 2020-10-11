package com.gstu.pda.controllers;

import com.gstu.pda.database.repository.SortsRepository;
import com.gstu.pda.entities.SortsEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * createdAt: 10/11/2020
 * project: One
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/sorts")
public class SortsController extends BasicController<SortsEntity> {
    public SortsController(SortsRepository repository) {
        super(repository);
    }
}
