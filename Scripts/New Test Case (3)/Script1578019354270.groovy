import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.ParseException as ParseException
import java.text.SimpleDateFormat as SimpleDateFormat

import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver

import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String timeStamp

def Date

Date="12/03/2019 11:09 am"

if(isThisDateValid(Date)){
	KeywordUtil.markPassed('Timestamp of last available data is displayed')
}
else{
	KeywordUtil.markFailed('Timestamp of last available data is not displayed')
}


def boolean isThisDateValid(String dateToValidate) {
	if (dateToValidate == null) {
		KeywordUtil.markFailed('Date is null')
	}

	SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy hh:mm aa')

	sdf.setLenient(false)

	try {
		Date date = sdf.parse(dateToValidate)
	return true
	}
	catch (ParseException e) {
		return false
	}
}

