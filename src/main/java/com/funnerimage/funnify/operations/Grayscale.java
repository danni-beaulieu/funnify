package com.funnerimage.funnify.operations;

import com.funnerimage.funnify.model.Image;
import com.funnerimage.funnify.model.ImageOperation;

public class Grayscale implements ImageOperation {

    private Image image;

    public Grayscale(Image image){
        this.image = image;
    }

    public void execute() throws Exception {
        image.grayscale();
    }
}
