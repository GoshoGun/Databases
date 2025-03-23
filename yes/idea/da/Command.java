package yes.idea.da;

public interface Command {
    void execute(String args);

    String getDescription();
}