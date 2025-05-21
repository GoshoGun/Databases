package yes.idea.da.Interface;

/** Представя една колона от таблица (име + тип). */
public class Column {
    private final String name;
    private final String type;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public String getName() { return name; }
    public String getType() { return type; }
}
