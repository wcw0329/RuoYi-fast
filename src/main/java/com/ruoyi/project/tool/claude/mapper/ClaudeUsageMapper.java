package com.ruoyi.project.tool.claude.mapper;

import com.ruoyi.project.tool.claude.domain.ClaudeUsage;
import java.util.List;

/**
 * Claude API 使用记录Mapper接口
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
public interface ClaudeUsageMapper 
{
    /**
     * 查询Claude API 使用记录
     * 
     * @param id Claude API 使用记录主键
     * @return Claude API 使用记录
     */
    public ClaudeUsage selectClaudeUsageById(Long id);

    /**
     * 查询Claude API 使用记录列表
     * 
     * @param claudeUsage Claude API 使用记录
     * @return Claude API 使用记录集合
     */
    public List<ClaudeUsage> selectClaudeUsageList(ClaudeUsage claudeUsage);

    /**
     * 新增Claude API 使用记录
     * 
     * @param claudeUsage Claude API 使用记录
     * @return 结果
     */
    public int insertClaudeUsage(ClaudeUsage claudeUsage);

    /**
     * 修改Claude API 使用记录
     * 
     * @param claudeUsage Claude API 使用记录
     * @return 结果
     */
    public int updateClaudeUsage(ClaudeUsage claudeUsage);

    /**
     * 删除Claude API 使用记录
     * 
     * @param id Claude API 使用记录主键
     * @return 结果
     */
    public int deleteClaudeUsageById(Long id);

    /**
     * 批量删除Claude API 使用记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClaudeUsageByIds(Long[] ids);

    /**
     * 统计用户使用量
     * 
     * @param userId 用户ID
     * @return 统计结果
     */
    public ClaudeUsage selectUserUsageStats(Long userId);

    /**
     * 统计总使用量
     * 
     * @return 统计结果
     */
    public ClaudeUsage selectTotalUsageStats();

    /**
     * 按日期统计使用量
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计结果列表
     */
    public List<ClaudeUsage> selectUsageStatsByDate(String startDate, String endDate);

    /**
     * 按模型统计使用量
     * 
     * @return 统计结果列表
     */
    public List<ClaudeUsage> selectUsageStatsByModel();
}