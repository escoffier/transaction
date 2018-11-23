package com.example.transaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = Controller.class)
public class ControllerAdvisor {

//    @ModelAttribute
//    public void doAdvice(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, @RequestBody String requestString) {
//        System.out.println("ControllerAdvisor, requestString: " + requestString);
//    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> exception(Model model, Exception ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("error: " , ex.getMessage());
        return result;
    }
}
