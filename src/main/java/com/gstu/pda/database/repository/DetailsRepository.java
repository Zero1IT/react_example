package com.gstu.pda.database.repository;

import com.gstu.pda.entities.DetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
public interface DetailsRepository extends JpaRepository<DetailsEntity, Integer> {
}
