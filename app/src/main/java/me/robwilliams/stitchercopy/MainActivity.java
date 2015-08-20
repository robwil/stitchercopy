package me.robwilliams.stitchercopy;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    private EditText editDestinationFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editDestinationFolder = (EditText) findViewById(R.id.destinationFolder);
    }

    public void copyAudioFiles(View view) {
        try {
            // Read values from UI
            String destinationFolder = String.valueOf(editDestinationFolder.getText());
            if (destinationFolder.trim().isEmpty()) {
                Toast.makeText(this, "Destination Folder can't be empty", Toast.LENGTH_LONG).show();
                return;
            }

            // Construct command
            String command = "mkdir -p /storage/extSdCard/Audio/" + destinationFolder + "/ && " + // Create destination directory
                    "ls /sdcard/Android/data/com.stitcher.app/files/*.audio | " + // Find all .audio files in Stitcher directory
                    "sed -r 's/(\\/sdcard\\/Android\\/data\\/com.stitcher.app\\/files\\/)([^\\.]*)\\.audio/\\1\\2.audio \\/storage\\/extSdCard\\/Audio\\/" + destinationFolder + "\\/\\2.mp3/' | " + // Construct params for "cp" command - #uglyEscapes
                    "xargs -n 2 cp"; // execute copy commands

            // Execute command as root user (required since these are other app's files)
            Process root = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
            int returnValue = root.waitFor();
            if (returnValue != 0) {
                Toast.makeText(this, "Something went wrong running command", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
