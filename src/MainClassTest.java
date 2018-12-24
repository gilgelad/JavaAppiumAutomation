import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Happy on 24.12.2018.
 */
public class MainClassTest {
    MainClass mainClass = new MainClass();

    @Test
    public void testGetLocalNumber() {
        Assert.assertTrue("Переданное значение не равно 14!",mainClass.getLocalNumber() == 14);


    }
}
