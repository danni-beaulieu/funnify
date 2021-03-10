package com.funnerimage.funnify.operations;

import com.funnerimage.funnify.model.Image;
import com.funnerimage.funnify.model.ImageOperation;

public class Thumbnail implements ImageOperation {

    private Image image;

    public Thumbnail(Image image){
        this.image = image;
    }

    public void execute() throws Exception {
        image.thumbnail();
    }
}
