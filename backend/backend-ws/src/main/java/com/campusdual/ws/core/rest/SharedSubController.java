package com.campusdual.ws.core.rest;

import com.campusdual.api.core.service.ISharedSubService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sharedSubs")
public class SharedSubController extends ORestController<ISharedSubService> {
    @Autowired
    private ISharedSubService sharedSubService;
    @Override
    public ISharedSubService getService() {
        return this.sharedSubService;
    }
}
