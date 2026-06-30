package dev.mayur.librarymanagement.core.utils.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProfileLogger implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProfileLogger.class);

    @Value("${spring.profiles.active:default}")
    private String activeProfiles;

    @Override
    public void run(String... args) throws Exception {
        logger.info("===========> Application is running with Active Profile(s): {} <===========", activeProfiles);
    }
}
