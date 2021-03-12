package com.funnerimage.funnify;

import com.funnerimage.funnify.model.Image;
import com.funnerimage.funnify.model.ImageExecutor;
import com.funnerimage.funnify.model.ImageOperation;
import com.funnerimage.funnify.model.OperationInput;
import com.funnerimage.funnify.operations.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("funnifyService")
public class FunnifyService {

    public enum Operations {
        GRAYSCALE, FLIP, FLOP, THUMBNAIL, SIMPLEROTATE, DEGREEROTATE, RESIZE
    }

    List<ImageOperation> convertOperations(Image image, List<OperationInput> operations) throws IllegalArgumentException {
        List<ImageOperation> imageOperations = new ArrayList<>();
        for (OperationInput operation : operations) {
            switch (Operations.valueOf(operation.getType().toUpperCase())) {
                case GRAYSCALE:
                    imageOperations.add(new Grayscale(image));
                    break;
                case FLIP:
                    imageOperations.add(new Mirror(image, Mirror.Type.FLIP));
                    break;
                case FLOP:
                    imageOperations.add(new Mirror(image, Mirror.Type.FLOP));
                    break;
                case THUMBNAIL:
                    Integer thumbnailWidth;
                    Integer thumbnailHeight;
                    try {
                        String[] size = operation.getValue().split("x", 2);
                        thumbnailWidth = Integer.parseInt(size[0]);
                        thumbnailHeight = Integer.parseInt(size[1]);
                    } catch(NumberFormatException e) {
                        throw new IllegalArgumentException("Values for thumbnail must be integers: NxN.");
                    } catch(NullPointerException e) {
                        throw new IllegalArgumentException("Null value for thumbnail not supported.");
                    }
                    imageOperations.add(new Thumbnail(image, thumbnailWidth, thumbnailHeight));
                    break;
                case SIMPLEROTATE:
                    Integer simpleDegrees = 0;
                    if (operation.getValue().equalsIgnoreCase(Rotate.Type.LEFT.toString())) {
                        simpleDegrees = -90;
                    } else if (operation.getValue().equalsIgnoreCase(Rotate.Type.RIGHT.toString())) {
                        simpleDegrees = 90;
                    } else {
                        throw new IllegalArgumentException("Value for simplerotate '" + operation.getValue() + "' not supported.");
                    }
                    imageOperations.add(new Rotate(image, simpleDegrees));
                    break;
                case DEGREEROTATE:
                    Integer detailDegrees = 0;
                    try {
                        detailDegrees = Integer.parseInt(operation.getValue());
                    } catch(NumberFormatException e) {
                        throw new IllegalArgumentException("Value for degreerotate must be an integer.");
                    } catch(NullPointerException e) {
                        throw new IllegalArgumentException("Null value for degreerotate not supported.");
                    }
                    imageOperations.add(new Rotate(image, detailDegrees));
                    break;
                case RESIZE:
                    Integer resizeWidth;
                    Integer resizeHeight;
                    try {
                        String[] size = operation.getValue().split("x", 2);
                        resizeWidth = Integer.parseInt(size[0]);
                        resizeHeight = Integer.parseInt(size[1]);
                    } catch(NumberFormatException e) {
                        throw new IllegalArgumentException("Values for resize must be integers: NxN.");
                    } catch(NullPointerException e) {
                        throw new IllegalArgumentException("Null value for resize not supported.");
                    }
                    imageOperations.add(new Resize(image, resizeWidth, resizeHeight));
                    break;
                default:
                    throw new IllegalArgumentException("Operation '" + operation.getType() + "' not supported.");
            }
        }
        return imageOperations;
    }

    void doOperations(Image image, List<ImageOperation> operations) throws Exception {
        ImageExecutor executor = new ImageExecutor();
        executor.queueOperations(image, operations);
        executor.executeOperations(image);
    }
}
