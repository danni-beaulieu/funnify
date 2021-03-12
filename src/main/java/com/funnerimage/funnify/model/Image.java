package com.funnerimage.funnify.model;

import com.funnerimage.funnify.operations.Mirror;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import java.io.File;

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

    public void mirror(Mirror.Type type) throws Exception{
        IMOperation op = new IMOperation();
        op.addImage(file.getPath());
        switch (type) {
            case FLIP:
                op.flip();
                break;
            case FLOP:
                op.flop();
                break;
        }
        op.addImage(file.getPath());
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    public void thumbnail(Integer width, Integer height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(file.getPath());
        op.thumbnail(width, height);
        op.addImage(file.getPath());
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    public void resize(Integer width, Integer height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(file.getPath());
        op.resize(width, height);
        op.addImage(file.getPath());
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    public void rotate(Integer value) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(file.getPath());
        op.rotate(value.doubleValue());
        op.addImage(file.getPath());
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }
}
