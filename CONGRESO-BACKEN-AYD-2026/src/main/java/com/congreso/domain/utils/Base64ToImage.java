package com.congreso.domain.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

@Component
public class Base64ToImage {

    public MultipartFile convert(String base64String) throws IOException {
        return new SimpleMultipartFile(base64String);
    }

    private static class SimpleMultipartFile implements MultipartFile {

        private final byte[] content;
        private final String filename;
        private final String contentType;

        public SimpleMultipartFile(String base64String) throws IOException {
            String[] parts = base64String.split(",");
            String metadata = parts[0];
            String base64Data = parts[1];

            this.content = Base64.getDecoder().decode(base64Data);

            // Determinar tipo
            if (metadata.contains("image/png")) {
                this.contentType = "image/png";
                this.filename = "image.png";
            } else if (metadata.contains("image/jpeg") || metadata.contains("image/jpg")) {
                this.contentType = "image/jpeg";
                this.filename = "image.jpg";
            } else if (metadata.contains("image/gif")) {
                this.contentType = "image/gif";
                this.filename = "image.gif";
            } else {
                this.contentType = "application/octet-stream";
                this.filename = "file.bin";
            }
        }

        @Override
        public String getName() {
            return "file";
        }

        @Override
        public String getOriginalFilename() {
            return filename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content == null || content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(File dest) throws IOException {
            try (FileOutputStream fos = new FileOutputStream(dest)) {
                fos.write(content);
            }
        }
    }
}