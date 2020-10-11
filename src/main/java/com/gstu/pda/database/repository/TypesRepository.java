package com.gstu.pda.database.repository;

import com.gstu.pda.entities.TypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
public interface TypesRepository extends JpaRepository<TypesEntity, Integer> {
}