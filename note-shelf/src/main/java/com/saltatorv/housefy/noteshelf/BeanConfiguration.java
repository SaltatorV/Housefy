package com.saltatorv.housefy.noteshelf;

import com.saltatorv.housefy.file.storage.manager.FileManager;
import com.saltatorv.housefy.file.storage.manager.FileManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanConfiguration {

    @Value("${housefy.note-shelf.files.destination}")
    private String noteShelfFilesDestination;

    @Bean
    FileManager noteShelfImplementation() {
        return new FileManagerFactory().create(noteShelfFilesDestination);
    }
}
