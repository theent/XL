package model;

public class Comment implements Cell {

    //TODO samma som i Expression
    private String content;
    private String text;

    public Comment(String content, String text) {
        this.content = content;
        this.text = text;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return text;
    }
}
