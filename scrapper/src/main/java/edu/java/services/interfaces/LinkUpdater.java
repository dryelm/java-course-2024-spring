package edu.java.services.interfaces;

import org.springframework.scheduling.annotation.Scheduled;

public interface LinkUpdater {
    @Scheduled(fixedDelayString = "#{@interval}") void update();
}
