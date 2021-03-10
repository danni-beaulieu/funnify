package com.funnerimage.funnify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funnerimage.funnify.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                                          @RequestPart("operations") OperationsInput json) throws Exception {

        File imageFile = convert(file);
        String fileType = getFileType(imageFile);
        Image.Type imageType = null;

        if (fileType.equals(MediaType.IMAGE_JPEG_VALUE.toString())) {
            imageType = Image.Type.JPEG;
        } else if (fileType.equals(MediaType.IMAGE_PNG_VALUE.toString())) {
            imageType = Image.Type.PNG;
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("File type '" + fileType + "' not supported.");
        }

        Image image = new Image(imageFile, imageType);
        try {
            funnifyService.doOperations(image,
                    funnifyService.convertOperations(image, json.getOperations()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(Files.readAllBytes(imageFile.toPath()));
    }

    public File convert(MultipartFile multipartFile) throws Exception {
        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    public String getFileType(File file) {
        String fileType = "Undetermined";
        try {
            //fileType = Files.probeContentType(file.toPath());
            MimetypesFileTypeMap m = new MimetypesFileTypeMap(file.toPath().toString());
            fileType = m.getContentType(file);
        }
        catch (IOException ioException) {
            System.out.println(
                    "ERROR: Unable to determine file type for "
                            + " due to exception " + ioException);
        }
        return fileType;
    }
}
