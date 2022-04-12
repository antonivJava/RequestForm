package org.example.service;

import org.example.model.User;
import org.example.repository.RequestTypeRepository;
import org.example.repository.UserRepository;
import org.example.repository.entries.RequestTypeEntity;
import org.example.repository.entries.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {
    private UserRepository userRepository;
    private RequestTypeRepository requestTypeRepository;

    public Service(UserRepository repository, RequestTypeRepository requestTypeRepository) {
        this.userRepository = repository;
        this.requestTypeRepository = requestTypeRepository;
        initDB();
    }

    private void initDB() {
        List<String> requestTypes = Arrays.asList("Contract Adjustment", "Damage Case", "Complaint");
        List<RequestTypeEntity> requestTypeEntities = requestTypes.stream()
                .map(RequestTypeEntity::new).collect(Collectors.toList());
        requestTypeRepository.saveAll(requestTypeEntities);
    }

    public void saveUser(User user) {
        UserEntity entity = new UserEntity(user);
        userRepository.save(entity);
    }

    public List<String> getAll() {
        return requestTypeRepository.findAll().stream().map(RequestTypeEntity::getRequestType).collect(Collectors.toList());
    }
}
