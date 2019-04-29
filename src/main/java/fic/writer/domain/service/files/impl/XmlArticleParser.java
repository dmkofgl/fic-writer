package fic.writer.domain.service.files.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.service.files.ArticleParser;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlArticleParser implements ArticleParser {
    private Element element;

    @Override
    public Article parse() {
        return Article.builder()
                .title(this.getTitle())
                .annotation(this.getAnnotation())
                .content(this.getContent())
                .build();
    }

    public XmlArticleParser(MultipartFile multipartFile) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(multipartFile.getInputStream());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        doc.getDocumentElement().normalize();
        element = doc.getDocumentElement();
    }


    @Override
    public String getTitle() {
        return element.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
    }

    @Override
    public String getAnnotation() {
        return element.getElementsByTagName("annotation").item(0).getFirstChild().getNodeValue();
    }

    @Override
    public String getContent() {
        return element.getElementsByTagName("section").item(0).getFirstChild().getNodeValue();
    }


}
