package com.ruoyi.project.develop.student.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 学生对象 student
 * 
 * @author ruoyi
 * @date 2021-09-27
 */
public class Student extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 学号 */
    @Excel(name = "学号")
    private String studentNum;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String address;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long studentAge;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String studentSex;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date studentBirthday;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getStudentName()
    {
        return studentName;
    }
    public void setStudentNum(String studentNum)
    {
        this.studentNum = studentNum;
    }

    public String getStudentNum()
    {
        return studentNum;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setStudentAge(Long studentAge)
    {
        this.studentAge = studentAge;
    }

    public Long getStudentAge()
    {
        return studentAge;
    }
    public void setStudentSex(String studentSex)
    {
        this.studentSex = studentSex;
    }

    public String getStudentSex()
    {
        return studentSex;
    }
    public void setStudentBirthday(Date studentBirthday)
    {
        this.studentBirthday = studentBirthday;
    }

    public Date getStudentBirthday()
    {
        return studentBirthday;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentName", getStudentName())
            .append("studentNum", getStudentNum())
            .append("address", getAddress())
            .append("studentAge", getStudentAge())
            .append("studentSex", getStudentSex())
            .append("studentBirthday", getStudentBirthday())
            .append("remark", getRemark())
            .toString();
    }
}
