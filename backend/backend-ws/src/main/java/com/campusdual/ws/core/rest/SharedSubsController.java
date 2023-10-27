package com.campusdual.ws.core.rest;

import com.campusdual.api.core.service.IPlanService;
import com.campusdual.api.core.service.ISharedSubsService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sharedSubs")
public class SharedSubsController extends ORestController<ISharedSubsService> {
    @Autowired
    private ISharedSubsService sharedSubsService;
    @Override
    public ISharedSubsService getService() {
        return this.sharedSubsService;
    }
}
