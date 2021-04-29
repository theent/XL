package model;

public class Comment implements Cell {

    private String content;

    public Comment(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

   /* @Override
    public void updateContent(Object content) {
        if (content instanceof String){
            String str = (String) content;
            this.content = str;
        }

        throw new IllegalArgumentException();
    }*/

    @Override
    public String toString() {
        return content;
    }
}
