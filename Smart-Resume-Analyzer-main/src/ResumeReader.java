public class ResumeReader {

    public static void main(String[] args) {
        // Provide the path to your PDF resume here
        String resumePath = "sample_resume.pdf"; // Make sure this file exists in your project folder

        String resumeText = ResumeParser.extractTextFromPDF(resumePath);
        System.out.println("Extracted Resume Text:\n");
        System.out.println(resumeText);
    }
}
