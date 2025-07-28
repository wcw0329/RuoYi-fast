package com.ruoyi.project.tool.claude.service;

import com.ruoyi.project.tool.claude.domain.ClaudeUsage;

/**
 * Claude API 服务接口
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
public interface IClaudeApiService 
{
    /**
     * 发送消息到Claude API
     * 
     * @param apiKey API密钥
     * @param modelName 模型名称
     * @param message 消息内容
     * @param systemPrompt 系统提示词
     * @return API响应
     */
    ClaudeUsage sendMessage(String apiKey, String modelName, String message, String systemPrompt);

    /**
     * 发送消息到Claude API（带会话ID）
     * 
     * @param apiKey API密钥
     * @param modelName 模型名称
     * @param message 消息内容
     * @param systemPrompt 系统提示词
     * @param sessionId 会话ID
     * @return API响应
     */
    ClaudeUsage sendMessage(String apiKey, String modelName, String message, String systemPrompt, String sessionId);

    /**
     * 获取模型列表
     * 
     * @param apiKey API密钥
     * @return 模型列表
     */
    String getModels(String apiKey);

    /**
     * 验证API密钥
     * 
     * @param apiKey API密钥
     * @return 验证结果
     */
    boolean validateApiKey(String apiKey);

    /**
     * 计算Token数量
     * 
     * @param text 文本内容
     * @return Token数量
     */
    long calculateTokens(String text);

    /**
     * 计算费用
     * 
     * @param inputTokens 输入Token数
     * @param outputTokens 输出Token数
     * @param modelName 模型名称
     * @return 费用（美元）
     */
    double calculateCost(long inputTokens, long outputTokens, String modelName);
}