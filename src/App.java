
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("Hello, World!");
        System.setProperty("webdriver.chrome.driver", "E:\\4ai3\\scrapebot\\bin\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://autotrader.ca");

        WebElement d = driver.findElement(By.id("rfMakes"));
        System.out.println(d);
        Select l = new Select(d);
        List<WebElement> m = l.getOptions();
        System.out.println("Drodown list items are: ");
        // iterate through options till list size
        for (int j = 0; j < m.size(); j++) {
            String s = m.get(j).getText();
            System.out.println(s);
        }

        l.selectByVisibleText("Acura");

        WebElement postalCode = driver.findElement(By.id("locationAddress"));
        postalCode.sendKeys("N2V2Y4");

        WebElement searchButton = driver.findElement(By.id("SearchButton"));
        searchButton.click();

    }
}
