package com.huawei.vca.web;

import com.huawei.vca.repository.IntentEntity;
import com.huawei.vca.repository.controller.IntentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("data/intents")
public class IntentWebController {

    private static final Logger logger = LoggerFactory.getLogger(IntentWebController.class);

    @Autowired
    private IntentRepository intentRepository;

    @GetMapping(produces = "application/json")
    public List<IntentEntity> getIntentEntitylist() {
        logger.debug("got get all intents request.");
        List<IntentEntity> all = intentRepository.findAll();

        Collections.sort(all);

        return all;
    }

    @GetMapping("{id}")
    public Optional<IntentEntity> getById(@PathVariable String id) {
        logger.debug("got get intent by id request. id: " + id);
        return intentRepository.findById(id);
    }

    @PostMapping
    public IntentEntity save(@RequestBody IntentEntity intentEntity){
        return intentRepository.save(intentEntity);
    }

    @PutMapping("{id}")
    public IntentEntity update(@RequestBody IntentEntity intentEntity, @PathVariable String id) {
        return intentRepository.save(intentEntity);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        logger.debug("got delete intent by id request. id: " + id);
        intentRepository.deleteById(id);
    }

    
}
