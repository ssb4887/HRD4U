package rbs.modules.basket.dto;


//공통 응답 Dto
public class CMRespDto {

	private Integer code;
	private String msg;
	private Object body;
	
	private CMRespDto() {}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Object getBody() {
		return body;
	}
	
	public static class Builder {
		private Integer code;
		private String msg;
		private Object body;
		
		public Builder(Integer code) {
			this.code = code;
		}
		
		public Builder setMsg(String msg) {
			this.msg = msg;
			return this;
		}
		
		public Builder setBody(Object body) {
			this.body = body;
			return this;
		}
		
		public CMRespDto build() {
			CMRespDto cmRespDto = new CMRespDto();
			cmRespDto.code = code;
			cmRespDto.msg = msg;
			cmRespDto.body = body;
			
			return cmRespDto;
		}
	}



	
	
}
