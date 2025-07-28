package com.ruoyi.project.tool.claude.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project.tool.claude.domain.ClaudeUsage;
import com.ruoyi.project.tool.claude.service.IClaudeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Claude API 控制器
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/tool/claude/api")
public class ClaudeApiController extends BaseController
{
    @Autowired
    private IClaudeApiService claudeApiService;

    /**
     * 发送消息到Claude
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:api:send')")
    @Log(title = "Claude API 发送消息", businessType = BusinessType.OTHER)
    @PostMapping("/send")
    public AjaxResult sendMessage(@RequestParam String apiKey,
                                  @RequestParam String modelName,
                                  @RequestParam String message,
                                  @RequestParam(required = false) String systemPrompt,
                                  @RequestParam(required = false) String sessionId)
    {
        try {
            ClaudeUsage result = claudeApiService.sendMessage(apiKey, modelName, message, systemPrompt, sessionId);
            return success(result);
        } catch (Exception e) {
            return error("发送消息失败: " + e.getMessage());
        }
    }

    /**
     * 获取模型列表
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:api:models')")
    @GetMapping("/models")
    public AjaxResult getModels(@RequestParam String apiKey)
    {
        try {
            String models = claudeApiService.getModels(apiKey);
            return success(models);
        } catch (Exception e) {
            return error("获取模型列表失败: " + e.getMessage());
        }
    }

    /**
     * 验证API密钥
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:api:validate')")
    @PostMapping("/validate")
    public AjaxResult validateApiKey(@RequestParam String apiKey)
    {
        try {
            boolean isValid = claudeApiService.validateApiKey(apiKey);
            return success(isValid);
        } catch (Exception e) {
            return error("验证API密钥失败: " + e.getMessage());
        }
    }

    /**
     * 计算Token数量
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:api:calculate')")
    @PostMapping("/calculate-tokens")
    public AjaxResult calculateTokens(@RequestParam String text)
    {
        try {
            long tokens = claudeApiService.calculateTokens(text);
            return success(tokens);
        } catch (Exception e) {
            return error("计算Token数量失败: " + e.getMessage());
        }
    }

    /**
     * 计算费用
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:api:calculate')")
    @PostMapping("/calculate-cost")
    public AjaxResult calculateCost(@RequestParam long inputTokens,
                                    @RequestParam long outputTokens,
                                    @RequestParam String modelName)
    {
        try {
            double cost = claudeApiService.calculateCost(inputTokens, outputTokens, modelName);
            return success(cost);
        } catch (Exception e) {
            return error("计算费用失败: " + e.getMessage());
        }
    }
}