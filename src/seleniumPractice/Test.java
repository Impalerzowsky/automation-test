import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {

    private static ChromeDriver driver;

    public static void main(String args[]) {
        try{
            //Configure Selenium or Cypress and make the following workflow:
            openBrowser();
            simpleWait();

            //Open browser in http://localhost:3000/shows
            navigateToURL("http://localhost:3000/shows");
            simpleWait();

            //Enter a text in search box with text batman
            enterText("//input[@name='search']","batman");
            simpleWait();

            //Press button search
            clickOnItem("//button[@class='btn waves-effect waves-light']");
            simpleWait();

            //Navigate to the url that is show in second card of results
            clickOnItem("(//*[contains(text(),'URL')])[2]");
            simpleWait();

            //Navigate back using browser features
            navigateBackUsingBrowser();
            simpleWait();

            //Change css background color to #4a148c to card with title Batman Unlimited
            changeCSSBackgroundColor("//*[contains(text(),'Batman Unlimited')]/ancestor::div[@class = 'card-content white-text']");
            simpleWait();

            //Press back button
            clickOnItem("//a[contains(text(),'Back')]");
            simpleWait();

            //Make sure that input for search is empty
            validateEmptySearchBox("//input[@name='search']");
            simpleWait();

            //Close driver
            tearDownDriver();

        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private static void openBrowser(){
        String strCurrentDir = System.getProperty("user.dir");
        String strDriverPath = strCurrentDir + "\\lib\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", strDriverPath);
        driver = new ChromeDriver();
    }

    private static void navigateToURL(String webpage){
        driver.get(webpage);
    }

    private static void enterText(String locator, String text){
        WebElement element = driver.findElement(By.xpath(locator));
        element.sendKeys(text);
    }

    private static void clickOnItem(String locator){
        WebElement element = driver.findElement(By.xpath(locator));
        element.click();
    }

    private static void navigateBackUsingBrowser(){
        driver.navigate().back();
    }

    private static void changeCSSBackgroundColor(String locator){
        WebElement element = driver.findElement(By.xpath(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, "style", "background-color:#4a148c");

    }

    private static boolean validateEmptySearchBox(String locator){
        WebElement element = driver.findElement(By.xpath(locator));
        String textInTheBox = element.getAttribute("value");
        if(textInTheBox.equals("")){
            System.out.println("The search box is empty - test complete");
            return true;
        }else{
            System.out.println("The search box isn't empty - test fail");
            return false;
        }
    }

    private static void simpleWait(){
        try{
            Thread.sleep(750);
        }catch(Exception e){
            System.out.println("Error on simpleWait function.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private static void tearDownDriver(){
        driver.quit();
    }

}