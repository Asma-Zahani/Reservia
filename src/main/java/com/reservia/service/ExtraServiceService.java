package com.reservia.service;

import com.reservia.entity.ExtraService;
import com.reservia.repository.ExtraServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtraServiceService {

    @Autowired
    private ExtraServiceRepository repository;

    public List<ExtraService> getAll() {
        return repository.findAll();
    }
}