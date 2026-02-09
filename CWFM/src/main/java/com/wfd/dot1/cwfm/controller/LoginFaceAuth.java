package com.wfd.dot1.cwfm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Login")
public class LoginFaceAuth {


    @GetMapping("/playsinline")
    public String showplaysinline() {

        return "requesTor/playsinline";
    }

    @GetMapping("/faceRegistration")
    public String showfaceRegistration() {

        return "requesTor/faceRegistration";
    }







}
