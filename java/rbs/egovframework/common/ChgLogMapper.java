package rbs.egovframework.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DTForm;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("chgLogMapper")
public class ChgLogMapper extends EgovAbstractMapper{
    
    public int writeDataLog(String targetTable,String executeType ,List<DTForm> searchList) {    	
    	
    	String colWhereValOneString = "";
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    		for(int i = 0; i < searchList.size(); i++) {
    			if(i == 0) {
    				colWhereValOneString += searchList.get(i).get("columnId") + "," + searchList.get(i).get("columnValue");
    			}else {
    				colWhereValOneString += "," + searchList.get(i).get("columnId") + "," + searchList.get(i).get("columnValue");
    			}
    			
    		}
    		param.put("targetTable", targetTable);
    		param.put("executeType", executeType);
    		param.put("condition", colWhereValOneString);
    		
    	
    	return (Integer)insert("rbs.commonMapper.writeDataLog", param);
    }
    
    
    public int writeDataLog(String targetTable,String executeType ,String queryCondition) {    	
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	
		param.put("targetTable", targetTable);
		param.put("executeType", executeType);
		param.put("condition", queryCondition);
    		
    	
    	return (Integer)insert("rbs.commonMapper.writeDataLogWithStringifyQuery", param);
    }
	
}
