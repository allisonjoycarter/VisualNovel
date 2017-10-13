package Playground.VisualNovel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GetFile {
    private InputStream path;

    public GetFile(InputStream file_path) {
        path = file_path;

    }

    public ArrayList<String> OpenFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        InputStreamReader fr = new InputStreamReader(path);
        BufferedReader br = new BufferedReader(fr);

        try {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
//            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
