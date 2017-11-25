package com.itee.bingsheng.web.action.base;

import com.itee.bingsheng.service.IComboboxService;
import com.itee.bingsheng.utils.core.util.BL3Utils;
import com.itee.bingsheng.utils.core.web.WebUtils;
import com.itee.bingsheng.web.action.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Jesse
 */
@Controller
@RequestMapping(value = "/combobox")
public class ComboboxController extends BaseController {

    @Resource
    IComboboxService comboboxService;

    /**获取所有combobox
     * @return
     */
    @RequestMapping(value = "getComboxData", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,Object>> getComboxData(HttpServletRequest request){
        try {
            params = WebUtils.getParamAsDto(request);
            Map<String, String[]> map = request.getParameterMap();
            for (String key : map.keySet()) {
                if(key.contains("Ids")||key.contains("Names")){
                    params.put(key, ((String[]) map.get(key)));
                }else {
                    params.put(key, ((String[]) map.get(key))[0]);
                }
            }
            BL3Utils.mapRemoveNull(params);
            return comboboxService.getComboxData(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**获取经纪人combobox
     * @return
     */
    @RequestMapping(value = "boxRecommend", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,Object>> boxRecommend(HttpServletRequest request){
        try {
            params = WebUtils.getParamAsDto(request);
            BL3Utils.mapRemoveNull(params);
            return comboboxService.boxRecommend(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
