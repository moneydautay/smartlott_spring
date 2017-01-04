package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Network;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.NetworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mrs Hoang on 04/01/2017.
 */
@Service
@Transactional(readOnly = true)
public class NetworkService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkService.class);

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private UserService userService;

    @Value("${network.level}")
    private int level;


    @Transactional
    public List<Network> createNetworks(List<Network> networks){
        Iterable<Network> datas = (Iterable<Network>) networks;
        return ( List<Network>) networkRepository.save(datas);
    }


    @Transactional
    public void processAncestor(User user){
        List<Network> networks = new ArrayList<>();
        networks.addAll(findAncestor(user, level,1));
        //save network
        createNetworks(networks);
    }

    public List<Network> findAncestor(User user, int level, int currentLevel){
        List<Network> networks = new ArrayList<>();
        User localUser = userService.findOne(user.getIntroducedBy().getId());
        networks.add(new Network(user, localUser,currentLevel));
        level--;
        currentLevel++;
        if(level > 0)
            networks.addAll(findAncestor(user, localUser, level, currentLevel));
        return networks;
    }

    public List<Network> findAncestor(User user, User ancestor,int level, int currentLevel){
        List<Network> networks = new ArrayList<>();
        User localUser = userService.findOne(ancestor.getIntroducedBy().getId());
        networks.add(new Network(user, localUser,currentLevel));
        level--;
        currentLevel++;
        if(level > 0)
            networks.addAll(findAncestor(user, localUser, level, currentLevel));
        return networks;
    }

    /**
     * Retrieve all network of user
     * @param userId
     * @return A list of network of user
     */
    public Page<Network> getByOfUserId(long userId, Pageable pageable) {
        return networkRepository.findByOfUserId(userId, pageable);
    }

    /**
     * Retrive all network of ancestor given by userid
     * @param userId
     * @return a list of ancestor network
     */
    public Page<Network> getByAncestor(long userId, Pageable pageable) {
        return networkRepository.findByAncestorId(userId, pageable);
    }
}
