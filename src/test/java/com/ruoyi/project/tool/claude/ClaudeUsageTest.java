package com.ruoyi.project.tool.claude;

import com.ruoyi.project.tool.claude.domain.ClaudeUsage;
import com.ruoyi.project.tool.claude.service.IClaudeApiService;
import com.ruoyi.project.tool.claude.service.IClaudeUsageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Claude API 使用工具测试类
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@SpringBootTest
public class ClaudeUsageTest {

    @Autowired
    private IClaudeUsageService claudeUsageService;

    @Autowired
    private IClaudeApiService claudeApiService;

    /**
     * 测试记录API调用
     */
    @Test
    public void testRecordApiCall() {
        ClaudeUsage usage = new ClaudeUsage();
        usage.setUserId(1L);
        usage.setUserName("admin");
        usage.setApiKey("sk-***test");
        usage.setModelName("claude-3-5-sonnet-20241022");
        usage.setRequestType("CHAT");
        usage.setInputTokens(100L);
        usage.setOutputTokens(50L);
        usage.setTotalTokens(150L);
        usage.setInputCharacters(400L);
        usage.setOutputCharacters(200L);
        usage.setTotalCharacters(600L);
        usage.setCost(new BigDecimal("0.00045"));
        usage.setStatus("SUCCESS");
        usage.setRequestTime(new Date());
        usage.setResponseTime(new Date());
        usage.setResponseTimeMs(1500L);
        usage.setIpAddress("127.0.0.1");
        usage.setUserAgent("Mozilla/5.0");
        usage.setSessionId("session-123");
        usage.setProjectName("测试项目");
        usage.setRemark("测试记录");

        int result = claudeUsageService.recordApiCall(usage);
        System.out.println("记录API调用结果: " + result);
    }

    /**
     * 测试Token计算
     */
    @Test
    public void testCalculateTokens() {
        String text = "这是一个测试文本，用于计算Token数量。This is a test text for token calculation.";
        long tokens = claudeApiService.calculateTokens(text);
        System.out.println("文本: " + text);
        System.out.println("Token数量: " + tokens);
    }

    /**
     * 测试费用计算
     */
    @Test
    public void testCalculateCost() {
        long inputTokens = 1000L;
        long outputTokens = 500L;
        String modelName = "claude-3-5-sonnet-20241022";
        
        double cost = claudeApiService.calculateCost(inputTokens, outputTokens, modelName);
        System.out.println("模型: " + modelName);
        System.out.println("输入Token: " + inputTokens);
        System.out.println("输出Token: " + outputTokens);
        System.out.println("费用: $" + cost);
    }

    /**
     * 测试API密钥验证
     */
    @Test
    public void testValidateApiKey() {
        // 注意：这里使用测试密钥，实际使用时需要替换为真实的API密钥
        String apiKey = "sk-test-key";
        boolean isValid = claudeApiService.validateApiKey(apiKey);
        System.out.println("API密钥验证结果: " + isValid);
    }

    /**
     * 测试获取用户使用统计
     */
    @Test
    public void testGetUserStats() {
        Long userId = 1L;
        ClaudeUsage stats = claudeUsageService.selectUserUsageStats(userId);
        if (stats != null) {
            System.out.println("用户ID: " + userId);
            System.out.println("总输入Token: " + stats.getInputTokens());
            System.out.println("总输出Token: " + stats.getOutputTokens());
            System.out.println("总费用: $" + stats.getCost());
        } else {
            System.out.println("用户 " + userId + " 暂无使用记录");
        }
    }

    /**
     * 测试获取总使用统计
     */
    @Test
    public void testGetTotalStats() {
        ClaudeUsage totalStats = claudeUsageService.selectTotalUsageStats();
        if (totalStats != null) {
            System.out.println("系统总使用统计:");
            System.out.println("总输入Token: " + totalStats.getInputTokens());
            System.out.println("总输出Token: " + totalStats.getOutputTokens());
            System.out.println("总费用: $" + totalStats.getCost());
        } else {
            System.out.println("暂无使用记录");
        }
    }

    /**
     * 测试按模型统计
     */
    @Test
    public void testGetModelStats() {
        var modelStats = claudeUsageService.selectUsageStatsByModel();
        System.out.println("按模型统计:");
        for (ClaudeUsage stats : modelStats) {
            System.out.println("模型: " + stats.getModelName());
            System.out.println("  输入Token: " + stats.getInputTokens());
            System.out.println("  输出Token: " + stats.getOutputTokens());
            System.out.println("  费用: $" + stats.getCost());
            System.out.println("---");
        }
    }

    /**
     * 测试发送消息到Claude（需要真实API密钥）
     */
    @Test
    public void testSendMessage() {
        // 注意：这个测试需要真实的API密钥才能运行
        // 在实际使用时，请替换为您的真实API密钥
        String apiKey = "your-api-key-here";
        String modelName = "claude-3-5-sonnet-20241022";
        String message = "你好，请介绍一下你自己。";
        String systemPrompt = "你是一个有用的AI助手。";
        
        try {
            ClaudeUsage result = claudeApiService.sendMessage(apiKey, modelName, message, systemPrompt);
            System.out.println("API调用结果:");
            System.out.println("状态: " + result.getStatus());
            System.out.println("输入Token: " + result.getInputTokens());
            System.out.println("输出Token: " + result.getOutputTokens());
            System.out.println("费用: $" + result.getCost());
            System.out.println("响应时间: " + result.getResponseTimeMs() + "ms");
        } catch (Exception e) {
            System.out.println("API调用失败: " + e.getMessage());
        }
    }
}