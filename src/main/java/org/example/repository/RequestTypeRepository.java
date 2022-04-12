package org.example.repository;

import org.example.repository.entries.RequestTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface RequestTypeRepository extends JpaRepository<RequestTypeEntity, Integer> {}