import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // ✅ Absolute path to resume
        String resumePath = "resumes/vishnu-priya.pdf";

        // ✅ Absolute path to keywords
        String keywordsPath = "data/job_keywords.txt";

        // Step 1: Extract text from resume
        String resumeText = ResumeParser.extractTextFromPDF(resumePath);

        // Step 2: Load job keywords (pass absolute path, no extra prefix inside SkillMatcher)
        List<String> keywords = SkillMatcher.loadKeywords(keywordsPath);

        // Step 3: Match skills
        int matched = SkillMatcher.countMatches(resumeText, keywords);

        // Step 4: Show result
        System.out.println("Matched Skills: " + matched + " out of " + keywords.size());
        System.out.println("Missing Skills:");
        for (String keyword : keywords) {
            if (!resumeText.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("- " + keyword);
            }
        }

        double percent = (matched * 100.0) / keywords.size();
        System.out.printf("Match Percentage: %.2f%%\n", percent);
    }
}
