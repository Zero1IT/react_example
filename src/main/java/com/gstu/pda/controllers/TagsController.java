package com.gstu.pda.controllers;

import com.gstu.pda.database.repository.TagsRepository;
import com.gstu.pda.entities.TagsEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * createdAt: 10/11/2020
 * project: One
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/tags")
public class TagsController extends BasicController<TagsEntity> {
    public TagsController(TagsRepository repository) {
        super(repository);
    }
}
