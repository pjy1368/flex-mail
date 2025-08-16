package com.jayon.flexmail.infrastructure.repository;

import com.jayon.flexmail.domain.mail.TempMailRepository;
import com.jayon.flexmail.infrastructure.entity.TempMailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempMailRedisRepository extends TempMailRepository, CrudRepository<TempMailEntity, String> {
} 
