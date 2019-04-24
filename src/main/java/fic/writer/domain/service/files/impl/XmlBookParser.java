package fic.writer.domain.service.files.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.User;
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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class XmlBookParser implements BookParser {
    private Document doc;
    private XPath xPath;

    public XmlBookParser(MultipartFile file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file.getInputStream());
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

        xPath = XPathFactory.newInstance().newXPath();

        doc.getDocumentElement().normalize();
    }

    @Override
    public String getTitle() {
        String title = "";
        try {
            XPathExpression expr = xPath.compile("//FictionBook/description/title-info/book-title");
            title = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        title = title.replaceAll("(\\s)\\1", "$1");
        return title;
    }

    @Override
    public Set<User> getCoAuthors() {
        return new HashSet<>();
    }

    @Override
    public String getDescription() {
        String description = "";
        try {
            XPathExpression expr = xPath.compile("//FictionBook/description/title-info/annotation");
            description = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        description = description.trim().replaceAll("(\\s)\\1", "$1");
        return description;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public Set<Article> getArticles() {
        Set<Article> articles = new LinkedHashSet<>();
        try {
            XPathExpression sectionExpression = xPath.compile("//body");
            Element bodyElements = (Element) sectionExpression.evaluate(doc, XPathConstants.NODE);
            NodeList articlesNode = bodyElements.getElementsByTagName("section");
            for (int i = 0; i < articlesNode.getLength(); i++) {
                Element node = (Element) articlesNode.item(i);
                Optional<Node> titleNode = getNode(node, "title", 0);
                String title = titleNode.map(Node::getTextContent)
                        .orElse("");
                title = title.trim().replaceAll("(\\s)\\1", "$1");

                Optional<Node> annotationNode = getNode(node, "annotation", 0);
                String annotation = annotationNode.map(Node::getTextContent)
                        .orElse("");
                annotation = annotation.trim().replaceAll("(\\s)\\1", "$1");
                StringBuilder content = new StringBuilder();
                NodeList nodeList = node.getElementsByTagName("p");
                for (int j = 0; j < nodeList.getLength(); j++) {
                    if (nodeList.item(j).getParentNode().getNodeName() == "section") {
                        content.append("\n" + nodeList.item(j).getTextContent() + "\n");
                    }
                }
                articles.add(Article.builder()
                        .title(title)
                        .annotation(annotation)
                        .content(content.toString())
                        .build());
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return articles;
    }

    private Optional<Node> getNode(Element element, String tagName, int index) {

        NodeList nodeList = element.getElementsByTagName(tagName);
        Optional<Node> result;

        result = index < nodeList.getLength()
                ? Optional.of(nodeList.item(index))
                : Optional.empty();
        return result;

    }
}
