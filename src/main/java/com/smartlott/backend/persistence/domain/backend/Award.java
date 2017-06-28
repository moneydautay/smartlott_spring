package com.smartlott.backend.persistence.domain.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lamdevops on 6/28/17.
 */
public class Award {

    private Map<Integer, List<Lottery>> listAwards;

    public Award() {
        this.listAwards = new HashMap<>();
    }

    public Map<Integer, List<Lottery>> getListAwards() {
        return listAwards;
    }

    public void setListAwards(Map<Integer, List<Lottery>> listAwards) {
        this.listAwards = listAwards;
    }

    public void add(Integer key, List<Lottery> value) {
        listAwards.put(key, value);
    }

    public void add(Integer key, Lottery value) {
        List<Lottery> lotteries = listAwards.containsKey(key) ? listAwards.get(key) : new ArrayList<>();
        lotteries.add(value);
        listAwards.put(key, lotteries);
    }

    public List<Lottery> get(Integer key) {
        return listAwards.get(key);
    }
}
