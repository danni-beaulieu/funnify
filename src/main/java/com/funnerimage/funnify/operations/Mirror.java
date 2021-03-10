package com.funnerimage.funnify.operations;

import com.funnerimage.funnify.model.Image;
import com.funnerimage.funnify.model.ImageOperation;

public class Mirror implements ImageOperation {

    public enum Type {
        FLIP, FLOP
    }

    private Image image;
    private Type type;

    public Mirror(Image image, Type type){
        this.type = type;
        this.image = image;
    }

    public void execute() {
        image.mirror(type);
    }
}
