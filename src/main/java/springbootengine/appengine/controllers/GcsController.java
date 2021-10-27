package springbootengine.appengine.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;


//Access files in Cloud Storage
@RestController
public class GcsController {

    //The bucket that you created, check this by doing echo $BUCKET command and pass it in @Value
    @Value("gs://REPLACE_WITH_YOUR_BUCKET/my-file.txt")
    private Resource gcsFile;

    //Reading the file in the cloud
    @GetMapping
    public String readGcsFile() throws IOException {
        return StreamUtils.copyToString(
                gcsFile.getInputStream(),
                Charset.defaultCharset());
    }

    //Write to the file in Cloud Storage
    @PostMapping
    public String writeGcs(@RequestBody String data) throws IOException {
        try (OutputStream os = ((WritableResource) gcsFile).getOutputStream()) {
            os.write(data.getBytes());
        }
        return "file was updated\n";
    }

}
