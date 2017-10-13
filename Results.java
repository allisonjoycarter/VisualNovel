//package VisualNovel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Results {
    private static final InputStream fileName3 = GetFile.class.getResourceAsStream("Results");
    private GetFile file3 = new GetFile(fileName3);
    private ArrayList<String> results = file3.OpenFile();

    public Results() throws IOException {

    }
}
