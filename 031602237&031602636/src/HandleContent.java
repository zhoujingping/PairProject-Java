import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandleContent {


    private List<String> titles = new ArrayList<>();
    private List<String> abstracts = new ArrayList<>();

    private String handledContent;

    public HandleContent(String path) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
 //               stringBuilder.append(line).append("\n");
                if (line.startsWith("Title: ")) {
                    titles.add(line.substring(7));
                    stringBuilder.append(line.substring(7));
                    stringBuilder.append("\n");
                } else if (line.startsWith("Abstract: ")) {
                    abstracts.add(line.substring(10));
                    stringBuilder.append(line.substring(10));
                    stringBuilder.append("\n");
                }
            }
            String content = stringBuilder.toString();
            handledContent = content.replace("\r", "");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File do not exist.");
        }

    }



    public List<String> getTitles() {
        return titles;
    }


    public List<String> getAbstracts() {
        return abstracts;
    }


    public String getHandledContent() {
        return handledContent;
    }

}
