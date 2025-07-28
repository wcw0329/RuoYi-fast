package com.ruoyi.project.tool.claude.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project.tool.claude.domain.ClaudeUsage;
import com.ruoyi.project.tool.claude.service.IClaudeUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Claude API 使用统计Controller
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/tool/claude/stats")
public class ClaudeStatsController extends BaseController
{
    @Autowired
    private IClaudeUsageService claudeUsageService;

    /**
     * 获取总使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/total")
    public AjaxResult getTotalStats()
    {
        try {
            ClaudeUsage totalStats = claudeUsageService.selectTotalUsageStats();
            return success(totalStats);
        } catch (Exception e) {
            return error("获取总使用统计失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/user/{userId}")
    public AjaxResult getUserStats(@PathVariable("userId") Long userId)
    {
        try {
            ClaudeUsage userStats = claudeUsageService.selectUserUsageStats(userId);
            return success(userStats);
        } catch (Exception e) {
            return error("获取用户使用统计失败: " + e.getMessage());
        }
    }

    /**
     * 按日期获取使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/date")
    public AjaxResult getDateStats(@RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate)
    {
        try {
            List<ClaudeUsage> list = claudeUsageService.selectUsageStatsByDate(startDate, endDate);
            return success(list);
        } catch (Exception e) {
            return error("获取日期统计失败: " + e.getMessage());
        }
    }

    /**
     * 按模型获取使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/model")
    public AjaxResult getModelStats()
    {
        try {
            List<ClaudeUsage> list = claudeUsageService.selectUsageStatsByModel();
            return success(list);
        } catch (Exception e) {
            return error("获取模型统计失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户今日使用量
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/today/{userId}")
    public AjaxResult getUserTodayUsage(@PathVariable("userId") Long userId)
    {
        try {
            ClaudeUsage todayUsage = claudeUsageService.selectUserTodayUsage(userId);
            return success(todayUsage);
        } catch (Exception e) {
            return error("获取用户今日使用量失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户本月使用量
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/month/{userId}")
    public AjaxResult getUserMonthUsage(@PathVariable("userId") Long userId)
    {
        try {
            ClaudeUsage monthUsage = claudeUsageService.selectUserMonthUsage(userId);
            return success(monthUsage);
        } catch (Exception e) {
            return error("获取用户本月使用量失败: " + e.getMessage());
        }
    }

    /**
     * 获取综合统计信息
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/comprehensive")
    public AjaxResult getComprehensiveStats()
    {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // 总使用统计
            ClaudeUsage totalStats = claudeUsageService.selectTotalUsageStats();
            stats.put("total", totalStats);
            
            // 按模型统计
            List<ClaudeUsage> modelStats = claudeUsageService.selectUsageStatsByModel();
            stats.put("byModel", modelStats);
            
            // 最近7天统计
            List<ClaudeUsage> recentStats = claudeUsageService.selectUsageStatsByDate(null, null);
            stats.put("recent", recentStats);
            
            return success(stats);
        } catch (Exception e) {
            return error("获取综合统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取费用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:stats:view')")
    @GetMapping("/cost")
    public AjaxResult getCostStats(@RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate)
    {
        try {
            List<ClaudeUsage> list = claudeUsageService.selectUsageStatsByDate(startDate, endDate);
            
            // 计算总费用
            double totalCost = list.stream()
                .mapToDouble(usage -> usage.getCost() != null ? usage.getCost().doubleValue() : 0.0)
                .sum();
            
            Map<String, Object> result = new HashMap<>();
            result.put("totalCost", totalCost);
            result.put("dailyStats", list);
            
            return success(result);
        } catch (Exception e) {
            return error("获取费用统计失败: " + e.getMessage());
        }
    }
}