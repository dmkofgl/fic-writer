package fic.writer.domain.service.files;

import org.springframework.web.multipart.MultipartFile;

public interface TextParser {
    String parseFile(MultipartFile file);
}
