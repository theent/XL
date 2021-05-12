package model;

import java.io.IOException;

public class EmptyError extends Error {
    public EmptyError (String text){
        super(text);
    }
}
