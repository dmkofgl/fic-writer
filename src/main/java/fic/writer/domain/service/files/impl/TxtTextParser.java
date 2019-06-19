package fic.writer.domain.service.files.impl;

import fic.writer.domain.service.files.TextParser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TxtTextParser implements TextParser {

    @Override
    public String parseFile(MultipartFile file) {
        String fileContent = "";
        try {
            ByteArrayOutputStream stringWriter = new ByteArrayOutputStream();
            IOUtils.copy(file.getInputStream(), stringWriter);
            fileContent = stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return fileContent;
    }
}
