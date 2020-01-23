package utilskat
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.math.RoundingMode;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import javax.imageio.ImageIO

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable
import java.awt.image.BufferedImage
import MobileBuiltInKeywords as Mobile
import WSBuiltInKeywords as WS
import WebUiBuiltInKeywords as WebUI
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot as Screenshot
import ru.yandex.qatools.ashot.comparison.ImageDiff as ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer as ImageDiffer
import ru.yandex.qatools.ashot.comparison.ImageMarkupPolicy as ImageMarkupPolicy
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider
import ru.yandex.qatools.ashot.cropper.indent.BlurFilter
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper
import ru.yandex.qatools.ashot.shooting.ShootingStrategies

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.exception.WebElementNotFoundException

public class ScreenShot {

	Screenshot screenshot;
	WebDriver driver;
	WebElement webElement;
	BufferedImage expImage;
	BufferedImage actImage;


	@Keyword
	def takeScreenshotmethod(TestObject object, String imageName) {
		WebElement element=WebUiCommonHelper.findWebElement(object,20)
		WebDriver webDriver = DriverFactory.getWebDriver()
		Screenshot screens = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(webDriver,element);
		ImageIO.write(screens.getImage(), "PNG", new File(System.getProperty("user.dir")+"\\Images\\"+imageName+".png"))
	}

	@Keyword
	def takeScreenshotmethod2(TestObject object, String imageName) {
		WebElement element=WebUiCommonHelper.findWebElement(object,20)
		WebDriver webDriver = DriverFactory.getWebDriver()
		Screenshot screens = new AShot()
				.shootingStrategy(ShootingStrategies.viewportPasting(100))
				.takeScreenshot(webDriver);
		ImageIO.write(screens.getImage(), "PNG", new File(System.getProperty("user.dir")+"\\Images\\"+imageName+".png"))
	}

	@Keyword
	def takeScreenshotmethod3(TestObject object, String imageName) {
		WebElement element=WebUiCommonHelper.findWebElement(object,20)
		WebDriver webDriver = DriverFactory.getWebDriver()
		Screenshot screens = new AShot().imageCropper(new IndentCropper().addIndentFilter(blur())).takeScreenshot(driver, element);
		ImageIO.write(screens.getImage(), "PNG", new File(System.getProperty("user.dir")+"\\Images\\"+imageName+".png"))
	}

	@Keyword
	def compareImages(String actImageScreen, String expImageScreen){
		driver = DriverFactory.getWebDriver();
		expImage=ImageIO.read(new File(System.getProperty("user.dir") +"//Images//"+expImageScreen+".png"));
		actImage=ImageIO.read(new File(System.getProperty("user.dir") +"//Images//"+actImageScreen+".png"));
		ImageDiffer imgDiffer=new ImageDiffer();
		ImageDiff imgDiff=imgDiffer.makeDiff(expImage, actImage);

		int heigh = imgDiff.getDiffImage().getHeight()
		int width = imgDiff.getDiffImage().getWidth()
		int diffPoints = imgDiff.getDiffSize()
		double totalPixels = width * heigh
		double result = (diffPoints / totalPixels) * 100
		double resultPrecised = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP).doubleValue()
		println("Difference Percentage = " + resultPrecised + "%")
		//		println("Screen difference: "+imgDiff.diffSize)
		return imgDiff.hasDiff();
	}
}




/**
 * Refresh browser
 */
@Keyword
def refreshBrowser() {
	KeywordUtil.logInfo("Refreshing")
	WebDriver webDriver = DriverFactory.getWebDriver()
	webDriver.navigate().refresh()
	KeywordUtil.markPassed("Refresh successfully")
}

/**
 * Click element
 * @param to Katalon test object
 */
@Keyword
def clickElement(TestObject to) {
	try {
		WebElement element = WebUiBuiltInKeywords.findWebElement(to);
		KeywordUtil.logInfo("Clicking element")
		element.click()
		KeywordUtil.markPassed("Element has been clicked")
	} catch (WebElementNotFoundException e) {
		KeywordUtil.markFailed("Element not found")
	} catch (Exception e) {
		KeywordUtil.markFailed("Fail to click on element")
	}
}

/**
 * Get all rows of HTML table
 * @param table Katalon test object represent for HTML table
 * @param outerTagName outer tag name of TR tag, usually is TBODY
 * @return All rows inside HTML table
 */
@Keyword
def List<WebElement> getHtmlTableRows(TestObject table, String outerTagName) {
	WebElement mailList = WebUiBuiltInKeywords.findWebElement(table)
	List<WebElement> selectedRows = mailList.findElements(By.xpath("./" + outerTagName + "/tr"))
	return selectedRows
}
