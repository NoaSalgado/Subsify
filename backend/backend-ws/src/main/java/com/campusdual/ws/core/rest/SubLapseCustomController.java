package com.campusdual.ws.core.rest;


import com.campusdual.api.core.service.ISubLapseCustomService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subLapseCustoms")
public class SubLapseCustomController  extends ORestController<ISubLapseCustomService> {

    @Autowired
     private ISubLapseCustomService subLapseCustomService;

    @Override
    public ISubLapseCustomService getService() {
        return subLapseCustomService;
    }
}
