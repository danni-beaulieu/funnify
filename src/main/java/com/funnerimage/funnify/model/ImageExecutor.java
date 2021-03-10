package com.funnerimage.funnify.model;

import com.funnerimage.funnify.FunnifyService;

import java.util.ArrayList;
import java.util.List;

public class ImageExecutor {

    private final List<ImageOperation> operations = new ArrayList<>();


    public void queueOperations(Image image, List<ImageOperation> imageOperations) {
        for (ImageOperation operation : imageOperations) {

        }
    }

    public void executeOperations(Image image) {
        for (ImageOperation order : operations) {
            order.execute();
        }
        operations.clear();
    }
}
