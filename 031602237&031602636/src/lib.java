

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileReader;
=======
>>>>>>> 88baa88c2a684785f65ed86f85c6378c40d29a23
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lib implements wordCount{
	@Override
	public int linesCount(String filepath) throws IOException {
<<<<<<< HEAD
		int linescount = -1;
        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	System.out.println(line);
                if (line.matches("\\d+")) {
                    linescount++;
                } else if (line.length() != 0 && !line.matches("\\s+")) {
                    linescount++;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linescount;
=======
		return 0;
>>>>>>> 88baa88c2a684785f65ed86f85c6378c40d29a23
	}

	@Override
	public int wordsCount(String filepath) throws IOException {
		return 0;
	}

	@Override
	public int charsCount(String content) throws IOException {
		String regex = "\\p{ASCII}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int sum = -1;
        while (matcher.find()) {
            sum++;
        }
        return sum;
	}

	@Override
	public void wordDetail(String filepath) throws IOException {
		
	}

}
