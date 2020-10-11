package com.gstu.pda.controllers;

import com.gstu.pda.database.repository.TypesRepository;
import com.gstu.pda.entities.TypesEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * createdAt: 10/11/2020
 * project: One
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/types")
public class TypesController extends BasicController<TypesEntity> {
    public TypesController(TypesRepository repository) {
        super(repository);
    }
}
