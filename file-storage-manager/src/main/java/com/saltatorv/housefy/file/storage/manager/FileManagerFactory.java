package com.saltatorv.housefy.file.storage.manager;

public class FileManagerFactory {

    public FileManager create(String destination) {
        return new FileManagerImpl(destination);
    }
}
