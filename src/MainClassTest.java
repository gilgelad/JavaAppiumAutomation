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
    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("Переданное значение не больше 45!",mainClass.getClassNumber() > 45);
    }
    @Test
//Альтернативный вариант - искать с учётом регистра. Здесь показан вариант с приведением к нижнему регистру.
    public void testGetClassString() {
        String orig_value = mainClass.getClassString();
        String search_value = "hello";
        if (orig_value.toLowerCase().indexOf(search_value.toLowerCase()) != -1) {
            System.out.println("Совпадение найдено!");
        } else {
            System.out.println("Совпадений не найдено!");
        }
    }
}

