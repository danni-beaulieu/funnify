package com.funnerimage.funnify.operations;

import com.funnerimage.funnify.model.Image;
import com.funnerimage.funnify.model.ImageOperation;

public class Rotate implements ImageOperation {

    public enum Type {
        LEFT, RIGHT
    }

    private Image image;
    private Integer value;

    public Rotate(Image image, Integer value){
        this.image = image;
        this.value = value;
    }

    public void execute() throws Exception {
        image.rotate(value);
    }
}
