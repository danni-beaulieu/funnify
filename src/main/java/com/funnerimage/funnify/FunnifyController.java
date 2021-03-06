package com.funnerimage.funnify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funnerimage.funnify.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/funnerimage")
public class FunnifyController {

    @Autowired
    FunnifyService funnifyService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final ObjectMapper mapper = new ObjectMapper();


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/greeting")
    @ResponseBody
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/funnify",
            consumes = {"multipart/form-data"},
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public ResponseEntity<?> doOperations(@RequestPart("image") MultipartFile file,
                                          @RequestPart("operations") OperationsInput json) {

        File imageFile;
        Image.Type imageType = null;
        try {
            imageFile = convert(file);
            String fileType = getFileType(imageFile);
            if (fileType.equals(MediaType.IMAGE_JPEG_VALUE.toString())) {
                imageType = Image.Type.JPEG;
            } else if (fileType.equals(MediaType.IMAGE_PNG_VALUE.toString())) {
                imageType = Image.Type.PNG;
            } else {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File type '" + fileType + "' not supported.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("File corrupt or not supported.");
        }

        Image image = new Image(imageFile, imageType);
        List<ImageOperation> imageOperations;
        try {
            imageOperations = funnifyService.convertOperations(image, json.getOperations());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        try {
            funnifyService.doOperations(image, imageOperations);
            return ResponseEntity.status(HttpStatus.OK).body(Files.readAllBytes(imageFile.toPath()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    public File convert(MultipartFile multipartFile) throws Exception {
        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    public String getFileType(File file) throws Exception{
        String fileType = "UNDETERMINED";
        MimetypesFileTypeMap m = new MimetypesFileTypeMap(file.toPath().toString());
        fileType = m.getContentType(file);
        return fileType;
    }
}
