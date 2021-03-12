package com.funnerimage.funnify.operations;

import com.funnerimage.funnify.model.Image;
import com.funnerimage.funnify.model.ImageOperation;

public class Resize implements ImageOperation {

    private Image image;
    private Integer width;
    private Integer height;

    public Resize(Image image, Integer width, Integer height){
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public void execute() throws Exception {
        image.resize(width, height);
    }
}
