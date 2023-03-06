
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class App {
    public static void main(String[] args) throws Exception {

        String CHROM_DRIVER_WINDOWS = "./chromedriver/chromedriver.exe";
        String CHROM_DRIVER_MAC_OS = "./chromedriver/chromedriver";
        String WINDOWS = "WINDOWS";

        System.out.println("App Started.");

        System.setProperty("webdriver.chrome.driver",
                System.getProperty("os.name").toUpperCase().contains(WINDOWS) ? CHROM_DRIVER_WINDOWS
                        : CHROM_DRIVER_MAC_OS);
        WebDriver driver = new ChromeDriver();
        driver.get("https://autotrader.ca");

        WebElement d = driver.findElement(By.id("rfMakes"));
        System.out.println(d);
        Select l = new Select(d);
        List<WebElement> m = l.getOptions();
        System.out.println("Drodown list items are: ");

        List<String> makeList = m.stream().map(e -> e.getText()).collect(Collectors.toList());

        // iterate through options till list size
        // for (int j = 0; j < m.size(); j++) {
        // String make = m.get(j).getText();
        // System.out.println(make);
        // }

        makeList.forEach(make -> {
            if (!make.equals("Any Make")) {
                System.out.println(make);
                driver.navigate().to("https://autotrader.ca/cars/" + make + "/on");
                WebElement pageSizElement = driver.findElement(By.id("pageSize"));
                Select pageSizSelect = new Select(pageSizElement);
                // update to 100 items per page
                pageSizSelect.selectByVisibleText("100");
                // todo: next page logic
                // todo: download the data
            }
        });

        // l.selectByVisibleText("Acura");

        // WebElement postalCode = driver.findElement(By.id("locationAddress"));
        // postalCode.sendKeys("N2V2Y4");

        // WebElement searchButton = driver.findElement(By.id("SearchButton"));
        // searchButton.click();
    }
}
