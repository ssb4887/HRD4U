package rbs.modules.recommend;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rbs.egovframework.util.JwtUtil;

@Controller
@RequestMapping("/recommend")
public class RecommendDummyController {
	@Autowired
	RecommendDummyService recommendService;
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(RecommendDummyController.class);
	
	private final Map<HttpSession, PrintWriter> clients = new ConcurrentHashMap<>();
	private final Map<HttpSession, String> clientsNames = new ConcurrentHashMap<>();
	private final List<String> animalNames = Arrays.asList(
			"독도새우 🦐", "우웁.... 🤢", "손오공님 🐵", "에이요맨 😎", "개굴개굴 🐸", "롯데리아 🍔", "한잔해~ 🍺", "동해꽃게 🦀", "그만이야 🤡");
	private final SecureRandom random = new SecureRandom();
	private String assginRandomName(HttpSession session) {
		String name;
		do {
			name = animalNames.get(random.nextInt(animalNames.size()));
		} while(clientsNames.containsValue(name));
		clientsNames.put(session, name);
		return name;
	}
		
	@RequestMapping(value="/trends.do", method=RequestMethod.POST)
	@ResponseBody
	public String trends(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {
		String BPL_NO = req.getParameter("BPL_NO");
		Map basket = recommendService.getBSK(BPL_NO);
		basket.put("RANGE", getRange(basket));
		List<Map> trends = recommendService.getTrends(basket);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(trends);
		return jsonString;
		
	}
	@RequestMapping(value="/recommends.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String recommends(HttpServletRequest req) throws JsonProcessingException {
		long RSLT_IDX = Long.valueOf(req.getParameter("RSLT_IDX"));
		List<Map> names = recommendService.basicBizNames(RSLT_IDX);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(names);
		return jsonString;
	}
	@RequestMapping(value="/recomBiz.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String recomBiz(HttpServletRequest req) throws JsonProcessingException {
		long bsiscnsl_idx = Long.valueOf(req.getParameter("bsiscnsl_idx"));
		List<Map> names = recommendService.bsisRecom(bsiscnsl_idx);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(names);
		return jsonString;
	}
	@RequestMapping(value="/qr.do", method=RequestMethod.POST)
	@ResponseBody
	public String qrInsert(@RequestBody String data) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map mapped = mapper.readValue(data, Map.class);
		recommendService.insertQR(mapped);
		return "ok";
	}
	private int getRange(Map bsk) {
		int employees = Integer.parseInt(String.valueOf(bsk.get("TOT_WORK_CNT")));
		if("Z".equals(bsk.get("TREND_LCLAS")))
			return 0;
		if(employees < 5)
			return 5;
		if(employees < 30)
			return 30;
		if(employees < 100)
			return 100;
		if(employees < 300)
			return 300;
		if(employees < 1000)
			return 1000;
		return 10000000;
		
	}
	@RequestMapping(value="/ncscodes.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String ncscodes() throws JsonProcessingException {
		List<Map> codes = recommendService.getNCSCODES();
		List<Map> biz = recommendService.getPRTBIZ();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		result.put("codes", codes);
		result.put("biz", biz);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
	
	@RequestMapping(value="/trsearch.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String trsearch(@ModelAttribute PrtbizVO prtbizVO) throws JsonProcessingException {
		List<Map> tps = recommendService.getPRTBIZTP(prtbizVO);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(tps);
		return jsonString;
	}
	
	@RequestMapping(value="/saveConsult.do", method=RequestMethod.POST)
	public void saveConsult(@RequestBody String json, HttpServletRequest req, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map data_ = mapper.readValue(json, Map.class);
		// update 해야지
		int status = "temp".equals(data_.get("temp")) ? 0 : 1;
		data_.put("status", status);
		recommendService.updateBsiscnsl(data_, req.getRemoteAddr());
		res.setStatus(200);
	}
	
	@RequestMapping(value="/charts.do", method=RequestMethod.POST)
	@ResponseBody
	public void setCharts(HttpServletRequest req, HttpServletResponse res) {
		String EXEC_PIC = req.getParameter("exec_pic");
		String SPRT_PIC = req.getParameter("sprt_pic");
		int bsc_idx = Integer.valueOf((String)req.getParameter("bsc_idx"));
		Map charts = new HashMap<String, String>();
		charts.put("exec_pic", EXEC_PIC);
		charts.put("sprt_pic", SPRT_PIC);
		charts.put("bsc_idx", bsc_idx);
		recommendService.insertChart(charts);
	}
	
	@RequestMapping(value="/qrmanage.do")
	public String qrmanage(Model model) {
		List<QrVO> qrs = recommendService.getQR();
		model.addAttribute("qrs", qrs);
		return "/rbs/usr/recommend/qrmanage";
	}
	
	@RequestMapping(value="/updateQR.do", method=RequestMethod.POST)
	public void updateqr(@RequestBody String json, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> qrs = (List<Map>)mapper.readValue(json, List.class);
		recommendService.updateQR(qrs);
		res.setStatus(200);
	}
	
	@RequestMapping(value="/jwt.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String jwt(@RequestBody String json, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map gradients = (Map)mapper.readValue(json,  Map.class);
		String id = (String)gradients.get("id");
		String access_token = JwtUtil.createAccessToken(id);
		int c_num = 10*60;
		int r_num = 60*60;
		if(c_num > 10*60) {
			c_num = 10*60;
		}
		if(r_num > 60*60) {
			r_num = 60*60;
		}
		
		Cookie cookie = new Cookie("access-token", access_token);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setMaxAge(c_num);
		
		String refresh_token = JwtUtil.createRefreshToken(id);
		Cookie rcookie = new Cookie("refresh-token", refresh_token);
		rcookie.setHttpOnly(true);
		rcookie.setPath("/");
		rcookie.setSecure(true);
		rcookie.setMaxAge(r_num);
		
		res.addCookie(cookie);
		res.addCookie(rcookie);
		
		Map result = new HashMap<String,String>();
		result.put("status", "ok");
		result.put("id", id);
		result.put("accessToken", access_token);
		result.put("refreshToken", refresh_token);
		String rst = mapper.writeValueAsString(result);
		return rst;
	}
	
	
	@RequestMapping(value="/chat.do", method=RequestMethod.GET)
	public void handleSse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter writer;
		final HttpSession session = request.getSession(true);
		try {
			writer = response.getWriter();
			String assignedName = assginRandomName(session);
			writer.write("event: nameAssignment\n");
			writer.write("data: "+assignedName+"\n\n");
			writer.flush();
			clients.put(session, writer);
			while(true) {
				writer.flush();
				if(writer.checkError()) {
					clients.remove(session);
					break;
				}
				Thread.sleep(1000);
			}
		} catch(IOException e) {
			log.debug("IOEXception occurred!");
			clients.remove(session);
			clientsNames.remove(session);
		} catch(InterruptedException e) {
			log.debug("InterruptedException occurred!");
			Thread.currentThread().interrupt();
		}
	}
	
	@RequestMapping(value="/send.do", method=RequestMethod.POST)
	public @ResponseBody void sendMessage(@RequestParam("message") String message, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String sessionId = session.getId();
		String senderName = clientsNames.get(session);
		for(Map.Entry<HttpSession, PrintWriter> entry : clients.entrySet()) {
			PrintWriter writer = entry.getValue();
			if(entry.getKey().getId().equals(sessionId)) {
				writer.write("event: self\n");
			} else {
				writer.write("event: message\n");
			}
			writer.write("data: " + senderName + " : " + message + "\n\n");
			if(writer.checkError()) {
				clients.remove(session);
			}
		}
	}
}
