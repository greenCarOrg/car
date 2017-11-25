package com.itee.bingsheng.web.action.report;

import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.util.MybatisPage;
import com.itee.bingsheng.mybatis.util.Pager;
import com.itee.bingsheng.persistence.PageSpecification;
import com.itee.bingsheng.service.IComboboxService;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.web.WebConstants;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;


/**
 * 课耗统计页面
 */
@Controller
@RequestMapping(value = "/report")
public class SchoolIncomeController extends BaseController {





    @Resource
    private IComboboxService comboboxService;


    //初始化加载数据
    @RequestMapping(value = "schoolIncome", method = RequestMethod.GET)
    public String schoolTuition(ModelMap modelMap,HttpServletRequest request) {
        Calendar calendar = Calendar.getInstance();
        modelMap.addAttribute("endDate", calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        modelMap.addAttribute("startDate", calendar.getTime());
        try {
            HttpSession session = request.getSession();
            SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);

            //加载全部教学点
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("type","school");

            //加载全部的学科
            map1.put("type","subject");





        }catch (Exception e){
            e.printStackTrace();
        }
        return "report/newschoolIncome";
    }




    /**
     * 加载列表显示数据
     *
     * @return
     */
    @RequestMapping(value = "pagedStudentCurrFee" ,method = RequestMethod.POST)
    @ResponseBody
    public MybatisPage pagedStudentCurrFee(@RequestBody PageSpecification<Map<String,Object>> pageSpecification, HttpServletRequest request) throws ParseException {
        MybatisPage<Map<String,Object>> returnMap = new MybatisPage<>();
        try {
            //判断当前登录用户是否授权过数据查看
            HttpSession session = request.getSession();
            SysUser SysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);

            Pager pager = new Pager(pageSpecification.getPage()-1,pageSpecification.getTake());
            params.put("pager", pager);

            BL3Utils.mapRemoveNull(params);


            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return returnMap;
    }

    /**
     * 根据教学点id获取老师
     */
    @RequestMapping(value = "getTeachersBySchoolIds", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getTeachersBySchoolIds(HttpServletRequest request) {
        Map<String, String[]> map1 = request.getParameterMap();
        String[] sc= map1.get("schoolIds[]");
        Map<String, Object> map = new HashMap<String, Object>();
        if(sc!=null && sc.length>0){
            map.put("schoolIds",Arrays.asList(sc));
        }
        List<Map<String, Object>> teacherList = new ArrayList<Map<String, Object>>();

        map = new HashMap<String, Object>();
        map.put("teacherId", null);
        map.put("teacherName", "所有老师");
        teacherList.add(map);
        return teacherList;
    }


    /**
     * 根据教学点获取课程
     * @return
     */
    @RequestMapping(value = "getCourseBySchoolIds", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getCourseBySchoolIds(HttpServletRequest request) {
        Map<String, String[]> map1 = request.getParameterMap();

        String[] sc= map1.get("schoolIds[]");
        Map<String, Object> map = new HashMap<String, Object>();
        if(sc!=null && sc.length>0){
            map.put("schoolIds",Arrays.asList(sc));
        }
        //加载所有的课程
        List<Map<String,Object>> courseList=new ArrayList<>();
        Map<String,Object> map5 = new HashMap<String, Object>();
        map5.put("courseId",null);
        map5.put("courseName","全部");
        courseList.add(map5);
        return courseList;
    }

    /**
     * 根据学段获取年级
     */
    @RequestMapping(value = "getGradeBySection", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getGradeBySection(HttpServletRequest request) {
        Map<String, String[]> map1 = request.getParameterMap();

        String[] sc= map1.get("sections[]");
        Map<String, Object> map = new HashMap<String, Object>();
        if(sc!=null && sc.length>0){
            map.put("sections",Arrays.asList(sc));
        }

        List<Map<String, Object>> gradeList = new ArrayList<Map<String, Object>>();
        map = new HashMap<String, Object>();
        map.put("gradeId",null);
        map.put("gradeName","全部");
        gradeList.add(map);
        return gradeList;
    }







    /**
     * 报表-- BS端课耗统计
     * @return
     */
    @RequestMapping(value = "schoolIncomeChart" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> schoolIncomeChart(HttpServletRequest request)throws Exception{

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
        SysUser SysUser = (SysUser) request.getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);

        Map<String, Object> m = new HashMap<>();

        return new ResponseEntity<Map<String,Object>>(m, HttpStatus.OK);
    }

    /**
     * 课酬报表课酬总数计算
     * @param request
     * @return
     */
    @RequestMapping(value = "getSchoolIncomeAll" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSchoolIncomeAll(HttpServletRequest request) {
        try {

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
            SysUser SysUser = (SysUser) request.getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
