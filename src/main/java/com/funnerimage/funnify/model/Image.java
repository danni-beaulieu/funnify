package com.funnerimage.funnify.model;

import com.funnerimage.funnify.operations.Mirror;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Image {

    public enum Type {
        JPEG, PNG
    }

    private Type type;
    private File file;

    public Image (File file, Type type) {
        this.file = file;
        this.type = type;
    }

    public void grayscale(){

    }

    public void mirror(Mirror.Type type) {

    }

    public void thumbnail() {

    }

    public void resize(Integer value) {

    }

    public void rotate(Integer value) {

    }
}
