
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://autotrader.ca");

        WebElement d = driver.findElement(By.id("rfMakes"));
        Select l = new Select(d);
        List<WebElement> m = l.getOptions();

        List<String> makeList = m.stream().map(e -> e.getText()).collect(Collectors.toList());

        File csvFile = new File("vehicle_listing_" + java.time.LocalDateTime.now() + ".csv");
        FileWriter fileWriter = new FileWriter(csvFile);
        // header
        fileWriter.write("Year,Make,Model,Mileage,Price\n");

        makeList.forEach(make -> {
            if (!make.equals("Any Make")) {
                System.out.println(make);
                driver.navigate().to("https://autotrader.ca/cars/" + make + "/on");

                WebElement pageSizElement = driver.findElement(By.id("pageSize"));
                Select pageSizSelect = new Select(pageSizElement);
                // update to 100 items per page
                pageSizSelect.selectByVisibleText("100");
                WebElement lastPageLink;
                // exit the loop when last page is disabled
                int offset = 0;
                do {
                    System.out.println("-page: " + (offset + 1));
                    if (offset != 0) {
                        String url = driver.getCurrentUrl();
                        url = url.replace("rcs=" + (offset - 1) * 100, "rcs=" + offset * 100);
                        driver.navigate().to(url);
                    }
                    offset++;

                    // wait to load full page
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    // save to csv file
                    try {
                        fileWriter.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    lastPageLink = driver.findElements(By.className("last-page-link")).get(0);

                    // download the data
                    List<WebElement> vehicleList = driver.findElements(By.className("result-item-inner"));
                    vehicleList.forEach(vehicle -> {
                        String[] detailWards = vehicle.findElement(By.className("listing-details")).getText()
                                .split(" ");
                        String year = detailWards[0];
                        String model = detailWards[2];
                        String mileage = vehicle.findElement(By.className("kms")).getText().split(" ")[1].replace(",",
                                "");
                        String price = vehicle.findElement(By.id("price-amount-value")).getText().replace("$", "")
                                .replace(",",
                                        "");

                        String csvEntry = String.join(",", year, make, model, mileage, price) + "\n";

                        try {
                            fileWriter.write(csvEntry);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        System.out.println("---" + csvEntry);
                    });

                } while (!lastPageLink.getAttribute("class").contains("disabled"));

            }
        });

        fileWriter.close();
        driver.quit();
    }
}
