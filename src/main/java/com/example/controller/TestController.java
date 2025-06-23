package com.example.controller;

import com.example.listener.TestLogListener;
import com.example.runner.SauceDemoTestRunner;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class TestController {

    private final Map<String, String> testResults = new ConcurrentHashMap<>();
    private final Map<String, String> testLogs = new ConcurrentHashMap<>();

    @GetMapping("/test-ui")
    public String showTestPage(Model model) {
        return "test-ui";
    }

    @PostMapping("/start-test")
    public String startTest(Model model) {
        System.out.println("heererererere");
        String testId = UUID.randomUUID().toString();
        testResults.put(testId, "PENDING");

        new Thread(() -> {
        System.out.println("heererererere1");

            try {
                boolean result = SauceDemoTestRunner.run();
                testResults.put(testId, result ? "PASS" : "FAIL");
            } catch (Exception e) {
                testResults.put(testId, "FAIL");
            }
        }).start();

        model.addAttribute("testId", testId);
        return "status";
    }

    @GetMapping("/test-result")
    @ResponseBody
    public Map<String, String> getTestResult(@RequestParam String id) {
        String result = testResults.getOrDefault(id, "UNKNOWN");
        String log = testLogs.getOrDefault(id, "");

        return Map.of("status", result, "log", log);
    }

    @RestController
    @RequestMapping("/test-log")
    public class LogController {

        @GetMapping("/{testName}")
        public ResponseEntity<String> getLog(@PathVariable String testName) {
            return ResponseEntity.ok(TestLogListener.getLog(testName));
        }
    }
}
