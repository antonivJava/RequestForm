package org.example.repository;

import org.example.repository.entries.RequestTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTypeRepository extends JpaRepository<RequestTypeEntity, Integer> {}