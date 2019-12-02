package com.huawei.vca.repository.nlu;

import com.huawei.vca.grpc.NluService;
import com.huawei.vca.intent.Entity;
import com.huawei.vca.intent.NluResponse;
import com.huawei.vca.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NluControllerImpl implements NluController{

    @Autowired
    private NluService nluService;


    public void handleNlu(Dialogue dialogue) {

        String text = dialogue.getText();
        UserUtterEvent userUtterEvent = new UserUtterEvent(text);
        dialogue.addToHistory(userUtterEvent);
        NluEvent nluEvent = handleNlu(text);
        userUtterEvent.setNluEvent(nluEvent);
        dialogue.setLastNluEvent(nluEvent);


    }

    private NluEvent handleNlu(String text) {
        NluResponse nluResponse = nluService.getNluResponse(text);
        NluEvent nluEvent = new NluEvent();
        nluEvent.setBestIntent(new Intent(nluResponse.getIntent().getName(), nluResponse.getIntent().getConfidence()));

        for (Entity entity : nluResponse.getEntitiesList()) {
            nluEvent.addSlot(new Slot(entity.getEntity(), entity.getValue(), entity.getConfidence(),entity.getStart(), entity.getEnd()));
        }

        return nluEvent;
    }


}
