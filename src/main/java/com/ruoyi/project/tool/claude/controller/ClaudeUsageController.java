package com.ruoyi.project.tool.claude.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.tool.claude.domain.ClaudeUsage;
import com.ruoyi.project.tool.claude.service.IClaudeUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Claude API 使用记录Controller
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/tool/claude/usage")
public class ClaudeUsageController extends BaseController
{
    @Autowired
    private IClaudeUsageService claudeUsageService;

    /**
     * 查询Claude API 使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClaudeUsage claudeUsage)
    {
        startPage();
        List<ClaudeUsage> list = claudeUsageService.selectClaudeUsageList(claudeUsage);
        return getDataTable(list);
    }

    /**
     * 导出Claude API 使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:export')")
    @Log(title = "Claude API 使用记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClaudeUsage claudeUsage)
    {
        List<ClaudeUsage> list = claudeUsageService.selectClaudeUsageList(claudeUsage);
        ExcelUtil<ClaudeUsage> util = new ExcelUtil<ClaudeUsage>(ClaudeUsage.class);
        util.exportExcel(response, list, "Claude API 使用记录数据");
    }

    /**
     * 获取Claude API 使用记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(claudeUsageService.selectClaudeUsageById(id));
    }

    /**
     * 新增Claude API 使用记录
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:add')")
    @Log(title = "Claude API 使用记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClaudeUsage claudeUsage)
    {
        return toAjax(claudeUsageService.insertClaudeUsage(claudeUsage));
    }

    /**
     * 修改Claude API 使用记录
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:edit')")
    @Log(title = "Claude API 使用记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClaudeUsage claudeUsage)
    {
        return toAjax(claudeUsageService.updateClaudeUsage(claudeUsage));
    }

    /**
     * 删除Claude API 使用记录
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:remove')")
    @Log(title = "Claude API 使用记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(claudeUsageService.deleteClaudeUsageByIds(ids));
    }

    /**
     * 获取用户使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:stats')")
    @GetMapping("/stats/user/{userId}")
    public AjaxResult getUserStats(@PathVariable("userId") Long userId)
    {
        return success(claudeUsageService.selectUserUsageStats(userId));
    }

    /**
     * 获取总使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:stats')")
    @GetMapping("/stats/total")
    public AjaxResult getTotalStats()
    {
        return success(claudeUsageService.selectTotalUsageStats());
    }

    /**
     * 按日期获取使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:stats')")
    @GetMapping("/stats/date")
    public AjaxResult getDateStats(@RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate)
    {
        List<ClaudeUsage> list = claudeUsageService.selectUsageStatsByDate(startDate, endDate);
        return success(list);
    }

    /**
     * 按模型获取使用统计
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:stats')")
    @GetMapping("/stats/model")
    public AjaxResult getModelStats()
    {
        List<ClaudeUsage> list = claudeUsageService.selectUsageStatsByModel();
        return success(list);
    }

    /**
     * 获取用户今日使用量
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:stats')")
    @GetMapping("/stats/today/{userId}")
    public AjaxResult getUserTodayUsage(@PathVariable("userId") Long userId)
    {
        return success(claudeUsageService.selectUserTodayUsage(userId));
    }

    /**
     * 获取用户本月使用量
     */
    @PreAuthorize("@ss.hasPermi('tool:claude:usage:stats')")
    @GetMapping("/stats/month/{userId}")
    public AjaxResult getUserMonthUsage(@PathVariable("userId") Long userId)
    {
        return success(claudeUsageService.selectUserMonthUsage(userId));
    }

    /**
     * 记录API调用
     */
    @PostMapping("/record")
    public AjaxResult recordApiCall(@RequestBody ClaudeUsage claudeUsage)
    {
        return toAjax(claudeUsageService.recordApiCall(claudeUsage));
    }
}