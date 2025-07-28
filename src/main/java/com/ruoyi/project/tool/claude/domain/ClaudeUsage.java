package com.ruoyi.project.tool.claude.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Claude API 使用记录对象 claude_usage
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
public class ClaudeUsage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** API Key */
    @Excel(name = "API Key")
    private String apiKey;

    /** 模型名称 */
    @Excel(name = "模型名称")
    private String modelName;

    /** 请求类型 */
    @Excel(name = "请求类型")
    private String requestType;

    /** 输入Token数 */
    @Excel(name = "输入Token数")
    private Long inputTokens;

    /** 输出Token数 */
    @Excel(name = "输出Token数")
    private Long outputTokens;

    /** 总Token数 */
    @Excel(name = "总Token数")
    private Long totalTokens;

    /** 输入字符数 */
    @Excel(name = "输入字符数")
    private Long inputCharacters;

    /** 输出字符数 */
    @Excel(name = "输出字符数")
    private Long outputCharacters;

    /** 总字符数 */
    @Excel(name = "总字符数")
    private Long totalCharacters;

    /** 费用（美元） */
    @Excel(name = "费用（美元）")
    private BigDecimal cost;

    /** 请求状态 */
    @Excel(name = "请求状态")
    private String status;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errorMessage;

    /** 请求时间 */
    @Excel(name = "请求时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

    /** 响应时间 */
    @Excel(name = "响应时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date responseTime;

    /** 响应时长（毫秒） */
    @Excel(name = "响应时长（毫秒）")
    private Long responseTimeMs;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ipAddress;

    /** 用户代理 */
    @Excel(name = "用户代理")
    private String userAgent;

    /** 会话ID */
    @Excel(name = "会话ID")
    private String sessionId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setApiKey(String apiKey) 
    {
        this.apiKey = apiKey;
    }

    public String getApiKey() 
    {
        return apiKey;
    }

    public void setModelName(String modelName) 
    {
        this.modelName = modelName;
    }

    public String getModelName() 
    {
        return modelName;
    }

    public void setRequestType(String requestType) 
    {
        this.requestType = requestType;
    }

    public String getRequestType() 
    {
        return requestType;
    }

    public void setInputTokens(Long inputTokens) 
    {
        this.inputTokens = inputTokens;
    }

    public Long getInputTokens() 
    {
        return inputTokens;
    }

    public void setOutputTokens(Long outputTokens) 
    {
        this.outputTokens = outputTokens;
    }

    public Long getOutputTokens() 
    {
        return outputTokens;
    }

    public void setTotalTokens(Long totalTokens) 
    {
        this.totalTokens = totalTokens;
    }

    public Long getTotalTokens() 
    {
        return totalTokens;
    }

    public void setInputCharacters(Long inputCharacters) 
    {
        this.inputCharacters = inputCharacters;
    }

    public Long getInputCharacters() 
    {
        return inputCharacters;
    }

    public void setOutputCharacters(Long outputCharacters) 
    {
        this.outputCharacters = outputCharacters;
    }

    public Long getOutputCharacters() 
    {
        return outputCharacters;
    }

    public void setTotalCharacters(Long totalCharacters) 
    {
        this.totalCharacters = totalCharacters;
    }

    public Long getTotalCharacters() 
    {
        return totalCharacters;
    }

    public void setCost(BigDecimal cost) 
    {
        this.cost = cost;
    }

    public BigDecimal getCost() 
    {
        return cost;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setErrorMessage(String errorMessage) 
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() 
    {
        return errorMessage;
    }

    public void setRequestTime(Date requestTime) 
    {
        this.requestTime = requestTime;
    }

    public Date getRequestTime() 
    {
        return requestTime;
    }

    public void setResponseTime(Date responseTime) 
    {
        this.responseTime = responseTime;
    }

    public Date getResponseTime() 
    {
        return responseTime;
    }

    public void setResponseTimeMs(Long responseTimeMs) 
    {
        this.responseTimeMs = responseTimeMs;
    }

    public Long getResponseTimeMs() 
    {
        return responseTimeMs;
    }

    public void setIpAddress(String ipAddress) 
    {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() 
    {
        return ipAddress;
    }

    public void setUserAgent(String userAgent) 
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent() 
    {
        return userAgent;
    }

    public void setSessionId(String sessionId) 
    {
        this.sessionId = sessionId;
    }

    public String getSessionId() 
    {
        return sessionId;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    @Override
    public void setRemark(String remark) 
    {
        this.remark = remark;
    }

    @Override
    public String getRemark() 
    {
        return remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("apiKey", getApiKey())
            .append("modelName", getModelName())
            .append("requestType", getRequestType())
            .append("inputTokens", getInputTokens())
            .append("outputTokens", getOutputTokens())
            .append("totalTokens", getTotalTokens())
            .append("inputCharacters", getInputCharacters())
            .append("outputCharacters", getOutputCharacters())
            .append("totalCharacters", getTotalCharacters())
            .append("cost", getCost())
            .append("status", getStatus())
            .append("errorMessage", getErrorMessage())
            .append("requestTime", getRequestTime())
            .append("responseTime", getResponseTime())
            .append("responseTimeMs", getResponseTimeMs())
            .append("ipAddress", getIpAddress())
            .append("userAgent", getUserAgent())
            .append("sessionId", getSessionId())
            .append("projectName", getProjectName())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}