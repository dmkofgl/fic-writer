package fic.writer.domain.service.helper;

class BookStringBuilder {
    private final static String DEFAULT_SPLITTER = ":";
    private String content = "";

    public BookStringBuilder addParagraph(String content) {
        this.content += "\n" + content;
        return this;
    }

    public BookStringBuilder addDescription(String header, String value) {
        return this.addDescription(header, value, DEFAULT_SPLITTER);
    }

    public BookStringBuilder addDescription(String header, String value, String splitter) {
        this.content += "\n" + header + splitter + value;
        return this;
    }

    public String getContent() {
        return content;
    }
}
