import java.util.List;

public class Word {
    private String lastWord; // Last word in path
    private List<String> path; // Storing path
    private int length; // Length of the path.

    public Word(List<String> path, int length, String lastWord) {
        this.path = path;
        this.length = length;
        this.lastWord = lastWord;
    }

    public List<String> getPath() {
        return path;
    }
    public int getLength() {
        return length;
    }
    public String getLastWord() {
        return lastWord;
    }
}



