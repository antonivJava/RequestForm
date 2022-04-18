package org.example.service;

import org.example.model.Request;
import org.example.repository.RequestTypeRepository;
import org.example.repository.RequestRepository;
import org.example.repository.entries.RequestTypeEntity;
import org.example.repository.entries.RequestEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RequestService saves and receives data from repositories and handles controllers requests.
 */
@Service
public class RequestService {
    private RequestRepository requestRepository;
    private RequestTypeRepository requestTypeRepository;

    public RequestService(RequestRepository requestRepository, RequestTypeRepository requestTypeRepository) {
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
    }

    public Request saveRequest(Request request) {
        RequestEntity entity = new RequestEntity(request);
        requestRepository.save(entity);
        return new Request(entity);
    }

    public List<String> getRequestTypes() {
        return requestTypeRepository.findAll().stream().map(RequestTypeEntity::getRequestType).collect(Collectors.toList());
    }
}
