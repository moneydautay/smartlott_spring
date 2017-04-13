package com.smartlott.utils;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by greenlucky on 4/13/17.
 */
public class ResponseUtil {

    private ResponseUtil() {
        throw new AssertionError("Not instantiable");
    }

    public static Map<String, Object> Response(MessageDTO messageDTO, Object object) {
        return new HashMap<String, Object>(){{
            put("messages", Arrays.asList(messageDTO));
            put("content", object); }};
    }

    public static Map<String, Object> Response(List<MessageDTO> messageDTOS, Object object) {
        return new HashMap<String, Object>(){{
            put("messages", messageDTOS);
            put("content", object); }};
    }
}
