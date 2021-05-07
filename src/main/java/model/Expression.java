package model;

public class Expression implements CellContent {


     //TODO döp om till exprContent kanske?
     private double content;
     //TODO döp om till exprText kanske?
     private String text;

     public Expression(double content, String text) {
          this.content = content;
          this.text = text;
     }

     public Double getContent() {
          return content;
     }

     @Override
     public String toString() {
          return text;
     }
}
