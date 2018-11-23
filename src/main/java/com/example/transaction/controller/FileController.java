package com.example.transaction.controller;

import com.sun.javafx.collections.MappingChange;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/image")
public class FileController {

    @GetMapping("/")
    public String uploadPage() {
        return "form";
    }

    @PostMapping("/request")
    @ResponseBody
    public Map<String, Object> uploadRequest(HttpServletRequest request) {
        MultipartHttpServletRequest mreq = null;

        if(request instanceof MultipartHttpServletRequest) {
            mreq = (MultipartHttpServletRequest) request;
        }
        else {
            return dealResult(false, "upload file failed");
        }
        MultipartFile multipartFile = mreq.getFile("image");

        String fileName = multipartFile.getOriginalFilename();

        File newfile = new File(fileName);

        try {
            multipartFile.transferTo(newfile);

        } catch (Exception e) {
            e.printStackTrace();
            return dealResult(false, "upload file failed");

        }

        MultipartFile infoFile = mreq.getFile("info");

        try {
            byte[] buf = infoFile.getBytes();
            String info = new String(buf, 0, buf.length, mreq.getCharacterEncoding());
            System.out.println("--------info: " + info);
        } catch (IOException ex) {
            return dealResult(false, "image info error");
        }

        return dealResult(true, "upload file succeed");
    }

    @PostMapping("/json")
    @ResponseBody
    public Map<String, Object> json(HttpServletRequest httpServletRequest) {
        Map<String, Object> result = new HashMap<>();

        try {
            InputStream inputStream = httpServletRequest.getInputStream();

            String body = IOUtils.toString(inputStream, httpServletRequest.getCharacterEncoding());

            result.put("data", body);
        } catch (IOException ex) {
            result.put("error", "error");
        }

        return result;
    }

    private Map<String, Object> dealResult(boolean success, String message){
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);

        return result;
    }
}
