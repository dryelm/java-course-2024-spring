package edu.java.linkUpdater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LinkUpdater {
    private final static Logger LOGGER = LogManager.getLogger();

    @Scheduled(fixedDelayString = "#{@interval}")
    public void update() {
        LOGGER.trace("Обновление ссылок...");
    }
}
