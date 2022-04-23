import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;


public class Bot {

    public static ArrayList<console> consoles = new ArrayList<console>();
    public static Website newegg = new Website();
    public static Website bestbuy = new Website();
    public static Website amazon = new Website();


    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false); //Set headless to true if you don't want a browser window to popup. WARNING: Bestbuy does not work headless.
        options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // wait up to 5 seconds for add to cart button to display

        displayMenu();
        int selection = scanner.nextInt();
        displayStores();
        int storeSelection = scanner.nextInt();

        switch (storeSelection) {
        case 1:
            if (selection == 1) {
                neweggPS5();
            }
            else if (selection == 2) {
                neweggXBOX();
            }
            else if (selection == 3) {
                neweggPS5();
                neweggXBOX();
            }
        case 2:
            if (selection == 1) {
                bestbuyPS5();
            }
            else if (selection == 2) {
                bestbuyXBOX();
            }
            else if (selection == 3) {
                bestbuyPS5();
                bestbuyXBOX();
            }
        case 3:
            if (selection == 1) {
                amazonPS5();
            }
            else if (selection == 2) {
                amazonXBOX();
            }
            else if (selection == 3) {
                amazonPS5();
                amazonXBOX();
            }
        case 4:
            if (selection == 1) {
                neweggPS5();
                bestbuyPS5();
                amazonPS5();
            }
            else if (selection == 2) {
                neweggXBOX();
                bestbuyXBOX();
                amazonXBOX();
            }
            else if (selection == 3) {
                neweggPS5();
                bestbuyPS5();
                amazonPS5();
                neweggXBOX();
                bestbuyXBOX();
                amazonXBOX();
            }
        case 5:
            if (selection == 1) {
                testconsoles();
            }
            else if (selection == 2) {
                testconsoles();
            }
            else if (selection == 3) {
                testconsoles();
            }
        default:
            System.out.println("Select 1-5");
        }






        try {
            driver.get(consoles.get(0).getUrl());
            //Look for in stock consoles
            boolean inStock = false; //inStock will always be false in order to keep bot running even if in stock console is found
            int counter = 0; // counter to send text message updates so you know the bot is still running

            do {
                for (console console : consoles) {
                    driver.get(console.getUrl());
                    Thread.sleep((long)(Math.random() * 876));
                    counter++;

                    if (console.getWebsite().equals(newegg))
                        if (driver.findElements(By.xpath("//button[@class=\"btn btn-primary btn-wide\"]")).size() > 0) {
                            Message message = Message.creator(new PhoneNumber(PHONE_NUMBER), new PhoneNumber(TWILIO_NUMBER), console.getUrl()).create();
                            System.out.println(console.getUrl());
                            System.out.println(ANSI_YELLOW + "Info " + ANSI_RESET + ": :" + ANSI_CYAN + "Adding :" + ANSI_RESET + console.getModel() + ANSI_GREEN + " to cart" + ANSI_RESET);
                            //inStock = true; // uncomment if you want the bot to stop after finding one console in stock > I recommend you leave this commented to prevent having to restart in case the console sells out fast

                        }
                        else {
                            System.out.println(ANSI_YELLOW + "Info " + ANSI_RESET + ": : " + ANSI_CYAN + console.getModel() + ANSI_RESET + ANSI_GREEN + " : : " + ANSI_RED + "OUT OF STOCK at NEWEGG");
                        }
                    else if (console.getWebsite().equals(bestbuy)) {
                        if (driver.findElements(By.xpath("//button[@class=\"btn btn-primary btn-lg btn-block btn-leading-ficon add-to-cart-button\"]")).size() > 0) {
                            Message message = Message.creator(new PhoneNumber(PHONE_NUMBER), new PhoneNumber(TWILIO_NUMBER), console.getAddToCartURL()).create();
                            driver.get(console.getAddToCartURL());
                            System.out.println(console.getAddToCartURL());
                            System.out.println(ANSI_YELLOW + "Info " + ANSI_RESET + ": :" + ANSI_CYAN + "Adding :" + ANSI_RESET + console.getModel() + ANSI_GREEN + " to cart" + ANSI_RESET);
                            //inStock = true; // uncomment if you want the bot to stop after finding one console in stock > I recommend you leave this commented to prevent having to restart in case the console sells out fast
                        }
                        else {
                            System.out.println(ANSI_YELLOW + "Info " + ANSI_RESET + ": : " + ANSI_CYAN + console.getModel() + ANSI_RESET + ANSI_GREEN + " : : " + ANSI_RED + "OUT OF STOCK at BEST BUY");
                        }

                    }
                    else if (console.getWebsite().equals(amazon)) {
                        //Thread.sleep((long) (Math.random() * 2323));
                        if (driver.findElements(By.name("submit.add-to-cart")).size() > 0) {
                            Message message = Message.creator(new PhoneNumber(PHONE_NUMBER), new PhoneNumber(TWILIO_NUMBER), console.getUrl()).create();
                            System.out.println(console.getUrl());
                            System.out.println(ANSI_YELLOW + "Info " + ANSI_RESET + ": :" + ANSI_CYAN + "Adding :" + ANSI_RESET + console.getModel() + ANSI_GREEN + " to cart" + ANSI_RESET);
                            //inStock = true; // uncomment if you want the bot to stop after finding one console in stock > I recommend you leave this commented to prevent having to restart in case the console sells out fast
                        }
                        else {
                            System.out.println(ANSI_YELLOW + "Info " + ANSI_RESET + ": : " + ANSI_CYAN + console.getModel() + ANSI_RESET + ANSI_GREEN + " : : " + ANSI_RED + "OUT OF STOCK at AMAZON");
                        }
                    }
                    if (counter == 750) {
                        Message message = Message.creator(new PhoneNumber(PHONE_NUMBER), new PhoneNumber(TWILIO_NUMBER), "No consoles found. Continuing to look.").create(); // use TWILIO_NUMBER_2 if you decide to get second twilio number for text updates
                        counter = 0;
                        System.out.println("STILL NO GPUS");
                    }

                }

            } while (!inStock);

        }
        catch (Exception exception) {
            System.out.println("Error occured - Waiting 15 secs...");
            Thread.sleep(15000);
        }
    }

    public static void displayMenu() {
        System.out.println("Welcome to consolefinder");
        System.out.println("This tool will look for instock consoles");
        System.out.println("and text you a link to purchase them. Use this tool on your");
        System.out.println("computer using a VPN, and checkout on your cellphone by following");
        System.out.println("the link provided. This will prevent you from being flagged as a bot.");
        System.out.println();
        System.out.println();
        System.out.println("Selections:");
        System.out.println("1. PS5");
        System.out.println("2. XBOX");
        System.out.println("3. Both");
    }

    public static void displayStores() {
        System.out.println("This tool searches both Newegg and Bestbuy for in stock consoles");
        System.out.println("Selections:");
        System.out.println("Selections:");
        System.out.println("1. Newegg");
        System.out.println("2. BestBuy");
        System.out.println("3. Amazon");
        System.out.println("4. All Stores");
        System.out.println("5. Test Alerts");
    }

    public static void neweggPS5() {
        // consoles.add(new console(
    }

    public static void neweggXBOX() {
        // consoles.add(new console
    }


    public static void bestbuyXBOX() {
        consoles.add(new console(
    }

    public static void bestbuyPS5() {
        consoles.add(new console(
    }


