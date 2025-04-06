package yes.idea.da;

import yes.idea.da.Interface.DatabaseClient;

public class Main {
    public static void main(String[] args) {
        DatabaseClient cli = new DatabaseClient();
        cli.run();
    }
}