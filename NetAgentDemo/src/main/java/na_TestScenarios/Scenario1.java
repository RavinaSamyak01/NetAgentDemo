package na_TestScenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;
import netAgent_BasePackage.SendEmail;

public class Scenario1 extends BaseInit {

	@Test
	public void unAssignANyRecord() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		// JavascriptExecutor js = (JavascriptExecutor) Driver;
		// Actions act = new Actions(Driver);

		logger.info("=======TestScenario 1 Test Start=======");
		msg.append("=======TestScenario 1 Test Start=======" + "\n\n");
		
		
		logger.info("To verify 'No Record Found' when no Jobs is/are assigned");
		msg.append("To verify 'No Record Found' when no Jobs is/are assigned" + "\n\n");
		
		// --LogOut from current courier
		logOut();

		// --Login with the Courier who doesnt have any Job assigned
		LoginNoRecord();

		// --Go to TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		isElementPresent("Operations_id").click();
		logger.info("Clicked on Operations");

		isElementPresent("TaskLog_linkText").click();
		logger.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

		getScreenshot(Driver, "TaskLog_Operations");

		// --Check the Record
		WebElement NoData = isElementPresent("NoData_className");
		if (NoData.isDisplayed()) {

			// --Get Total Jobs
			String TotalJobs = isElementPresent("TotalJob_xpath").getText();
			logger.info("Total Jobs=" + TotalJobs);
			msg.append("Total Jobs=" + TotalJobs + "\n\n");
			System.out.println("Data is not present related search parameter=PASS");
			logger.info("Data is not present related search parameter=PASS");
			msg.append("Data is not present related search parameter=PASS" + "\n\n");

		} else {
			// --Get Total Jobs
			String TotalJobs = isElementPresent("TotalJob_xpath").getText();
			logger.info("Total Jobs=" + TotalJobs);
			msg.append("Total Jobs=" + TotalJobs + "\n\n");

			System.out.println("Data is present related search parameter=FAIL");
			logger.info("Data is present related search parameter=FAIL");
			msg.append("Data is present related search parameter=FAIL" + "\n\n");

		}

		// --Refresh the App
		refreshApp();

		// --LogOut from current courier
		logOut();

		// --Login with Demo COurier
		Login();

		logger.info("=======TestScenario 1 Test End=======");
		msg.append("=======TestScenario 1 Test End=======" + "\n\n");
	}

	public void LoginNoRecord() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 40);

		String Env = storage.getProperty("Env");
		String baseUrl = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			baseUrl = storage.getProperty("PREProdURL");
			Driver.get(baseUrl);
			logger.info("Url opened");
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
				getScreenshot(Driver, "LoginPage");
				String UserName = storage.getProperty("PreProdNewCourUserName");
				String password = storage.getProperty("PreProdNewCourPass");
				// Enter User_name and Password and click on Login
				isElementPresent("UserName_id").clear();
				isElementPresent("UserName_id").sendKeys(UserName);
				isElementPresent("Password_id").clear();
				isElementPresent("Password_id").sendKeys(password);
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NA_Screenshot\\LoginIssue.png";
				String subject = "Selenium Automation Script: " + Env + " NetAgent Portal";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

					/*
					 * SendEmail.sendMail(
					 * "ravina.prajapati@samyak.com,asharma@samyak.com, parth.doshi@samyak.com, saurabh.jain@samyak.com, himanshu.dholakia@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					SendEmail.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

					/*
					 * SendEmail.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */
					// SendEmail.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
					// ,parth.doshi@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
				}
			}

		} else if (Env.equalsIgnoreCase("STG")) {
			baseUrl = storage.getProperty("STGURL");
			Driver.get(baseUrl);
			logger.info("Url opened");
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
				getScreenshot(Driver, "LoginPage");
				String UserName = storage.getProperty("STGNewCourUserName");
				String password = storage.getProperty("STGNewCourPassword");
				// Enter User_name and Password and click on Login
				isElementPresent("UserName_id").clear();
				isElementPresent("UserName_id").sendKeys(UserName);
				isElementPresent("Password_id").clear();
				isElementPresent("Password_id").sendKeys(password);
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NA_Screenshot\\LoginIssue.png";
				String subject = "Selenium Automation Script: " + Env + " NetAgent Portal";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

					/*
					 * SendEmail.sendMail(
					 * "ravina.prajapati@samyak.com,asharma@samyak.com, parth.doshi@samyak.com, saurabh.jain@samyak.com, himanshu.dholakia@samyak.com"
					 * , subject, msg.toString(), File);
					 */
					SendEmail.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

					/*
					 * SendEmail.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */
					// SendEmail.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
					// ,parth.doshi@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
				}
			}

		} else if (Env.equalsIgnoreCase("DEV")) {
			baseUrl = storage.getProperty("DEVURL");
			Driver.get(baseUrl);
			logger.info("Url opened");
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
				getScreenshot(Driver, "LoginPage");
				String UserName = storage.getProperty("DEVUserName");
				String password = storage.getProperty("DEVPassword");
				// Enter User_name and Password and click on Login
				isElementPresent("UserName_id").clear();
				isElementPresent("UserName_id").sendKeys(UserName);
				isElementPresent("Password_id").clear();
				isElementPresent("Password_id").sendKeys(password);
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NA_Screenshot\\LoginIssue.png";
				String subject = "Selenium Automation Script: " + Env + " NetAgent Portal";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

					/*
					 * SendEmail.sendMail(
					 * "ravina.prajapati@samyak.com,asharma@samyak.com, parth.doshi@samyak.com, saurabh.jain@samyak.com, himanshu.dholakia@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					SendEmail.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

					/*
					 * SendEmail.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */
					// SendEmail.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
					// ,parth.doshi@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}

		isElementPresent("Login_id").click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		Thread.sleep(2000);
		getScreenshot(Driver, "HomeScreen");

	}

}
