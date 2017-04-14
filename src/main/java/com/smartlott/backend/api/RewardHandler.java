package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Reward;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.RewardService;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * Created by greenlucky on 4/13/17.
 */
@RestController
@RequestMapping(RewardHandler.API_REWARD_URL)
public class RewardHandler {

    public static final String API_REWARD_URL = "/api/reward";


    @Autowired
    private RewardService rewardService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOs;

    @RequestMapping("/list-all")
    public ResponseEntity<Object> getListAllReward() {

        List<Reward> rewards = rewardService.getAll();

        return new ResponseEntity<Object>(rewards, HttpStatus.OK);
    }

    @RequestMapping("/all")
    public ResponseEntity<Object> getAllReward(Pageable pageable) {

        Page<Reward> rewards = rewardService.getAll(pageable);

        return new ResponseEntity<Object>(rewards, HttpStatus.OK);
    }

    @GetMapping("/{rewardId}")
    public ResponseEntity<Object> getReward(@PathVariable int rewardId, Locale locale) {
        messageDTOs = new ArrayList<>();

        Reward reward = getLocalReward(rewardId, locale);

        return new ResponseEntity<Object>(reward, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> createReward(@RequestBody Reward reward, Locale locale) {
        messageDTOs = new ArrayList<>();
        reward = rewardService.create(reward);
        messageDTOs.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.reward.success.edit.text", reward.getName(), locale)));

        Map<String, Object> response = new HashMap<>();
        response.put("messages", messageDTOs);
        response.put("content", reward);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PutMapping("/{rewardId}")
    public ResponseEntity<Object> editReward(@PathVariable int rewardId, @RequestBody Reward reward, Locale locale) {
        messageDTOs = new ArrayList<>();
        Reward localReward = getLocalReward(rewardId, locale);
        reward = rewardService.update(reward);

        messageDTOs.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.reward.success.edit.text", localReward.getName(), locale)));

        Map<String, Object> response = new HashMap<>();
        response.put("messages", messageDTOs);
        response.put("content", reward);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{rewardId}")
    public ResponseEntity<Object> deleteReward(@PathVariable int rewardId, Locale locale) {

        messageDTOs = new ArrayList<>();

        Reward localReward = getLocalReward(rewardId, locale);
        try {
            rewardService.delete(rewardId);
        }catch (Exception ex) {
            messageDTOs.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.reward.error.delete.text", localReward.getName(), locale)));
            return new ResponseEntity<Object>(messageDTOs, HttpStatus.EXPECTATION_FAILED);
        }

        messageDTOs.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.reward.success.delete.text", localReward.getName(), locale)));
        return new ResponseEntity<Object>(messageDTOs, HttpStatus.EXPECTATION_FAILED);
    }

    private Reward getLocalReward(int rewardId, Locale locale) {

        Reward reward = rewardService.getOne(rewardId);
        if (reward == null) {
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.reward.error.id.not.found.text",
                            String.valueOf(rewardId), locale));

            throw new NotFoundException(messageDTO);
        }
        return reward;
    }
}
