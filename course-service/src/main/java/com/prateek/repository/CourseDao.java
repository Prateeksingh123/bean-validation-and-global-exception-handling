package com.prateek.repository;

import com.prateek.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

public interface CourseDao extends CrudRepository<CourseEntity, Integer> {
}
