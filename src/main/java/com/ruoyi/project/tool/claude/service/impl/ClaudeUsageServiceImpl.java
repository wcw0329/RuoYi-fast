package com.ruoyi.project.tool.claude.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.tool.claude.domain.ClaudeUsage;
import com.ruoyi.project.tool.claude.mapper.ClaudeUsageMapper;
import com.ruoyi.project.tool.claude.service.IClaudeUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Claude API 使用记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@Service
public class ClaudeUsageServiceImpl implements IClaudeUsageService 
{
    @Autowired
    private ClaudeUsageMapper claudeUsageMapper;

    /**
     * 查询Claude API 使用记录
     * 
     * @param id Claude API 使用记录主键
     * @return Claude API 使用记录
     */
    @Override
    public ClaudeUsage selectClaudeUsageById(Long id)
    {
        return claudeUsageMapper.selectClaudeUsageById(id);
    }

    /**
     * 查询Claude API 使用记录列表
     * 
     * @param claudeUsage Claude API 使用记录
     * @return Claude API 使用记录
     */
    @Override
    public List<ClaudeUsage> selectClaudeUsageList(ClaudeUsage claudeUsage)
    {
        return claudeUsageMapper.selectClaudeUsageList(claudeUsage);
    }

    /**
     * 新增Claude API 使用记录
     * 
     * @param claudeUsage Claude API 使用记录
     * @return 结果
     */
    @Override
    public int insertClaudeUsage(ClaudeUsage claudeUsage)
    {
        claudeUsage.setCreateTime(DateUtils.getNowDate());
        return claudeUsageMapper.insertClaudeUsage(claudeUsage);
    }

    /**
     * 修改Claude API 使用记录
     * 
     * @param claudeUsage Claude API 使用记录
     * @return 结果
     */
    @Override
    public int updateClaudeUsage(ClaudeUsage claudeUsage)
    {
        claudeUsage.setUpdateTime(DateUtils.getNowDate());
        return claudeUsageMapper.updateClaudeUsage(claudeUsage);
    }

    /**
     * 批量删除Claude API 使用记录
     * 
     * @param ids 需要删除的Claude API 使用记录主键
     * @return 结果
     */
    @Override
    public int deleteClaudeUsageByIds(Long[] ids)
    {
        return claudeUsageMapper.deleteClaudeUsageByIds(ids);
    }

    /**
     * 删除Claude API 使用记录信息
     * 
     * @param id Claude API 使用记录主键
     * @return 结果
     */
    @Override
    public int deleteClaudeUsageById(Long id)
    {
        return claudeUsageMapper.deleteClaudeUsageById(id);
    }

    /**
     * 统计用户使用量
     * 
     * @param userId 用户ID
     * @return 统计结果
     */
    @Override
    public ClaudeUsage selectUserUsageStats(Long userId)
    {
        return claudeUsageMapper.selectUserUsageStats(userId);
    }

    /**
     * 统计总使用量
     * 
     * @return 统计结果
     */
    @Override
    public ClaudeUsage selectTotalUsageStats()
    {
        return claudeUsageMapper.selectTotalUsageStats();
    }

    /**
     * 按日期统计使用量
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计结果列表
     */
    @Override
    public List<ClaudeUsage> selectUsageStatsByDate(String startDate, String endDate)
    {
        return claudeUsageMapper.selectUsageStatsByDate(startDate, endDate);
    }

    /**
     * 按模型统计使用量
     * 
     * @return 统计结果列表
     */
    @Override
    public List<ClaudeUsage> selectUsageStatsByModel()
    {
        return claudeUsageMapper.selectUsageStatsByModel();
    }

    /**
     * 记录API调用
     * 
     * @param claudeUsage 使用记录
     * @return 结果
     */
    @Override
    public int recordApiCall(ClaudeUsage claudeUsage)
    {
        // 设置当前用户信息
        if (claudeUsage.getUserId() == null) {
            claudeUsage.setUserId(SecurityUtils.getUserId());
        }
        if (claudeUsage.getUserName() == null) {
            claudeUsage.setUserName(SecurityUtils.getUsername());
        }
        
        // 设置请求时间
        if (claudeUsage.getRequestTime() == null) {
            claudeUsage.setRequestTime(DateUtils.getNowDate());
        }
        
        return insertClaudeUsage(claudeUsage);
    }

    /**
     * 获取用户今日使用量
     * 
     * @param userId 用户ID
     * @return 今日使用量
     */
    @Override
    public ClaudeUsage selectUserTodayUsage(Long userId)
    {
        String today = DateUtils.getDate();
        List<ClaudeUsage> list = claudeUsageMapper.selectUsageStatsByDate(today, today);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return new ClaudeUsage();
    }

    /**
     * 获取用户本月使用量
     * 
     * @param userId 用户ID
     * @return 本月使用量
     */
    @Override
    public ClaudeUsage selectUserMonthUsage(Long userId)
    {
        String monthStart = DateUtils.getMonthStart();
        String monthEnd = DateUtils.getMonthEnd();
        List<ClaudeUsage> list = claudeUsageMapper.selectUsageStatsByDate(monthStart, monthEnd);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return new ClaudeUsage();
    }
}