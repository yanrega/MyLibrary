package com.my.library.challenge.service;

import com.my.library.challenge.controller.LoanDataController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    private static final Logger LOGGER = LogManager.getLogger(LoanDataController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    LoanDataController loanDataController;

    @Scheduled(cron = "0 0 0 * * ?")
    public void checking() throws Exception {
        LOGGER.info("The time is now {}", dateFormat.format(new Date()));
        loanDataController.checkLoanLimitDate();
    }
}
