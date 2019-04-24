package fic.writer.domain.service.files.impl;

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

    public XmlArticleParser(MultipartFile multipartFile) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(multipartFile.getInputStream());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (SAXException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        doc.getDocumentElement().normalize();
        element = doc.getDocumentElement();
        String title = element.getElementsByTagName("book-title").item(0).getFirstChild().getNodeValue();
        String annotation = element.getElementsByTagName("annotation").item(0).getFirstChild().getNodeValue();
    }

    @Override
    public String getTitle() {
        String title = element.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
        return title;
    }

    @Override
    public String getAnnotation() {
        String annotation = element.getElementsByTagName("annotation").item(0).getFirstChild().getNodeValue();
        return null;
    }

    @Override
    public String getContent() {
        String annotation = element.getElementsByTagName("section").item(0).getFirstChild().getNodeValue();
        return null;
    }
}
