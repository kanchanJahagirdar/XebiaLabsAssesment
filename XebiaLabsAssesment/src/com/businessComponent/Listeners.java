package com.businessComponent;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


	public class Listeners extends ShoppingBol implements ITestListener {

		@Override
		public void onTestStart(ITestResult result) {
			// TODO Auto-generated method stub
		      System.out.println("Test has started running:"  + result.getMethod().getMethodName() + " at:" + result.getStartMillis());
			
		}

		@Override
		public void onTestSuccess(ITestResult result) {
			// TODO Auto-generated method stub
			System.out.println("The name of the testcase passed is :"+result.getName());
			
		}

		@Override
		public void onTestFailure(ITestResult result) {
			// TODO Auto-generated method stub
			System.out.println("***** Error "+result.getName()+" test has failed *****");
			Screenshot();
		}     
	    

		@Override
		public void onTestSkipped(ITestResult result) {
			// TODO Auto-generated method stub
			System.out.println("The name of the testcase Skipped is :"+result.getName());
		}

		@Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStart(ITestContext context) {
			// TODO Auto-generated method stub
			//WebDriver driver=new ChromeDriver();
		}

		@Override
		public void onFinish(ITestContext context) {
			// TODO Auto-generated method stub
			
		}



}
