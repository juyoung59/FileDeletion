package com.example.scheduler.FileDeletion;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileDeletionScheduler {
	
	@Value("${file.deletion.directory}")
	private String directoryPath;
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void deleteFiles() {
		File directory = new File(directoryPath);
		
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						file.delete();
					}
				}
			}
		} else {
			System.out.println("Directory not found or is not a directory.");
		}
	}
}
