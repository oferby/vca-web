package com.huawei.vca.web;

import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.controller.BotUtterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("data/actions")
public class ActionWebController {

    private static final Logger logger = LoggerFactory.getLogger(ActionWebController.class);

    @Autowired
    private BotUtterRepository botUtterRepository;

    @GetMapping(produces = "application/json")
    public List<BotUtterEntity> getAll(HttpSession httpSession) {
        List<BotUtterEntity> utterEntityList = botUtterRepository.findAll();
        Collections.sort(utterEntityList);
        return utterEntityList;
    }

    @PostMapping
    public BotUtterEntity save(@RequestBody BotUtterEntity entity) {
        return this.botUtterRepository.save(entity);
    }

    @PutMapping("{id}")
    public BotUtterEntity update(@RequestBody BotUtterEntity updated, @PathVariable String id) {

        return this.botUtterRepository.save(updated);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        botUtterRepository.deleteById(id);
    }

}
