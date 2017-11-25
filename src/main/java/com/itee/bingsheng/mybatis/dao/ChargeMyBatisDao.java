package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.persistence.PageSpecification;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by abc on 2016/5/12.
 */
public interface ChargeMyBatisDao {

    /**
     * 查询营收总数 (按支付渠道)
     * @param params
     * @return
     */
    Double  getChargeGroupByPayTypeToCount(Map<String,Object> params);

    /**
     * 查询营收总数 (按学生年级)
     * @param params
     * @return
     */
    Double  getChargeGroupByGradeToCount(Map<String,Object> params);


    /**
     *查询学生的充值退费记录
     * @param params
     * @return
     */
    List<Map> pagedStudentRechargeAndRefund(Map<String,Object> params);

    /**
     *查询学生的充值退费记录总数
     * @param params
     * @return
     */
    Long getStudentRechargeAndRefundCount(Map<String,Object> params);

    Double getSchoolByTypeSum(Map<String,Object> params);

    /**
     * 0.grid-营收按校区-list
     * @param pageSpecification
     * @return
     */
    List<Map<String, Object>> pageBusinessIncomeBySchool(PageSpecification<Map<String, Object>> pageSpecification);

    /**
     * 0.grid-营收按校区-count
     * @param pageSpecification
     * @return
     */
    int pageBusinessIncomeBySchoolCount(PageSpecification<Map<String, Object>> pageSpecification);

	/**
	 * 0.export-营收按校区
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> exportBusinessIncomeBySchool(Map<String, Object> params);
    /**
     * 1.grid-营收按时间-list
     * @param pageSpecification
     * @return
     */
    List<Map<String, Object>> pageBusinessIncomeByDate(PageSpecification<Map<String, Object>> pageSpecification);

    /**
     * 1.grid-营收按时间-count
     * @param pageSpecification
     * @return
     */
    int pageBusinessIncomeByDateCount(PageSpecification<Map<String, Object>> pageSpecification);

	/**
	 * 1.export-营收按时间
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> exportBusinessIncomeByDate(Map<String, Object> params);

	/**
	 * 2.grid-营收按支付方式-list
	 * @param pageSpecification
	 * @return
	 */
	List<Map<String, Object>> pageBusinessIncomeByPayType(PageSpecification<Map<String, Object>> pageSpecification);


	/**2.grid-营收按支付方式-title
	 * @param params
	 * @return
	 */
	Double businessIncomeByPayTypeCount(Map<String,Object> params);
    /**
     * 2.grid-营收按支付方式-count
     * @param pageSpecification
     * @return
     */
    int pageBusinessIncomeByPayTypeCount(PageSpecification<Map<String, Object>> pageSpecification);

	/**
	 * 2.export-营收按支付方式
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> exportBusinessIncomeByPayType(Map<String, Object> params);

    /**
     * 3.grid-营收按收入类型-count
     * @param pageSpecification
     * @return
     */
    int pageBusinessIncomeByTypeCount(PageSpecification<Map<String, Object>> pageSpecification);
	/**
	 * 3.grid-营收按收入类型-list
	 * @param pageSpecification
	 * @return
	 */
	List<Map<String, Object>> pageBusinessIncomeByType(PageSpecification<Map<String, Object>> pageSpecification);

	/**3.grid-营收按收入类型-title
	 * @param params
	 * @return
	 */
	Double businessIncomeByTypeCount(Map<String,Object> params);

	/**3.chart：营业收入-按收入方式
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> businessIncomeTypeChart(Map<String,Object> params)throws Exception;
	/**
	 * 3.export-营收按收入类型
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> exportBusinessIncomeByType(Map<String, Object> params);
    /**
     * 4.grid-支出按类型-count
     * @param pageSpecification
     * @return
     */
    int pageBusinessIncomePayByTypeCount(PageSpecification<Map<String, Object>> pageSpecification);
	/**
	 * 4.grid-支出按类型-list
	 * @param pageSpecification
	 * @return
	 */
	List<Map<String, Object>> pageBusinessIncomePayByType(PageSpecification<Map<String, Object>> pageSpecification);

	/**4.grid-支出按类型-title
	 * @param params
	 * @return
	 */
	Double businessIncomePayByTypeCount(Map<String,Object> params);
	/**4.chart：营业收入-按支出方式
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> businessIncomePayByTypeChart(Map<String,Object> params)throws Exception;

	/**
	 * 4.export-支出按类型
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> exportBusinessIncomePayByType(Map<String, Object> params);




	/**修改退费状态
	 * @param chargeState
	 * @return
	 */
	int updateChargeState(@Param("studentId")int studentId,@Param("chargeState")int chargeState);

	/**0.报表：营业收入-营收按校区
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> businessIncomeSchoolChart(Map<String,Object> params)throws Exception;

	/**1.报表：营业收入-营收按时间
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> businessIncomeDateChart(Map<String,Object> params)throws Exception;
	/**2.chart：营业收入-营收充值方式
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> businessIncomePayTypeChart(Map<String,Object> params)throws Exception;

	/**表头：营业收入-统计汇总
	 * @param params
	 * @return
	 */
	Map<String,Object> businessIncomeCount(Map<String,Object> params)throws Exception;

	/**报表：老师课酬-按用户老师
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> formulaIncomeTeacherChart(Map<String,Object> params);


	/**报表：老师课酬-按用户老师
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> formulaSchoolTeacherChart(Map<String,Object> params);

	/**报表：老师课酬-按校区
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> formulaIncomeSchoolChart(Map<String,Object> params);

	/**报表：老师课酬-按学科
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> formulaIncomeSubjectChart(Map<String,Object> params);

	/**报表：老师课酬-按学科
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> formulaIncomeSectionChart(Map<String,Object> params);

	/**报表：老师课酬-按学科
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> formulaIncomeGradeChart(Map<String,Object> params);

	/**报表：老师课酬-按课程
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> formulaIncomeCourseChart(Map<String,Object> params);

	/**
	 * 班级营收报表分页查询
	 * @param params
	 * @return
	 */
    List<Map<String,Object>> getClassRevenueInfo(Map<String,Object> params);

	/**
	 * 班级营收报表数量
	 * @param params
	 * @return
	 */
	Map<String,Object> getClassRevenueInfoCount(Map<String,Object> params);
}
