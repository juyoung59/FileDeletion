package com.example.scheduler.FileDeletion;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileDeletionScheduler {
	
	@Value("${file.deletion.directory}")
	private String directoryPath;
	
	// "*/10 * * * * *"
	@Scheduled(cron = "0 0 1 * * ?")
	public void deleteFiles() {
		File directory = new File(directoryPath);
		
		if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {

                    if (file.isFile() && file.getName().endsWith(".zip") && isOlderThanDays(file, 3)) {
                        file.delete();
                    }
                }
            }
        } else {
            System.out.println("Directory not found or is not a directory.");
        }
    }
	

	
	private boolean isOlderThanDays(File file, int days) {
        LocalDateTime fileDate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
        LocalDateTime thresholdDate = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
        return fileDate.isBefore(thresholdDate);
    }
}
