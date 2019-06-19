package fic.writer.domain.service.files.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.enums.State;
import fic.writer.domain.service.files.BookParser;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class XmlBookParser implements BookParser {
    private Document xmlDocument;
    private XPath xPath;

    {
        xPath = XPathFactory.newInstance().newXPath();
    }

    public XmlBookParser(MultipartFile file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            xmlDocument = dBuilder.parse(file.getInputStream());
        } catch (ParserConfigurationException
                | SAXException
                | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        xmlDocument.getDocumentElement().normalize();
    }


    @Override
    public String getTitle() {
        final String BOOK_TITLE_PATH = "//FictionBook/description/title-info/book-title";
        String title = "";
        try {
            XPathExpression expr = xPath.compile(BOOK_TITLE_PATH);
            title = (String) expr.evaluate(xmlDocument, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return trimAndNormalizeSpace(title);
    }

    @Override
    public String getDescription() {
        final String BOOK_DESCRIPTION_PATH = "//FictionBook/description/title-info/annotation";
        String description = "";
        try {
            XPathExpression expr = xPath.compile(BOOK_DESCRIPTION_PATH);
            description = (String) expr.evaluate(xmlDocument, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return trimAndNormalizeSpace(description);
    }

    @Override
    public Set<Profile> getCoauthors() {
        throw new UnsupportedOperationException("fb2 doesn't contain this field");
    }

    @Override
    public State getState() {
        throw new UnsupportedOperationException("fb2 doesn't contain this field");
    }

    @Override
    public Set<Article> getArticles() {
        final String ARTICLE_CONTAINER_PATH = "//body";
        final String ARTICLE_ELEMENT_NAME = "section";
        final String TITLE_ELEMENT_NAME = "title";
        final String ANNOTATION_ELEMENT_NAME = "annotation";
        final String CONTENT_TAG_NAME = "p";
        Set<Article> articles = new LinkedHashSet<>();
        Element bodyElement;
        try {
            XPathExpression sectionPathExpression = xPath.compile(ARTICLE_CONTAINER_PATH);
            bodyElement = (Element) sectionPathExpression.evaluate(xmlDocument, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        NodeList articleNodes = bodyElement.getElementsByTagName(ARTICLE_ELEMENT_NAME);
        for (int i = 0; i < articleNodes.getLength(); i++) {
            Element articleElement = (Element) articleNodes.item(i);

            String title = getNormalizedTagContent(articleElement, TITLE_ELEMENT_NAME);
            String annotation = getNormalizedTagContent(articleElement, ANNOTATION_ELEMENT_NAME);

            StringBuilder content = new StringBuilder();
            NodeList articleContentNodes = articleElement.getElementsByTagName(CONTENT_TAG_NAME);
            for (int j = 0; j < articleContentNodes.getLength(); j++) {
                if (articleContentNodes.item(j).getParentNode().getNodeName().equals(ARTICLE_ELEMENT_NAME)) {
                    content.append("\n")
                            .append(articleContentNodes.item(j).getTextContent())
                            .append("\n");
                }
            }
            Article article = Article.builder()
                    .title(title)
                    .annotation(annotation)
                    .content(content.toString())
                    .build();
            articles.add(article);
        }

        return articles;
    }

    private String trimAndNormalizeSpace(String source) {
        return source.trim().replaceAll("(\\s)\\1", "$1");
    }

    private String getNormalizedTagContent(Element source, String tagName) {
        Optional<Node> annotationNode = getFirstNodeByTag(source, tagName);
        return trimAndNormalizeSpace(annotationNode.map(Node::getTextContent).orElse(""));
    }

    private Optional<Node> getNode(Element element, String tagName, int index) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        Optional<Node> result;

        result = index < nodeList.getLength()
                ? Optional.of(nodeList.item(index))
                : Optional.empty();
        return result;
    }

    private Optional<Node> getFirstNodeByTag(Element element, String tagName) {
        return getNode(element, tagName, 0);
    }
}
