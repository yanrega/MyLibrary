package com.my.library.challenge.common;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public class ResponseMessage {

    public static HashMap<String, Object> responseMissingParam(String status, String message) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("message", message);
        return map;
    }

    public static boolean invalidateParam(HashMap<String, Object> payload) {
        if(Optional.ofNullable(payload).isPresent()) {
            return true;
        }
        return false;
    }

    public static ResponseEntity<?> responseApi(String code, String message) {
        return ResponseEntity.ok().body(
                ResponseMessage.responseMissingParam(code, message));
    }

}
