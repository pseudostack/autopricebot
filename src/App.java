
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

public class App {

    public static String[] pullVehicleSpecs(WebDriver vehicleDriver) {

        String[] pulledSpecs = new String[12];

        WebDriverWait w = new WebDriverWait(vehicleDriver, Duration.ofSeconds(3));
        w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@id='sl-card-body']")));

        JavascriptExecutor js = (JavascriptExecutor) vehicleDriver;

        js.executeScript("document.getElementById('sl-card-body').classList.remove('closed')");

        WebDriverWait w2 = new WebDriverWait(vehicleDriver, Duration.ofSeconds(3));
        w2.until(ExpectedConditions.presenceOfElementLocated(By.id("specificationWidget")));

        List<WebElement> specSpans = vehicleDriver.findElement(By.id("specificationWidget"))
                .findElements(By.xpath("//span[starts-with(@id,'spec-key-')]"));

        AtomicInteger specCounter = new AtomicInteger(0);

        specSpans.forEach(spec -> {

            if (spec.getText().equals("Kilometres")) {

                String valueId = "spec-value-" + specCounter;
                WebElement kms = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[0] = kms.getText();
                pulledSpecs[0] = pulledSpecs[0].replaceAll(",", "");
            }
            if (spec.getText().equals("Body Type")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[1] = type.getText();
            }
            if (spec.getText().equals("Engine")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[2] = type.getText();
            }
            if (spec.getText().equals("Transmission")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[3] = type.getText();
            }
            if (spec.getText().equals("Drivetrain")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[4] = type.getText();
            }
            if (spec.getText().equals("Exterior Colour")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[5] = type.getText();
            }
            if (spec.getText().equals("Interior Colour")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[6] = type.getText();
            }
            if (spec.getText().equals("Passengers")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[7] = type.getText();
            }
            if (spec.getText().equals("Doors")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[8] = type.getText();
            }
            if (spec.getText().equals("Fuel Type")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[9] = type.getText();
            }
            if (spec.getText().equals("City Fuel Economy")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[10] = type.getText();
            }
            if (spec.getText().equals("Hwy Fuel Economy")) {
                String valueId = "spec-value-" + specCounter;
                WebElement type = vehicleDriver.findElement(By.xpath("//span[@id='" + valueId + "']"));
                pulledSpecs[11] = type.getText();
            }

            specCounter.getAndIncrement();

        });

        return pulledSpecs;

    }

    static File createFile(String make) throws IOException {

        Date date = new Date();
        Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");

        File csvFile = null;
        try {
            csvFile = new File("vehicle_listing_" + make + "_" + formatter.format(date) + ".csv");
            if (csvFile.createNewFile()) {
                System.out.println("File is created!");

            } else {
                System.out.println("Failed to create file. File may already exist");
            }
        } catch (Exception e) {
            System.out.println("An exception was thrown" + e);
        }
        return csvFile;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String CHROM_DRIVER_WINDOWS = "./chromedriver/chromedriver.exe";
        String CHROM_DRIVER_MAC_OS = "/Users/angelena/Desktop/Programs/scrapebot/chromedriver/chromedriver";
        // String CHROM_DRIVER_MAC_OS = "./chromedriver/chromedriver";
        String WINDOWS = "WINDOWS";

        System.out.println("App Started.");

        System.setProperty("webdriver.chrome.driver",
                System.getProperty("os.name").toUpperCase().contains(WINDOWS) ? CHROM_DRIVER_WINDOWS
                        : CHROM_DRIVER_MAC_OS);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--window-position=-32000,-32000");
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://autotrader.ca");

        // WebElement d = driver.findElement(By.id("rfMakes"));
        // Select l = new Select(d);
        // List<WebElement> m = l.getOptions();

        // List<String> makeList = m.stream().map(e ->
        // e.getText()).collect(Collectors.toList());

        // Collections.reverse(makeList);

        // System.out.println(makeList.size());

        // System.out.println(makeList);
        // 104 makes
        List<String> makeList = Arrays.asList("AC", "Acura", "Alfa Romeo", "AM General", "American Bantam",
                "American Motors (AMC)", "Aston Martin", "Audi", "Austin", "Austin-Healey", "Autozam", "Bentley", "BMW",
                "Bugatti", "Buick", "Cadillac", "Checker", "Chevrolet", "Chrysler", "Citroen", "Clenet", "Daihatsu",
                "Daimler", "Datsun", "De Soto", "De Tomaso", "Dodge", "Factory Five Racing",
                "Ferrari", "Fiat", "Fisker", "Ford", "Freightliner", "Genesis", "Geo", "GMC", "Hino",
                "Honda", "Hummer", "Hyundai", "Infiniti", "Innocenti", "International", "Isuzu", "Jaguar", "Jeep",
                "Karma", "Kia", "Lamborghini", "Lancia", "Land Rover", "Lexus", "Lincoln", "Lotus", "Lucid", "Maserati",
                "Maybach", "Mazda", "McLaren", "Mercedes-AMG", "Mercedes-Benz", "Mercedes-Maybach", "Mercury", "MG",
                "MINI", "Mitsubishi", "Morgan", "MV-1", "Nash", "Nissan", "Oldsmobile", "Packard", "Pagani", "Panther",
                "Passport", "Plymouth", "Polestar", "Pontiac", "Porsche", "Puma", "Qvale", "Ram", "Renault", "Rivian",
                "Rolls-Royce", "Rover", "Saab", "Saleen", "Saturn", "Scion", "Shelby", "smart", "Sterling", "Subaru",
                "Sunbeam", "Superformance", "Suzuki", "Tesla", "Toyota", "Triumph", "TVR", "Volkswagen", "Volvo",
                "Willys");

        List<String> myList = new ArrayList<String>();

        // for (int i = 0; i < 25; i++) {
        // System.out.println(makeList.get(i));
        // myList.add(makeList.get(i));
        // }

        for (int i = 25; i < 50; i++) {
            myList.add(makeList.get(i));
        }

        /*
         * for (int i = 50; i< 75; i++)
         * {
         * myList.add(makeList.get(i));
         * }
         * 
         * 
         * for (int i = 75; i< 104; i++)
         * {
         * myList.add(makeList.get(i));
         * }
         */
        try {
            myList.remove("Acura");
            myList.remove("Alfa Romeo");
            myList.remove("Audi");
            // second batch
            myList.removeAll(Arrays.asList("De Tomaso", "Dodge", "Factory Five Racing",
                    "Ferrari", "Fiat", "Fisker", "Ford", "Freightliner", "Genesis", "Geo", "GMC", "Hino", "Honda",
                    "Hummer", "Hyundai", "Infiniti", "Innocenti", "International", "Isuzu", "Jaguar", "Jeep", "Karma",
                    "Kia", "Lamborghini", "Lancia"));
        } catch (Exception er) {
        }
        myList.forEach(make -> extracted(chromeOptions, driver, make));

        driver.quit();
        System.out.println("Done.");
    }

    /**
     * @param chromeOptions
     * @param driver
     * @param make
     */
    private static void extracted(ChromeOptions chromeOptions, WebDriver driver, String make) {
        if (!make.equals("Any Make")) {
            System.out.println(make);

            File csvFile = null;
            try {
                csvFile = createFile(make);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try (FileWriter fileWriter = new FileWriter(csvFile)) {

                String within25KmToronto = "/on/toronto/?rcp=15&rcs=0&srt=35&prx=25&prv=Ontario&loc=M3C%200E3&hprc=True&wcp=True&iosp=True&sts=New-Used&inMarket=advancedSearch";

                driver.navigate().to("https://autotrader.ca/cars/" + make + within25KmToronto);

                WebElement numResults = driver.findElement(By.id("titleCount"));
                String numResValue = numResults.getText();

                if (numResValue.equals("0")) {

                } else {

                    try {
                        fileWriter.write(
                                "Year,Make,Model,Kilometres,Body Type, Engine, Transmission, Drivetrain, Exterior Colour, Interior Colour, Passengers, Doors, Fuel Type, City, Highway,Price\n");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

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

                        lastPageLink = driver.findElements(By.className("last-page-link")).get(0);

                        // download the data
                        List<WebElement> vehicleList = driver.findElements(By.className("result-title"));
                        vehicleList.forEach(vehicle -> {
                            System.out.println(vehicle.getAttribute("href"));

                            WebDriver vehicleDriver = new ChromeDriver(chromeOptions);
                            vehicleDriver.navigate().to(vehicle.getAttribute("href"));

                            WebDriverWait w = new WebDriverWait(vehicleDriver, Duration.ofSeconds(3));
                            w.until(ExpectedConditions.presenceOfElementLocated(By.className("hero-title")));

                            String[] detailWards = vehicleDriver.findElement(By.className("hero-title")).getText()
                                    .replace("\n", " ").split(" ");
                            String year = detailWards[0];
                            String model = detailWards[2];

                            String price = vehicleDriver.findElement(By.className("hero-price")).getText()
                                    .replace("$", "")
                                    .replace(",", "");

                            String[] vehicleSpecs = pullVehicleSpecs(vehicleDriver);

                            String csvEntry = String.join(",", year, make, model, vehicleSpecs[0], vehicleSpecs[1],
                                    vehicleSpecs[2], vehicleSpecs[3],
                                    vehicleSpecs[4], vehicleSpecs[5], vehicleSpecs[6], vehicleSpecs[7], vehicleSpecs[8],
                                    vehicleSpecs[9], vehicleSpecs[10], vehicleSpecs[11], price) + "\n";

                            try {
                                fileWriter.write(csvEntry);
                                fileWriter.flush();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            System.out.println("--->" + csvEntry);
                            vehicleDriver.close();
                            vehicleDriver.quit();

                        });

                    } while (!lastPageLink.getAttribute("class").contains("disabled"));
                    fileWriter.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}