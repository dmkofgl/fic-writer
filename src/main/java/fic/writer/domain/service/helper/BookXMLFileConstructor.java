package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.ByteArrayOutputStream;

public class BookXMLFileConstructor extends BookFileConstructor {
    private Document document;
    private XPath xPath;

    public BookXMLFileConstructor() {
        super();
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException("cannot create documentBuilder");
        }

        document = documentBuilder.newDocument();
        Element root = document.createElement("FictionBook");
        document.appendChild(root);

        Element description = document.createElement("description");
        root.appendChild(description);

        Element annotation = document.createElement("annotation");
        description.appendChild(annotation);

        Element body = document.createElement("body");
        root.appendChild(body);

        Element titleInfo = document.createElement("title-info");

        description.appendChild(titleInfo);

        xPath = XPathFactory.newInstance().newXPath();
    }

    @Override
    protected void writeTitle(Book book) {
        Element titleInfo;
        try {
            String path = "//FictionBook/description/title-info";
            XPathExpression sectionPathExpression = xPath.compile(path);
            titleInfo = (Element) sectionPathExpression.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        Element bookTitle = document.createElement("book-title");
        titleInfo.appendChild(bookTitle);
        bookTitle.setTextContent(book.getTitle());
    }

    @Override
    protected void writeDescription(Book book) {
        Element titleInfo;
        try {
            String path = "//FictionBook/description/title-info";
            XPathExpression sectionPathExpression = xPath.compile(path);
            titleInfo = (Element) sectionPathExpression.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        Element annotation = document.createElement("annotation");
        titleInfo.appendChild(annotation);

        annotation.setTextContent(book.getDescription());
    }

    @Override
    protected void writeAuthor(Book book) {
        Element titleInfo;
        try {
            String path = "//FictionBook/description/title-info";
            XPathExpression sectionPathExpression = xPath.compile(path);
            titleInfo = (Element) sectionPathExpression.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        Element author = document.createElement("author");
        Element firstName = document.createElement("first-name");
        titleInfo.appendChild(author);
        author.appendChild(firstName);
        firstName.setTextContent(book.getAuthor().getUsername());
    }

    @Override
    protected void writeCoauthors(Book book) {

    }

    @Override
    protected void writeSize(Book book) {

    }

    @Override
    protected void writeState(Book book) {

    }

    @Override
    protected void writeArticleHeaders(Book book) {

    }

    @Override
    protected void writeArticlesContent(Book book) {
        Element body;
        try {
            String path = "//FictionBook/body";
            XPathExpression sectionPathExpression = xPath.compile(path);
            body = (Element) sectionPathExpression.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        for (Article article : book.getArticles()) {
            Element section = document.createElement("section");
            Element title = document.createElement("title");
            Element titleName = document.createElement("p");
            titleName.setTextContent(article.getTitle());

            Element annotation = document.createElement("p");
            annotation.setTextContent(article.getAnnotation());
            String changedContent = "<p>" + article.getContent().replaceAll("\n", "</p><p>");

            body.appendChild(section);
            section.appendChild(title);
            title.appendChild(titleName);
            section.appendChild(annotation);
            section.setTextContent(changedContent);
        }
    }

    @Override
    public byte[] convertToByteArray(Book book) {
        super.buildBook(book);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(bos);
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource domSource = new DOMSource(document);
            transformer.transform(domSource, result);
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new RuntimeException("cannot convert book");
        }
        return bos.toByteArray();

    }
}
