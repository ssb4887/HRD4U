package rbs.modules.kedCntc.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;


public class batchLogVO {
	private long    OPERT_IDX  = 0 ;
	private String  OPERT_PROGRM_NM = "";   //  HRD_COM_BATCH_LOG.OPERT_PROGRM_NM%TYPE,
	private String  OPERT_DC = "" ;        //        HRD_COM_BATCH_LOG.OPERT_DC%TYPE,
	private String  OPERT_PARAMTR;     //    HRD_COM_BATCH_LOG.OPERT_PARAMTR%TYPE,
	private int     OPERT_CO = 0;         //        HRD_COM_BATCH_LOG.OPERT_CO%TYPE,
	private String  OPERT_RESULT = "";     //    HRD_COM_BATCH_LOG.OPERT_RESULT%TYPE,
	private String  RESULT_MSSAGE = "";    //    HRD_COM_BATCH_LOG.RESULT_MSSAGE%TYPE,
	private Timestamp   OPERT_START_DT = null;    //   HRD_COM_BATCH_LOG.OPERT_START_DT%TYPE,
	private Timestamp   OPERT_END_DT  = null;      //     HRD_COM_BATCH_LOG.OPERT_END_DT%TYPE
	
    public batchLogVO (String OPERT_PROGRM_NM, String OPERT_DC, String OPERT_PARAMTR, Timestamp OPERT_START_DT ) {
    	this.OPERT_PROGRM_NM = OPERT_PROGRM_NM;
    	this.OPERT_DC        = OPERT_DC;
    	this.OPERT_PARAMTR   = OPERT_PARAMTR;
    	this.OPERT_START_DT  = OPERT_START_DT;
    }
    
    public void setOPERT_IDX(long idx) {
    	this.OPERT_IDX = idx ;
    }
    
    public long getOPERT_IDX() {
    	return OPERT_IDX ;
    }
    
    public void setOPERT_PROGRM_NM(String programName) {
    	this.OPERT_PROGRM_NM = programName ;
    }
    
    public String getOPERT_PROGRM_NM() {
    	return OPERT_PROGRM_NM ;
    }

    public void setOPERT_DC(String programDesc) {
    	this.OPERT_DC = programDesc ;
    }
    
    public String getOPERT_DC() {
    	return OPERT_DC ;
    }
    
    public void setOPERT_PARAMTR(String paramter) {
    	this.OPERT_PARAMTR = paramter ;
    }
    
    public String getOPERT_PARAMTR() {
    	return OPERT_PARAMTR ;
    }
	    
    public void setRESULT_MSSAGE(String resultMsg) {
    	this.RESULT_MSSAGE = resultMsg ;
    }
    
    public String getRESULT_MSSAGE() {
    	return RESULT_MSSAGE  ;
    }
    
    public void setOPERT_RESULT(String result) {
    	this.OPERT_RESULT = result ;
    }
    
    public String getOPERT_RESULT() {
    	return OPERT_RESULT  ;
    }
    
    
    public void setOPERT_CO(int procCnt) {
    	this.OPERT_CO = procCnt ;
    }
    
    public int getOPERT_CO() {
    	return OPERT_CO  ;
    }
    
    public void setOPERT_START_DT(Timestamp startDt) {
    	this.OPERT_START_DT = startDt ;
    }
    
    public Timestamp getOPERT_START_DT() {
    	return OPERT_START_DT  ;
    }
    
    public void setOPERT_END_DT(Timestamp endDt) {
    	this.OPERT_END_DT = endDt ;
    }
    
    public Timestamp getOPERT_END_DT() {
    	return OPERT_END_DT  ;
    }
   
    
    
    
}
