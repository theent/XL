package model;

import java.io.IOException;

public class CircularError extends Error {
    public CircularError (String text){
        super(text);
    }
}
