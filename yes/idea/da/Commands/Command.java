package yes.idea.da.Commands;

public interface Command {
    void execute(String args);

    String getDescription();
}