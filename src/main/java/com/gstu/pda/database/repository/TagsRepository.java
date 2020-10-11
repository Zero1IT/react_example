package com.gstu.pda.database.repository;

import com.gstu.pda.entities.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
public interface TagsRepository extends JpaRepository<TagsEntity, Integer> {
}
