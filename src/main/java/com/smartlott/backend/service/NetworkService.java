package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Network;
import com.smartlott.backend.persistence.domain.backend.NetworkLevel;
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


    @Autowired
    private NetworkLevelService networkLevelService;

    @Transactional
    public List<Network> createNetworks(List<Network> networks){
        Iterable<Network> datas = networks;
        return ( List<Network>) networkRepository.save(datas);
    }


    @Transactional
    public void processAncestor(User user){
        List<Network> networks = new ArrayList<>();
        networks.addAll(findAncestor(user, level,1));
        //save network
        createNetworks(networks);
    }

    /**
     * Finds all ancestor given by user, level, currentLevel or null if not exist.
     *
     * @param user
     * @param level number of level want control network. Example Cust A introduces Cust B
     *              Cust B; Cust B introduces Cust C and Cust C introduces Cust D. Assume we
     *              set level is 2 so Cust B is level 1 and Cust C is level 2 of Cust A.
     *              Cust D will out of network of A but Cust D is in network of Cust B.
     *              Otherwise if we set level is 3 that mean Cust D also in network of
     *              Cust A and similar for another case.
     * @param currentLevel this param control recursive of this function
     * @return A list network or null if not exist
     */
    public List<Network> findAncestor(User user, int level, int currentLevel){
        List<Network> networks = new ArrayList<>();
        if(user.getIntroducedBy() == null)
            return networks;
        User localUser = userService.findOne(user.getIntroducedBy().getId());

        //get network level
        NetworkLevel networkLevel = new NetworkLevel(currentLevel);

        //add network level to list
        networks.add(new Network(user, localUser, networkLevel));
        level--;
        currentLevel++;
        if(level > 0)
            networks.addAll(findAncestor(user, localUser, level, currentLevel));
        return networks;
    }

    /**
     * Finds all ancestor of user's ancestor user given by user, level, currentLevel or null if not exist.
     *
     * @param user
     * @param level number of level want control network. Example Cust A introduces Cust B
     *              Cust B; Cust B introduces Cust C and Cust C introduces Cust D. Assume we
     *              set level is 2 so Cust B is level 1 and Cust C is level 2 of Cust A.
     *              Cust D will out of network of A but Cust D is in network of Cust B.
     *              Otherwise if we set level is 3 that mean Cust D also in network of
     *              Cust A and similar for another case.
     * @param ancestor
     * @param currentLevel this param control recursive of this function
     * @return A list network or null if not exist
     */
    public List<Network> findAncestor(User user, User ancestor,int level, int currentLevel){
        List<Network> networks = new ArrayList<>();
        if(ancestor.getIntroducedBy() == null)
            return networks;
        User localUser = userService.findOne(ancestor.getIntroducedBy().getId());

        //get network level
        NetworkLevel networkLevel = networkLevelService.getOne(currentLevel);

        networks.add(new Network(user, localUser,networkLevel));
        level--;
        currentLevel++;
        if(level > 0)
            networks.addAll(findAncestor(user, localUser, level, currentLevel));
        return networks;
    }

    /**
     * Retrieves all network of user
     * @param userId
     * @return A list of network of user
     */
    public List<Network> getByOfUserId(long userId) {
        return networkRepository.findByOfUserId(userId);
    }

    /**
     * Retrieves all network of user
     * @param userId
     * @return A list of network of user
     */
    public Page<Network> getByOfUserId(long userId, Pageable pageable) {
        return networkRepository.findByOfUserId(userId, pageable);
    }



    /**
     * Retrieves all network of ancestor given by userId
     * @param userId
     * @return a list of ancestor network
     */
    public List<Network> getByAncestor(long userId) {
        return networkRepository.findByAncestorId(userId);
    }

    /**
     * Retrieves all network of ancestor given by userid
     * @param userId
     * @return a page of ancestor network
     */
    public Page<Network> getByAncestor(long userId, Pageable pageable) {
        return networkRepository.findByAncestorId(userId, pageable);
    }
}
