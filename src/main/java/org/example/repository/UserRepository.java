package org.example.repository;

import org.example.repository.entries.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {}