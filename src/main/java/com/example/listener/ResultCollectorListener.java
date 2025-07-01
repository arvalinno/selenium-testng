package com.example.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.*;

public class ResultCollectorListener implements ITestListener {

    public static final List<Map<String, String>> passedTests = new ArrayList<>();
    public static final List<Map<String, String>> failedTests = new ArrayList<>();
    public static final List<Map<String, String>> skippedTests = new ArrayList<>();

    @Override
    public void onStart(ITestContext context) {
        passedTests.clear();
        failedTests.clear();
        skippedTests.clear();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests.add(Map.of(
            "name", result.getName(),
            "class", result.getTestClass().getName(),
            "status", "PASSED"
        ));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests.add(Map.of(
            "name", result.getName(),
            "class", result.getTestClass().getName(),
            "status", "FAILED"
        ));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests.add(Map.of(
            "name", result.getName(),
            "class", result.getTestClass().getName(),
            "status", "SKIPPED"
        ));
    }

    public static Map<String, List<Map<String, String>>> getSummary() {
        return Map.of(
            "passed", passedTests,
            "failed", failedTests,
            "skipped", skippedTests
        );
    }
}
