package edu.java.services;

import edu.java.services.interfaces.LinkUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DefaultLinkUpdater implements LinkUpdater {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public void update() {
        LOGGER.trace("Обновление ссылок...");
    }
}
