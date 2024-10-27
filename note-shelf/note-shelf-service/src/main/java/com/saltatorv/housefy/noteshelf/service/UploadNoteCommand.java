package com.saltatorv.housefy.noteshelf.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public record UploadNoteCommand(String fileName, MultipartFile multipartFile, String destination) {
}