package fic.writer.domain.service.files.impl;

import fic.writer.domain.service.files.TextParser;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//TODO IT read unformatted text
public class DocxTextParser implements TextParser {

    @Override
    public String parseFile(MultipartFile file) {
        StringBuilder fileContent = new StringBuilder();
        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                fileContent.append(paragraphs.get(i).getParagraphText());
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return fileContent.toString();
    }
}
