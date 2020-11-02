package com.gstu.pda.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
public class BasicController<T> {

    private final JpaRepository<T, ?> repository;

    public BasicController(JpaRepository<T, ?> repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<T> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{page}")
    public List<T> getPage(@PathVariable("page") int page,
                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        return repository.findAll(PageRequest.of(page - 1, limit)).get().collect(Collectors.toList());
    }

    @PostMapping
    public T create(@Valid @RequestBody T entity) {
        return repository.save(entity);
    }

    @PutMapping
    public T update(@Valid @RequestBody T entity) {
        return repository.save(entity);
    }

    @DeleteMapping
    public void delete(@RequestBody T entity) {
        repository.delete(entity);
    }

    @DeleteMapping("/all")
    public void deleteRange(@RequestBody List<T> entities) {
        repository.deleteAll(entities);
    }
}
