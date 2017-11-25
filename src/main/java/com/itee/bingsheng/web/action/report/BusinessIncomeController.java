package com.itee.bingsheng.web.action.report;

import com.alibaba.fastjson.JSONObject;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.dao.ChargeMyBatisDao;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.utils.CalendarUtils;
import com.itee.bingsheng.utils.DateUtils;
import com.itee.bingsheng.utils.ExportExcel;
import com.itee.bingsheng.utils.StringEncodeUtils;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.utils.core.web.WebUtils;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/7/26.
 */
@Controller
@RequestMapping(value = "/report")
public class BusinessIncomeController extends BaseController{

    private Logger logger = Logger.getLogger(BusinessIncomeController.class.getName());

    @Resource
    private ChargeMyBatisDao chargeMyBatisDao;



    @RequestMapping(value = "businessIncome", method = RequestMethod.GET)
    public String businessIncome(ModelMap modelMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        Calendar calendar = Calendar.getInstance();
        modelMap.addAttribute("endDate", calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        modelMap.addAttribute("startDate",calendar.getTime());

        //教学点
       // modelMap.addAttribute("schoolList", comboboxService.boxSchools());

        //收入类型
        List<Map<String,Object>> incomeTypeList = new ArrayList<Map<String,Object>>();
        Map<String, Object> incomeTypeMap=new HashMap<String, Object>();
        incomeTypeMap.put("typeId",1);
        incomeTypeMap.put("typeName","充值");
        incomeTypeList.add(incomeTypeMap);
        incomeTypeMap=new HashMap<String, Object>();
        incomeTypeMap.put("typeId",12);
        incomeTypeMap.put("typeName","转校充值");
        incomeTypeList.add(incomeTypeMap);
        incomeTypeMap=new HashMap<String, Object>();
        incomeTypeMap.put("typeId",9);
        incomeTypeMap.put("typeName","退费营收");
        incomeTypeList.add(incomeTypeMap);
        incomeTypeMap=new HashMap<String, Object>();
        incomeTypeMap.put("typeId",10);
        incomeTypeMap.put("typeName","预报名订金");
        incomeTypeList.add(incomeTypeMap);
        incomeTypeMap=new HashMap<String, Object>();
        incomeTypeMap.put("typeId",14);
        incomeTypeMap.put("typeName","精品课营销");
        incomeTypeList.add(incomeTypeMap);
        modelMap.addAttribute("incomeTypeList", incomeTypeList);
        //支出类型
        List<Map<String,Object>> payTypeList = new ArrayList<Map<String,Object>>();
        Map<String, Object> payTypeMap=new HashMap<String, Object>();
        payTypeMap.put("typeId",2);
        payTypeMap.put("payTypeName","退费");
        payTypeList.add(payTypeMap);
        payTypeMap=new HashMap<String, Object>();
        payTypeMap.put("typeId",13);
        payTypeMap.put("payTypeName","转校退费");
        payTypeList.add(payTypeMap);
        payTypeMap=new HashMap<String, Object>();
        payTypeMap.put("typeId",16);
        payTypeMap.put("payTypeName","经纪人介绍费");
        payTypeList.add(payTypeMap);
        payTypeMap=new HashMap<String, Object>();
        payTypeMap.put("typeId",14);
        payTypeMap.put("payTypeName","精品课购买");
        payTypeList.add(payTypeMap);
        modelMap.addAttribute("payTypeList", payTypeList);
        //获取manage端机构类型写入新增、编辑页面：0-全日制；1-课外辅导；2-全选(全日制+课外辅导)
        int manageModel = 1;

        modelMap.addAttribute("cropManageModel",manageModel);
        return "report/businessIncome";
    }
    /**表头：营业收入-统计汇总
     * @return
     */
    @RequestMapping(value = "businessIncomeCount" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> businessIncomeCount(HttpServletRequest request) {
        try {
            params = WebUtils.getParamAsDto(request);
            BL3Utils.mapRemoveNull(this.params);
            HttpSession session = request.getSession();
            SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
            if(params!=null&&params.size()>0&&params.getAsInteger("operateType") != null){
                //操作类型：0-营收按校区；1-营收按时间；2-充值收入按充值方式；3-收入按类型；4-支出按类型
                int operateType=params.getAsInteger("operateType");
                if (operateType==0||operateType==1) {
                    return chargeMyBatisDao.businessIncomeCount(params);
                }else if(operateType==2){
                    Double totalMoney= chargeMyBatisDao.businessIncomeByPayTypeCount(params);
                    map.put("totalMoney",totalMoney);
                    return map;
                }else if(operateType==3){
                    Double totalMoney= chargeMyBatisDao.businessIncomeByTypeCount(params);
                    map.put("totalMoney",totalMoney);
                    return map;
                }else if(operateType==4){
                    Double totalMoney= chargeMyBatisDao.businessIncomePayByTypeCount(params);
                    map.put("totalMoney",totalMoney);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "refundStatistics", method = RequestMethod.GET)
    public String refundStatistics(ModelMap modelMap){
        Calendar calendar = Calendar.getInstance();
        modelMap.addAttribute("endDate", CalendarUtils.formatDate(calendar.getTime()));
        calendar.add(Calendar.MONTH, -3);
        modelMap.addAttribute("startDate", CalendarUtils.formatDate(calendar.getTime()));
        return "report/refundStatistics";
    }

    /**营收按校区
     * @param pageSpecification
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "pageBusinessIncome" ,method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage pageBusinessIncome(@RequestBody PageSpecification<Map<String,Object>> pageSpecification, HttpServletRequest request) throws ParseException {
        try {
            pageSpecification.setPage((pageSpecification.getPage() - 1) * pageSpecification.getPageSize());
            HttpSession session = request.getSession();
            SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**报表：营业收入-营收按校区
     * @return
     */
    @RequestMapping(value = "businessIncomeSchoolChart" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> businessIncomeSchoolChart(HttpServletRequest request){
        Map<String, Object> m = null;
        try {
            HttpSession session = request.getSession();
            SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);

            params = WebUtils.getParamAsDto(request);

            BL3Utils.mapRemoveNull(this.params);
            if (params!= null) {
                if(params.getAsInteger("operateType") != null){
                    m = new HashMap<String, Object>();
                    //操作类型：0-营收按校区；1-营收按时间；2-充值收入按充值方式；3-收入按类型；4-支出按类型
                    int operateType=params.getAsInteger("operateType");
                    switch (operateType){
                        case 0:
                            //按校区统计营业收入
                            m.put("columnChart", chargeMyBatisDao.businessIncomeSchoolChart(params));
                            break;
                        case 1:
                            //按时间统计营业收入
                            m.put("businessIncomeDateChart", chargeMyBatisDao.businessIncomeDateChart(params));
                            break;
                        case 2:
                            //按充值方式统计营业收入
                            m.put("columnChart", chargeMyBatisDao.businessIncomePayTypeChart(params));
                            break;
                        case 3:
                            //按收入类型统计营业收入
                            m.put("columnChart", chargeMyBatisDao.businessIncomeTypeChart(params));
                            break;
                        default:
                            //按支出类型统计营业收入
                            m.put("columnChart", chargeMyBatisDao.businessIncomePayByTypeChart(params));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String,Object>>(m, HttpStatus.OK);
    }
    /**导出：营业收入
     * @return
     */
    @RequestMapping(value = "exportBusinessIncome" ,method = RequestMethod.GET)
    @ResponseBody
    public void exportBusinessIncome(HttpServletRequest request,HttpServletResponse response){
        try {
            params = WebUtils.getParamAsDto(request);
            BL3Utils.mapRemoveNull(this.params);
            HttpSession session = request.getSession();

            params = WebUtils.getParamAsDto(request);
            if (params!= null) {
                if(params.getAsInteger("operateType") != null){
                    List<Map<String, Object>> list=null;
                    String worktableTitle="";
                    //操作类型：0-营收按校区；1-营收按时间；2-充值收入按充值方式；3-收入按类型；4-支出按类型
                    int operateType=params.getAsInteger("operateType");
                    //导出excel列名称
                    String [] columnNames=null;
                    // 字段列名
                    String [] column=null;
                    Double totalMoney=0.0;
                    Map<String, Object> totalMoneyMap=new HashMap<String, Object>();
                    switch (operateType){
                        case 0:
                            //按校区统计营业收入
                            worktableTitle="营收按校区";
                            columnNames=new String[]{"校区","充值收入(元)","其他收入(元)","退费支出(元)","其他支出(元)","营业收入(元)","营收占比"};
                            column=new String[]{"school_name","recharge_income","other_income","returns_pay","other_pay","business_income","business_scale"} ;
                            totalMoneyMap=chargeMyBatisDao.businessIncomeCount(params);
                            if (totalMoneyMap!= null&&totalMoneyMap.size()>0) {
                                params.put("totalMoney",totalMoneyMap.get("business_income_v"));
                            }
                            list =chargeMyBatisDao.exportBusinessIncomeBySchool(params);
                            break;
                        case 1:
                            //按时间统计营业收入
                            worktableTitle="营收按时间";
                            columnNames=new String[]{"日期","充值收入(元)","其他收入(元)","退费支出(元)","其他支出(元)","营业收入(元)","营收占比"};
                            column=new String[]{"create_time","recharge_income","other_income","returns_pay","other_pay","business_income","business_scale"} ;
                            if(params.getAsInteger("type")!= null){
                                params.put("dayMonth",(params.getAsInteger("type")==1?"%Y-%m-%d":"%Y-%m"));
                            }
                            totalMoneyMap=chargeMyBatisDao.businessIncomeCount(params);
                            if (totalMoneyMap!= null&&totalMoneyMap.size()>0) {
                                params.put("totalMoney",totalMoneyMap.get("business_income_v"));
                            }
                            list =chargeMyBatisDao.exportBusinessIncomeByDate(params);
                            break;
                        case 2:
                            //按充值方式统计营业收入
                            worktableTitle="充值收入按充值方式";
                            columnNames=new String[]{"充值方式","金额(元)","占比"};
                            column=new String[]{"pay_type","money","money_scale"};
                            totalMoney= chargeMyBatisDao.businessIncomeByPayTypeCount(params);
                            params.put("totalMoney",totalMoney);
                            list =chargeMyBatisDao.exportBusinessIncomeByPayType(params);
                            break;
                        case 3:
                            //按收入类型统计营业收入
                            worktableTitle="收入按类型";
                            columnNames=new String[]{"收入类型","金额(元)","占比"};
                            column=new String[]{"type_name","money","money_scale"} ;
                            totalMoney= chargeMyBatisDao.businessIncomeByTypeCount(params);
                            params.put("totalMoney",totalMoney);
                            list = chargeMyBatisDao.exportBusinessIncomeByType(params);
                            break;
                        default:
                            //按支出类型统计营业收入
                            worktableTitle="支出按类型";
                            columnNames=new String[]{"支出类型","金额(元)","占比"};
                            column=new String[]{"type_name","money","money_scale"} ;
                            totalMoney= chargeMyBatisDao.businessIncomePayByTypeCount(params);
                            params.put("totalMoney",totalMoney);
                            list =chargeMyBatisDao.exportBusinessIncomePayByType(params);
                            break;
                    }
                    HSSFWorkbook wb = ExportExcel.exportExcelNoLineNum(worktableTitle,worktableTitle,columnNames,column, list);
                    String excelName = worktableTitle+CalendarUtils.formatDateToUnsigned(new Date())+".xls";
                    response.setContentType("application/x-download");
                    response.setHeader("Content-disposition","attachment;filename=\""+ StringEncodeUtils.gbkToIso88591(excelName) + "\"");
                    OutputStream os = response.getOutputStream();
                    wb.write(os);
                    os.flush();
                    os.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**报表：课酬统计-老师课酬按校区
     * @return
     */
    @RequestMapping(value = "formulaIncomeSchoolChart" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> formulaIncomeSchoolChart(HttpServletRequest request){
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> params = new HashMap<>();
        for (String key : map.keySet()) {
            if(key.contains("Ids")||key.contains("Names")){
                params.put(key, ((String[]) map.get(key)));
            }else {
                params.put(key, ((String[]) map.get(key))[0]);
            }
        }
        BL3Utils.mapRemoveNull(params);
        String year = params.get("year").toString();
        String month = params.get("month").toString();

        //判断当前登录用户是否授权过数据查看
        SysUser SysUser = (SysUser) this.getRequest().getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);

        if(month.equals("00")){
            params.put("all", "all");
            params.put("startDate", year+"-01-01 00:00:00");
            params.put("endDate", year+"-12-31 23:59:59");
        }else {
            params.put("startDate", year+"-"+month+"-01 00:00:00");
            Date date = DateUtils.parseDate(year+"-"+month+"-01 00:00:00");
            Calendar ca = CalendarUtils.toCalendar(date);
            //获取当前月最后一天
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last = DateUtils.dateToStringYMD(ca.getTime());
            params.put("endDate", last+" 23:59:59");
        }

        params.put("startMonth",params.get("startDate").toString().substring(0,7));
        params.put("endMonth",params.get("endDate").toString().substring(0,7));

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("startDate",params.get("startDate").toString().substring(0,10));
        m.put("endDate",params.get("endDate").toString().substring(0,10));
		if("0".equals(params.get("operateType").toString())){
			m.put("formulaIncomeSchoolChart", chargeMyBatisDao.formulaIncomeTeacherChart(params));
		}else if("1".equals(params.get("operateType").toString())){
			m.put("formulaIncomeSchoolChart", chargeMyBatisDao.formulaIncomeSchoolChart(params));
		}else if("2".equals(params.get("operateType").toString())){
			m.put("formulaIncomeSchoolChart", chargeMyBatisDao.formulaIncomeSubjectChart(params));
		}else if("3".equals(params.get("operateType").toString())){
            m.put("formulaIncomeSchoolChart", chargeMyBatisDao.formulaIncomeCourseChart(params));
        }else if("4".equals(params.get("operateType").toString())){
            m.put("formulaIncomeSchoolChart", chargeMyBatisDao.formulaIncomeSectionChart(params));
        }else if("5".equals(params.get("operateType").toString())){
            m.put("formulaIncomeSchoolChart", chargeMyBatisDao.formulaIncomeGradeChart(params));
        }

		return new ResponseEntity<Map<String,Object>>(m, HttpStatus.OK);
    }

    @RequestMapping(value = "getBusinessIncomeSumByDate" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Double> getBusinessIncomeSumByDate(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("schoolName")String schoolName) {

        return null;
    }

    @RequestMapping(value = "modifiedRechargeDate", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifiedRechargeDate(@RequestParam("chargeId") int chargeId, @RequestParam("createDate") String createDate) {
        JSONObject returnObj = new JSONObject();
        returnObj.put("msg","");
        boolean flag = false;


        return returnObj;
    }

    @RequestMapping(value = "businessIncomeDtl", method = RequestMethod.GET)
    public String businessIncomeDtl(@RequestParam("schoolId")String schoolId,@RequestParam("schoolName")String schoolName,ModelMap modelMap){
        Calendar calendar = Calendar.getInstance();
        modelMap.addAttribute("endDate", CalendarUtils.formatDate(calendar.getTime()));
        calendar.add(Calendar.MONTH, -3);
        modelMap.addAttribute("startDate", CalendarUtils.formatDate(calendar.getTime()));
        modelMap.addAttribute("schoolId",schoolId);
        modelMap.addAttribute("schoolName",schoolName);
        return "report/businessIncomeDtl";
    }
}
