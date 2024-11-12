package rbs.modules.plagiarism;

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
import org.springframework.scheduling.annotation.Scheduled;
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
import com.woowonsoft.egovframework.annotation.ModuleMapping;

import rbs.egovframework.util.JwtUtil;

@Controller
@RequestMapping("/plagiarism")
public class PlagiarismController {
	@Autowired
	PlagiarismService plagiarismService;
	@Autowired
	PlagiarismCore core;
		
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PlagiarismController.class);
	
	@RequestMapping(value="/test.do", method=RequestMethod.GET)
	public String test() {
		return "/rbs/usr/recommend/plagiarism";
	}
	
	@RequestMapping(value="/test.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String test2(String source) throws JsonProcessingException {
		List<Map> result = core.run("225", source);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
}
