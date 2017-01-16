package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.NetworkLevel;
import com.smartlott.backend.persistence.repositories.NetworkLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mrs Hoang on 16/01/2017.
 */
@Service
@Transactional
public class NetworkLevelService {

    @Autowired
    private NetworkLevelRepository networkLevelRepository;

    @Transactional
    public NetworkLevel create(NetworkLevel networkLevel){
        return networkLevelRepository.save(networkLevel);
    }

    @Transactional
    public NetworkLevel update(NetworkLevel networkLevel){
        return networkLevelRepository.save(networkLevel);
    }

    @Transactional
    public void delete(int id){
        networkLevelRepository.delete(id);
    }

    public List<NetworkLevel> getAll(){
        return networkLevelRepository.findAll();
    }

    public NetworkLevel getOne(int id){
        return networkLevelRepository.findOne(id);
    }
}
