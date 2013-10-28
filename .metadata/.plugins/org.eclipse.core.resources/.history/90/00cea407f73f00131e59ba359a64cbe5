package com.eseteam9.shoppyapp.tests;

public abstract class EmulatorTestclass {
	public final String testTitle;
	private int caseCount, caseErrorCount;
	private int totalCount, totalErrorCount;
	
	public EmulatorTestclass(String testTitle) {
		this.testTitle = testTitle;
		setUp();
	}
	
	protected void start(){
		totalCount = 0;
		totalErrorCount = 0;
		System.out.println("test: =====================================");
		System.out.println("test: TEST " + testTitle);
	}
	
	protected void setUp(){}
	
	protected boolean reportAndEnd(){
		System.out.println("test: TEST END (" + (totalCount-totalErrorCount) + "\\" + totalCount + ") " + (totalErrorCount == 0 ? "OK" : "FAIL"));
		return totalErrorCount == 0;
	}
	
	protected void startCase(String testName) {
		caseCount = 0;
		caseErrorCount = 0;
		
		System.out.println("test:\tCASE " + testName);
	}
	
	protected void endCase() {
		System.out.println("test:\tCASE END (" + (caseCount-caseErrorCount) + "\\" + caseCount + ")");
		totalCount += caseCount;
		totalErrorCount += caseErrorCount;
	}
	
	protected void assertTrue(boolean condition)  {
		caseCount++;
		if (condition) {
			System.out.println("test: aOK");
		} else {
			System.out.println("test: aFAIL");
			caseErrorCount++;
		}
	}
	
	protected void trySucceeded() {
		caseCount++;
		System.out.println("test: tOK");
	}
	
	protected void tryFailed(Exception e) {
		caseCount++;
		System.out.println("test: tFAIL (" + e + ")");
		caseErrorCount++;
	}
	
	protected void print(String message) {
		System.out.println("test: MESSAGE (" + message + ")");
	}
}
