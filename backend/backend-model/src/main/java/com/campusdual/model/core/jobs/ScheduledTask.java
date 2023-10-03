package com.campusdual.model.core.jobs;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTask {


    @Scheduled(fixedRate=5000)
    public void scheduleTask(){
        System.out.println("hola");
    }
}
