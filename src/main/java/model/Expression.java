package model;

public class Expression implements Cell{

     private double content;
     private String text;

     public Expression(double content, String text) {
          this.content = content;
          this.text = text;
     }

     public Object getContent() {
          return content;
     }

     @Override
     public String toString() {
          return text;
     }
}
