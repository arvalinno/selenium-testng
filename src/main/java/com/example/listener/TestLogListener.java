package com.example.listener;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayOutputStream;

public class TestLogListener implements ITestListener {
    // Store logs in memory, key = test name
    private static final Map<String, String> logStore = new HashMap<>();
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @Override
    public void onTestStart(ITestResult result) {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        saveLog(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        saveLog(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        saveLog(result);
    }

    private void saveLog(ITestResult result) {
        System.out.flush();
        System.setOut(originalOut);
        logStore.put(result.getName(), outContent.toString());
    }

    public static String getLog(String testName) {
        return logStore.getOrDefault(testName, "No logs found.");
    }
}
