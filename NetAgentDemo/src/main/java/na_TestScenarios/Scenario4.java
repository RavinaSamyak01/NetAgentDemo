package na_TestScenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class Scenario4 extends BaseInit {

	@Test
	public void searchAndRejectJob() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=======TestScenario 4 Test Start=======");
		msg.append("=======TestScenario 4 Test Start=======" + "\n\n");

		logger.info("To verify 'Total Job=0' after Reject the job from NetAgent.");
		msg.append("To verify 'Total Job=0' after Reject the job from NetAgent." + "\n\n");

		// --Go to TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		isElementPresent("Operations_id").click();
		logger.info("Clicked on Operations");

		isElementPresent("TaskLog_linkText").click();
		logger.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

		getScreenshot(Driver, "TaskLog_Operations");

		int rowNum = getTotalRow("OrderProcessing");
		logger.info("total No of Rows=" + rowNum);

		int colNum = getTotalCol("OrderProcessing");
		logger.info("total No of Columns=" + colNum);

		// --Search with PickUPID
		for (int row1 = 1; row1 < rowNum; row1++) {
			// --Search with PickUP ID
			String ServiceID = getData("OrderProcessing", row1, 0);
			logger.info("ServiceID is==" + ServiceID);
			msg.append("ServiceID is==" + ServiceID + "\n");

			String PUID = getData("OrderProcessing", row1, 1);
			logger.info("PickUpID is==" + PUID);
			msg.append("PickUpID is==" + PUID + "\n");

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\"][contains(@class,'active')]")));
				logger.info("Operation tab is already selected");

			} catch (Exception Operation) {
				logger.info("Operation tab is not selected");
				WebElement OpTab = isElementPresent("TLOperationTab_xpath");
				wait.until(ExpectedConditions.visibilityOf(OpTab));
				act.moveToElement(OpTab).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(OpTab));
				js.executeScript("arguments[0].click();", OpTab);
				logger.info("Click on Operation Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
				isElementPresent("TLOSearch_id").clear();
				logger.info("Clear search input");
				isElementPresent("TLOSearch_id").sendKeys(PUID);
				logger.info("Enter PickUpID in Search input");
				WebElement OPSearch = isElementPresent("TLOSearchBTN_id");
				act.moveToElement(OPSearch).build().perform();
				js.executeScript("arguments[0].click();", OPSearch);
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				WebElement PickuPBox = Driver.findElement(By.xpath("//*[contains(@class,'pickupbx')]"));
				if (PickuPBox.isDisplayed()) {
					logger.info("Searched Job is displayed in edit mode");
					getScreenshot(Driver, "LOCOrderEditor_" + PUID);

					// --Click on Reject Button
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lnkRejAlert")));
					WebElement RejectBTN = isElementPresent("TLEORejectBtn_id");
					act.moveToElement(RejectBTN).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(RejectBTN));
					js.executeScript("arguments[0].click();", RejectBTN);
					logger.info("Clicked on Reject button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					// --Get Total Jobs
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					String TotalJobs = isElementPresent("TotalJob_xpath").getText().trim();
					int TotalJob = Integer.parseInt(TotalJobs);
					logger.info("Total Jobs=" + TotalJobs);
					msg.append("Total Jobs=" + TotalJobs + "\n\n");

					if (TotalJob == 0 && NoData.isDisplayed()) {
						System.out.println("Job is rejected and not displayed in TaskLog=PASS");
						logger.info("Job is rejected and not displayed in TaskLog=PASS");
						msg.append("Job is rejected and not displayed in TaskLog=PASS" + "\n");

					} else {
						logger.info("Job is not rejected and displayed in TaskLog=FAIL");
						msg.append("Job is not rejected and displayed in TaskLog=FAIL" + "\n");

					}

				}
			} catch (Exception NoData1) {
				logger.error(NoData1);
				getScreenshot(Driver, "NoData1_LOC_Error");

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is not exist with the search parameters");
						msg.append("Job is not exist with the search parameters" + "\n");

					}
				} catch (Exception OnBoard) {
					logger.error(OnBoard);
					getScreenshot(Driver, "OnBoard_LOC_Error");

					String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");
					logger.info("Issue in Order stage==" + Orderstage);
					msg.append("Issue in Order stage==" + Orderstage + "\n");
					getScreenshot(Driver, "LOCStageIssue_" + Orderstage);

				}
			}
		}

		// --Refresh the App
		refreshApp();

		logger.info("=======TestScenario 4 Test End=======");
		msg.append("=======TestScenario 4 Test End=======" + "\n\n");
	}

}
