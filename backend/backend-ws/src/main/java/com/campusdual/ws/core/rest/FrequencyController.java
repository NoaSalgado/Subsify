package com.campusdual.ws.core.rest;

import com.campusdual.api.core.service.IFrequencyService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frequencies")
public class FrequencyController extends ORestController<IFrequencyService> {

    @Autowired
    private IFrequencyService frequencyService;
    @Override
    public IFrequencyService getService() {
        return this.frequencyService;
    }
}
