/**
 * Created by Happy on 24.12.2018.
 */

public class MainClass {
    private int class_number = 20;
    private String class_string = "Hello, world";

    public int getLocalNumber() {
        return 14;
    }
    public int getClassNumber() {
        int a = this.class_number;
        return a;
    }
    public String getClassString() {
        String a = this.class_string;
        return a;
    }
}
