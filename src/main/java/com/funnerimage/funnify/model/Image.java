package com.funnerimage.funnify.model;

import com.funnerimage.funnify.operations.Mirror;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

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

    public void grayscale() throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(file.getPath());
        op.colorspace("Gray");
        op.addImage(file.getPath());
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
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
