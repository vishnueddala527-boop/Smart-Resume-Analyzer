import java.nio.file.*;
import java.util.*;

public class SkillMatcher {
    public static List<String> loadKeywords(String filename) throws Exception {
        // Load keywords from a relative path (data/job_keywords.txt)
        return Files.readAllLines(Paths.get(filename));
    }

    public static int countMatches(String resumeText, List<String> keywords) {
        int count = 0;
        for (String keyword : keywords) {
            if (resumeText.toLowerCase().contains(keyword.toLowerCase())) {
                count++;
            }
        }
        return count;
    }
}
