package com.funnerimage.funnify.operations;

import com.funnerimage.funnify.model.Image;
import com.funnerimage.funnify.model.ImageOperation;

public class Resize implements ImageOperation {

    private Image image;
    private Integer value;

    public Resize(Image image, Integer value){
        this.image = image;
        this.value = value;
    }

    public void execute() throws Exception {
        image.resize(value);
    }
}
