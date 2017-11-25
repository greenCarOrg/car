package com.itee.bingsheng.aspect.DataSource;

import com.itee.bingsheng.service.ICommonService;
import com.itee.bingsheng.utils.GeoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CommonInterceptor implements HandlerInterceptor {

	@Resource
	ICommonService commonService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 	long start = System.currentTimeMillis();
	        request.setAttribute("_start", start);
	        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	private String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null||ip.length()<=0) {
			return request.getRemoteAddr();
		}
		return ip;
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long start = (long) request.getAttribute("_start");

        String actionName = request.getRequestURI();
        String clientIp = getRemortIP(request);
        StringBuffer logstring = new StringBuffer();
        logstring.append(clientIp).append("|").append(actionName).append("|");
        Map<String, String[]> parmas = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iter = parmas.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String[]> entry = iter.next();
            logstring.append(entry.getKey());
            logstring.append("=");
            for (String paramString : entry.getValue()) {
                logstring.append(paramString);
            }
            logstring.append("|");
        }
        long executionTime = System.currentTimeMillis() - start;
        logstring.append("excutetime=").append(executionTime).append("ms");
		if(!actionName.equals("/")){
			Map<String,Object> map=new HashMap<>();
			map.put("ip",clientIp);
			map.put("excuteTime",executionTime+"ms");
			map.put("name",actionName);
			String add=null;
			try {
				String[] ss= GeoUtil.getIPXY(clientIp);
				if(ss!=null) {
					map.put("jwd",ss[0]+","+ss[1]);
					add=GeoUtil.getBaiduAddress(ss[0]+","+ss[1]);
				}
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				map.put("address",add);
				commonService.saveIntefaceLog(map);
			}
		}
	}
}
