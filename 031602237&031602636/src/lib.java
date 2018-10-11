

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lib implements wordCount{
	@Override
	public int linesCount(String filepath) throws IOException {
		return 0;
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
