package com.example.myawesomepastebin.sheduler;

import com.example.myawesomepastebin.repository.PastaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.time.Instant;

@Slf4j
@Component
public class CleanPasta {
    private final PastaRepository pastaRepository;

    public CleanPasta(PastaRepository pastaRepository) {
        this.pastaRepository = pastaRepository;
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void clearTokens() {
        log.info("Cleaned old pasta's");
        pastaRepository.deleteAllByExpiredDateIsBefore(Instant.now());
    }
}
