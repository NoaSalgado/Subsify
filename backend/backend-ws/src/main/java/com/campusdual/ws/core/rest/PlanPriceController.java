package com.campusdual.ws.core.rest;

import com.campusdual.api.core.service.IPlanPriceService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/planPrices")
public class PlanPriceController extends ORestController<IPlanPriceService> {


    @Autowired
    private IPlanPriceService planPriceService;
    @Override
    public IPlanPriceService getService() {
        return this.planPriceService;
    }
}
