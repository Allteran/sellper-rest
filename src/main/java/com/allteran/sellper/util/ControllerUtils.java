package com.allteran.sellper.util;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ControllerUtils {
    public static Map<String, String> getFieldErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            errors.put(bindingResult.getFieldErrors().get(i).getField() + "Error",
                    bindingResult.getFieldErrors().get(i).getCode());
        }

        return errors;
    }

}
