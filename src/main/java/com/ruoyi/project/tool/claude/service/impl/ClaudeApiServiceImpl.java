package com.ruoyi.project.tool.claude.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.tool.claude.domain.ClaudeUsage;
import com.ruoyi.project.tool.claude.service.IClaudeApiService;
import com.ruoyi.project.tool.claude.service.IClaudeUsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Claude API 服务实现类
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@Service
public class ClaudeApiServiceImpl implements IClaudeApiService 
{
    private static final Logger log = LoggerFactory.getLogger(ClaudeApiServiceImpl.class);

    @Autowired
    private IClaudeUsageService claudeUsageService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${claude.api.base-url:https://api.anthropic.com}")
    private String baseUrl;

    @Value("${claude.api.version:v1}")
    private String apiVersion;

    // Claude模型定价（每1000个token的美元价格）
    private static final Map<String, Map<String, Double>> MODEL_PRICING = new HashMap<>();
    
    static {
        // Claude 3.5 Sonnet
        Map<String, Double> sonnetPricing = new HashMap<>();
        sonnetPricing.put("input", 3.0); // $3.00 per 1M input tokens
        sonnetPricing.put("output", 15.0); // $15.00 per 1M output tokens
        MODEL_PRICING.put("claude-3-5-sonnet-20241022", sonnetPricing);
        
        // Claude 3.5 Haiku
        Map<String, Double> haikuPricing = new HashMap<>();
        haikuPricing.put("input", 0.25); // $0.25 per 1M input tokens
        haikuPricing.put("output", 1.25); // $1.25 per 1M output tokens
        MODEL_PRICING.put("claude-3-5-haiku-20241022", haikuPricing);
        
        // Claude 3 Opus
        Map<String, Double> opusPricing = new HashMap<>();
        opusPricing.put("input", 15.0); // $15.00 per 1M input tokens
        opusPricing.put("output", 75.0); // $75.00 per 1M output tokens
        MODEL_PRICING.put("claude-3-opus-20240229", opusPricing);
        
        // Claude 3 Sonnet
        Map<String, Double> sonnet3Pricing = new HashMap<>();
        sonnet3Pricing.put("input", 3.0); // $3.00 per 1M input tokens
        sonnet3Pricing.put("output", 15.0); // $15.00 per 1M output tokens
        MODEL_PRICING.put("claude-3-sonnet-20240229", sonnet3Pricing);
        
        // Claude 3 Haiku
        Map<String, Double> haiku3Pricing = new HashMap<>();
        haiku3Pricing.put("input", 0.25); // $0.25 per 1M input tokens
        haiku3Pricing.put("output", 1.25); // $1.25 per 1M output tokens
        MODEL_PRICING.put("claude-3-haiku-20240307", haiku3Pricing);
    }

    @Override
    public ClaudeUsage sendMessage(String apiKey, String modelName, String message, String systemPrompt) {
        return sendMessage(apiKey, modelName, message, systemPrompt, null);
    }

    @Override
    public ClaudeUsage sendMessage(String apiKey, String modelName, String message, String systemPrompt, String sessionId) {
        ClaudeUsage usage = new ClaudeUsage();
        Date requestTime = new Date();
        long startTime = System.currentTimeMillis();
        
        try {
            // 设置基本信息
            usage.setApiKey(maskApiKey(apiKey));
            usage.setModelName(modelName);
            usage.setRequestType("CHAT");
            usage.setRequestTime(requestTime);
            usage.setSessionId(sessionId);
            usage.setUserId(SecurityUtils.getUserId());
            usage.setUserName(SecurityUtils.getUsername());
            
            // 计算输入Token数
            long inputTokens = calculateTokens(message);
            if (StringUtils.isNotEmpty(systemPrompt)) {
                inputTokens += calculateTokens(systemPrompt);
            }
            usage.setInputTokens(inputTokens);
            usage.setInputCharacters(message.length());
            if (StringUtils.isNotEmpty(systemPrompt)) {
                usage.setInputCharacters(usage.getInputCharacters() + systemPrompt.length());
            }
            
            // 构建请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", modelName);
            requestBody.put("max_tokens", 4096);
            
            JSONArray messages = new JSONArray();
            if (StringUtils.isNotEmpty(systemPrompt)) {
                JSONObject systemMessage = new JSONObject();
                systemMessage.put("role", "system");
                systemMessage.put("content", systemPrompt);
                messages.add(systemMessage);
            }
            
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", message);
            messages.add(userMessage);
            
            requestBody.put("messages", messages);
            
            // 发送HTTP请求
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", apiKey);
            headers.set("anthropic-version", apiVersion);
            
            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
            String url = baseUrl + "/v1/messages";
            
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            
            // 处理响应
            long endTime = System.currentTimeMillis();
            usage.setResponseTime(new Date());
            usage.setResponseTimeMs(endTime - startTime);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseJson = JSON.parseObject(response.getBody());
                JSONObject content = responseJson.getJSONArray("content").getJSONObject(0);
                String responseText = content.getString("text");
                
                // 计算输出Token数和费用
                long outputTokens = responseJson.getJSONObject("usage").getLong("output_tokens");
                usage.setOutputTokens(outputTokens);
                usage.setOutputCharacters(responseText.length());
                usage.setTotalTokens(usage.getInputTokens() + outputTokens);
                usage.setTotalCharacters(usage.getInputCharacters() + usage.getOutputCharacters());
                
                double cost = calculateCost(usage.getInputTokens(), outputTokens, modelName);
                usage.setCost(BigDecimal.valueOf(cost));
                usage.setStatus("SUCCESS");
                
                log.info("Claude API调用成功 - 模型: {}, 输入Token: {}, 输出Token: {}, 费用: ${}", 
                    modelName, usage.getInputTokens(), outputTokens, cost);
            } else {
                usage.setStatus("ERROR");
                usage.setErrorMessage("HTTP状态码: " + response.getStatusCode());
                log.error("Claude API调用失败 - 状态码: {}", response.getStatusCode());
            }
            
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            usage.setResponseTime(new Date());
            usage.setResponseTimeMs(endTime - startTime);
            usage.setStatus("ERROR");
            usage.setErrorMessage(e.getMessage());
            log.error("Claude API调用异常", e);
        }
        
        // 记录使用情况
        claudeUsageService.recordApiCall(usage);
        
        return usage;
    }

    @Override
    public String getModels(String apiKey) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.set("anthropic-version", apiVersion);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = baseUrl + "/v1/models";
            
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("获取模型列表失败", e);
        }
        return null;
    }

    @Override
    public boolean validateApiKey(String apiKey) {
        try {
            String models = getModels(apiKey);
            return models != null && !models.isEmpty();
        } catch (Exception e) {
            log.error("验证API密钥失败", e);
            return false;
        }
    }

    @Override
    public long calculateTokens(String text) {
        if (StringUtils.isEmpty(text)) {
            return 0;
        }
        
        // 简单的Token计算（实际应该使用Claude的tokenizer）
        // 这里使用一个近似值：1个token约等于4个字符
        return Math.max(1, text.length() / 4);
    }

    @Override
    public double calculateCost(long inputTokens, long outputTokens, String modelName) {
        Map<String, Double> pricing = MODEL_PRICING.get(modelName);
        if (pricing == null) {
            // 使用默认定价
            pricing = MODEL_PRICING.get("claude-3-5-sonnet-20241022");
        }
        
        double inputCost = (inputTokens / 1000000.0) * pricing.get("input");
        double outputCost = (outputTokens / 1000000.0) * pricing.get("output");
        
        return inputCost + outputCost;
    }
    
    /**
     * 掩码API密钥（只显示前4位和后4位）
     */
    private String maskApiKey(String apiKey) {
        if (StringUtils.isEmpty(apiKey) || apiKey.length() <= 8) {
            return "***";
        }
        return apiKey.substring(0, 4) + "***" + apiKey.substring(apiKey.length() - 4);
    }
}