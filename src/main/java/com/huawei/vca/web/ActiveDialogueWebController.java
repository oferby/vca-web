package com.huawei.vca.web;

import com.huawei.vca.repository.SessionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("data/dialogue")
public class ActiveDialogueWebController {

    @Autowired
    private SessionController sessionController;

    @GetMapping("sessions")
    public Set<String> getSessionList(){
        return sessionController.getAllSessions();
    }



}
