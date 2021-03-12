package com.funnerimage.funnify.model;

import java.util.ArrayList;
import java.util.List;

public class ImageExecutor {

    private final List<ImageOperation> operations = new ArrayList<>();


    public void queueOperations(Image image, List<ImageOperation> imageOperations) {
        for (ImageOperation operation : imageOperations) {
            operations.add(operation);
        }
    }

    public void executeOperations(Image image) throws Exception {
        for (ImageOperation operation : operations) {
            operation.execute();
        }
        operations.clear();
    }
}
