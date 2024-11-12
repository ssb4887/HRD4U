<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="frm" />
<jsp:useBean id="today" class="java.util.Date" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/evalInput.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<form id="${inputFormId}" name="${inputFormId}" method="post" target="submit_target" action="${contextPath}/web/consulting/applyProc.do?mId=122" enctype="multipart/form-data">
	<div class="contents-area pl0">
    	<div class="title-wrapper">
    		<h4 class="title-type01 fl ml0 mb0">
      	 		주치의 컨설팅 신청
    		</h4>
    		<p class="right word-type01 fr mt05">
    			<strong class="point-important">
    				*
    			</strong> 필수 입력
    		</p>
    	</div>
		<div class="board-write">
			<dl>
				<dt>
					<label for="bplNo">
						사업장 관리번호
						<strong class="point-important">*</strong>
					</label>
				</dt>
				<dd class="left" colspan="3">
					<div class="input-add-btns-wrapper04">
						<input type="text" id="id_bplNo" name="bplNo" value="${param.bplNo}" class="form-control" maxlength="11" title="사업장 관리번호" />
						<button type="button" id="findBplNo" class="btn-etc btn-m01 btn-color03" title="사업장 관리번호 조회">사업장 관리번호 조회</button>
					</div>
					<p class="word-notice02 mt10">
						사업장관리번호를 모르실 경우 관할 소속기관으로 문의하시기 바랍니다
					</p>
				</dd>
			</dl>
			<dl>
	            <dt>
	                <label for="corp_nm">
	                    	기업명 
	                    <strong class="point-important">*</strong>
	                </label>
	            </dt>
	            <dd class="left" colspan="3">
	                <input type="text" id="id_corpNm" name="corpNm" class="form-control" value="${param.corpNm}" title="기업명" />
	            </dd>
	        </dl>
	        <dl>
	            <dt>
	                <label for="postno">
	                    	주소 
	                    <strong class="point-important">*</strong>
	                </label>
	            </dt>
	            <dd class="left" colspan="3">
	                <div class="zipcode">
	                    <input type="text" name="zip" id="id_zip" class="text w90" placeholder="우편번호" maxlength="5" value="${param.zip}" title="우편번호" />
	                    <button type="button" class="btn-zipcode" onclick="execDaumPostcode();return false;" title="우편번호 찾기">우편번호 찾기</button>
	                </div>
	                <div class="address">
		                <input type="text" title="주소" id="id_bplAddr" name="bplAddr" value="${param.bplAddr}" class="w100 mt05">
		                <input type="text" title="상세 주소" id="id_bplAddrDtl" name="bplAddrDtl" value="${param.bplAddrDtl}" class="w100 mt05">
	                </div>
	            </dd>
	        </dl>
	        <dl>
	            <dt>
	                <label for="branch">
	                    	관할지부지사 
	                    <strong class="point-important">*</strong>
	                </label>
	            </dt>
	            <dd class="left" colspan="3">
                	<select id="id_cmptncBrffcIdx" name="cmptncBrffcIdx" title="소속기관">
						<option value="">소속기관 선택</option>
						<c:forEach var="instt" items="${insttList}">
							<option value="${instt.INSTT_IDX}"
								<c:if test="${instt.INSTT_IDX eq param.insttIdx}">selected</c:if>>
								<c:out value="${instt.INSTT_NAME}" />
							</option>
						</c:forEach>
					</select>
	            </dd>
	        </dl>
	        <dl>
	            <dt>
	                <label for="charger_nm">
	                   	 기업 담당자명 
	                    <strong class="point-important">*</strong>
	                </label>
	            </dt>
	            <dd class="left" colspan="3">
	                <input type="text" id="id_corpPicNm" name="corpPicNm" class="form-control" value="${corpInfo.chrgrNm }" title="기업 담당자 명" required>
	            </dd>
	        </dl>
	        <dl>
	            <dt>
	                <label for="charger_tel1">
	                    	연락처 
	                    <strong class="point-important">*</strong>
	                </label>
	            </dt>
	            <dd class="left" colspan="3">
	                <div class="input-email-wrapper type02">
	                    <input type="text" id="id_corpPicTelNo" name="corpPicTelNo" class="corpPicTelNo" value="" title="연락처 입력" onkeyup="validation();" oninput="formatPhoneNumber(this);" placeholder="연락처(000-0000-000)" size="30" maxlength="30" required>
						<span id="telError" style="display: none; color: red;"></span>
	                </div>
	            </dd>
	        </dl>
	        <dl>
	            <dt>
	                <label for="charger_email1">
	                    	담당자 이메일 
	                    <strong class="point-important">*</strong>
	                </label>
	            </dt>
	            <dd class="left" colspan="3">
	                <div class="input-email-wrapper type02">
	                    <input type="text" id="id_corpPicEmail" name="corpPicEmail" class="corpPicEmail w100" value="" title="이메일 입력" onkeyup="validation();" placeholder="이메일(@mail.com)" size="40" maxlength="40">
						<span id="emailError" style="display: none; color: red;"></span>	
	                </div>
	            </dd>
	        </dl>
	        <dl>
	            <dt><label for="recommendOrg">추천인 소속</label></dt>
	            <dd colspan="3">
	            	<div class="zipcode">
						<select id="id_recomendPsitn" name="recomendPsitn" title="추천인 소속">
							<option value="hrdk">한국산업인력공단</option>
							<option value="prof">산업현장교수</option>
							<option value="etc">기타</option>
							<option value="not">없음</option>
						</select>
		                <input type="text" id="id_recomendPsitnEtc" name="recomendPsitnEtc" class="form-control"/>
	                </div>
	            </dd>
	        </dl>
	        <dl>
	            <dt><label for="recommendNm">추천인성명(선택)</label></dt>
	            <dd class="left">
	                <input type="text" title="추천인성명" id="id_recomendNm" name="recomendNm" class="form-control" required>
	            </dd>
	        </dl>
	        <dl>
	            <dt><label for="recommendPhone">추천인 연락처(선택)</label></dt>
	            <dd>
	            	<div class="input-email-wrapper type02">
		                <input type="text" id="id_recomendTelno" name="recomendTelno" class="form-control w100" value="" title="추천인 연락처" onkeyup="validation();" oninput="formatPhoneNumber(this);" placeholder="연락처(000-0000-000)" size="30" maxlength="30" required>
						<span id="telError2" style="display: none; color: red;"></span>
					</div>
	            </dd>
	        </dl>
		</div>
	</div>
				
				
		<div class="contents-area pl0">
              <h5 class="title-type02 ml0">
                  	개인정보 수집 및 이용에 대한 안내
              </h5>


              <div class="agreement-area" tabindex="0">
                  <p style="font-size:20px; font-weight: 500; color: #212121;">회원약관</p>
                  <h4>제 1장 총칙</h4>
                  <ol>
                      <li>1. 수집하는 개인정보의 항목
                          <p>HRD4U는 회원가입, 원활한 고객상담, 각종 서비스의 제공을 위해 최초 회원가입 당시 아래와 같은 최소한의 <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;">개인정보를 필수항목으로 수집</span>하고 있습니다.</p>
                          <ul>
                              <li>- <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 성명, 생년월일, 성별, 이메일, 휴대전화번호 </span> </li>
                          </ul>
                      </li>
                      <li>2. 개인정보의 수집 및 이용 목적
                          <p> HRD4U는 회원님들의 개인정보를 수집하는 목적은 회원님들의 <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 신분 및 서비스를 이용하고자하는 의사를 확인하기 위함</span> 이며, 회원님에게 최적의 서비스를 제공하고자 하는 것입니다.
                              HRD4U는 공유서비스를 제공, 중계, 활용하기 위해 아래와 같이 수집정보별 목적에 따라 개인정보를 이용하고 있습니다. 다만, 이용목적은 수집목적을 벗어나지 않는 범위에서 <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;">추가 서비스제공을 위해 확장</span>될
                              수 있으며, 이 경우에는 미리 회원에게 그 사실을 알려 드립니다.</p>
                          <ul>
                              <li>- <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 성명, 생년월일, 이메일 </span> : 개인 회원 서비스를 제공하기 위함. 본인식별</li>
                              <li>- <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 휴대전화, 주소 </span> : 공지사항 전달, 본인 의사 확인, 불만처리 등의 의사소통 경로 확보, 개인화 서비스, 이벤트 당첨시 오프라인 배송지 주소 및 연락처 확보 등 </li>
                          </ul>
                      </li>
                      <li>
                          3. 개인정보의 보유 및 이용기간
                          <p>회원의 개인정보는 다음과 같이 개인정보의 수집목적 또는 제공받은 <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 목적이 달성되면 파기</span>됩니다.</p>
                          <ul>
                              <li>- 회원가입정보의 경우, <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 회원가입을 탈퇴하거나 회원에서 제명될 때 </span> </li>
                          </ul>
                      </li>
                      <li>4. 개인정보 수집에 대한 거부 권리 및 그에 따른 서비스 이용 제한
                          <p>회원님은 HRD4U에서 수집하는 개인정보에 대해 <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 정보제공을 거부할 권리 </span>가 있으나, 정보제공 동의 거부 시 <span style="font-size:14px; font-weight: 400; color: blue; padding:0px !important;"> 회원가입과 서비스 이용에 제한</span>을
                              받을 수 있습니다.</p>
                      </li>
                      <li><br><br>
                          <p>-휴대전화번호는 나이스 평가정보에서 인증받은 휴대폰 번호를 사용하고 있습니다.</p>
                      </li>
                  </ol>
              </div>
		
			<div class="right">
				<p class="word-agreement-checked">
					<input type="checkbox" id="agreement02" class="agreement checkbox-type01 name="" value="">
					<label for="agreement02">
						위 약관의 내용을 확인하고 동의합니다.
					</label>
				</p>
			</div>
		</div>
				<div class="btns-area">
			        <div class="btns-right">
			            <button type="button" onclick="fn_apply();" class="btn-m01 btn-color03 depth2">
			            	컨설팅 신청
			            </button>
			            <button type="button" onclick="history.back();" class="btn-m01 btn-color02 depth2">
			            	취소
			            </button>
			        </div>
			    </div>
			</form>
		</div>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>	
<script type="text/javascript">
	function execDaumPostcode() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var addr = ''; // 주소 변수
	            var extraAddr = ''; // 참고항목 변수
	
	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                 addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }
	
	            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	            if(data.userSelectedType === 'R'){
	                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있고, 공동주택일 경우 추가한다.
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                if(extraAddr !== ''){
	                    extraAddr = ' (' + extraAddr + ')';
	                }
	            
	            } 
	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('id_zip').value = data.zonecode;
	            document.getElementById("id_bplAddr").value = addr;
	            // 커서를 상세주소 필드로 이동한다.
	            document.getElementById("id_bplAddrDtl").focus();
	
            }
        }).open();
    }
	
	$(document).ready(function(){		
		$("#id_recomendPsitnEtc").hide();
		$("#id_recomendPsitn").change(function() {
			if(this.value == 'etc'){
				$("#id_recomendPsitnEtc").show();
			}else{
				$("#id_recomendPsitnEtc").hide();
			}
		});
		
		$("#id_bplNo").keyup(function() {
			if(this.value.indexOf("-") > 0){
				$("#id_bplNo").val(this.value.replace('-',''));
			}
		});
		
		$('#findSbcd').click( function() {
			location.href="/web/insttSearch/list.do?mId=40";
		});
		
		$("#findBplNo").on("click",function(){
			if($("#id_bplNo").val()==""){
				alert("사업장 관리번호를 입력해주세요.");
				$("#id_bplNo").focus(); return false;
			}else{
				$.ajax({
					url: `${contextPath}/web/consulting/findBizData.do?mId=122`,
					method: "POST",
					dataType : "JSON",
					data: {
						bplNo : $("#id_bplNo").val()
					},
					success: function(data) {
						var resultData = data.result.body;
						console.log(resultData)
						if(resultData == null){
							alert("조회된 내용이 없습니다.");
							$("#id_bplNo").val("");
						}else{
							var corpNm = resultData.BPL_NM;
							var zip = resultData.BPL_ZIP;
							var bplAddr = resultData.BPL_ADDR;
							var bplAddrDtl = resultData.ADDR_DTL;
							var instt = resultData.INSTT_IDX;
							var bplAreaNo = resultData.BPL_AREA_NO;
							var bplTelNo1 = resultData.BPL_TELNO1;
							var bplTelNo2 = resultData.BPL_TELNO2;
							var bplEmail = resultData.BPL_EMAIL;
							
							$("#id_corpNm").val(corpNm);
							$("#id_zip").val(zip);
							$("#id_bplAddr").val(bplAddr);
							$("#id_bplAddrDtl").val(bplAddrDtl);
							$("#id_cmptncBrffcIdx").val(instt).attr("selected","selected");
							if(bplAreaNo == null) {
								$("#id_corpPicTelNo").val("-"+bplTelNo1+"-"+bplTelNo2);
							} else {
								$("#id_corpPicTelNo").val(bplAreaNo+"-"+bplTelNo1+"-"+bplTelNo2);
							}
							$("#id_corpPicEmail").val(bplEmail);
						}
					}
				});								
			}
		});
	});
	
	/**
	 * 유효성 검사
	 */
	function validation() {
		let emailRegex = /^[0-9a-zA-Z._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		let telRegex = /^([0-9]{2,3})-?([0-9]{3,4})-?([0-9]{4})$/;
		
		// 휴대전화번호
			let telInput = document.querySelector("#id_corpPicTelNo");
			let telError = document.getElementById("telError");
			
			let telInput2 = document.querySelector("#id_recomendTelno");
			let telError2 = document.getElementById("telError2");
			
			let enteredTel = telInput.value;
			let enteredTel2 = telInput2.value;
			
			telError.style.display = 'block';
			telError2.style.display = 'block';
			
			if(!telRegex.test(enteredTel) && enteredTel.length != 0) {
				// telInput.style.border = "2px solid #1e89e9";
				telError.innerHTML = "유효한 연락처 형식이 아닙니다.";
				return false;
			}else {
				telInput.style.border = "1px solid #f1f3f5 font-size: 13px";
				telError.innerHTML = "";
				telError.style.display = 'none';
			}
			
			if(!telRegex.test(enteredTel2) && enteredTel2.length != 0) {
				// telInput.style.border = "2px solid #1e89e9";
				telError2.innerHTML = "유효한 연락처 형식이 아닙니다.";
				return false;
			}else {
				telInput2.style.border = "1px solid #f1f3f5 font-size: 13px";
				telError2.innerHTML = "";
				telError2.style.display = 'none';
			}
			
		
		// 이메일
			let emailInput = document.querySelector(".corpPicEmail");
			let emailError = document.getElementById("emailError");
			let enteredEmail = emailInput.value;
			
			emailError.style.display = 'block';
			
			if(!emailRegex.test(enteredEmail) && enteredEmail.length != 0) {
	// emailInput.style.border = "2px solid #1e89e9";
				emailError.innerHTML = "유효한 이메일 형식이 아닙니다.";
				return false;
			}else {
				emailInput.style.border = "1px solid #f1f3f5 font-size: 13px";
				emailError.innerHTML = "";
				emailError.style.display = 'none';
			}

	}

	// 기업검색시 사업장관리번호 입력값 제한 설정
	document.querySelector('#id_bplNo').oninput = function() {
		this.value = this.value.replace(/\D/g, '');
	}

	// 기업담당자 연락처 입력시 전화번호 입력 포맷팅
	function formatPhoneNumber(input) {
		let inputValue = input.value.replace(/\D/g, '');
		
		if(inputValue.length == 9) {
			input.value = inputValue.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
		}else if(inputValue.length == 10) {
			input.value = inputValue.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
		}else if(inputValue.length == 11) {
			input.value = inputValue.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
		}else {
			input.value = inputValue;
		}
	}
	
	//사업주 훈련신청
	function fn_apply(){
		var click = true;
		
		if($("#id_bplNo").val() == ""){
			alert("사업장 관리번호를 입력해주세요.");
			$("#id_bplNo").focus();
			return false;
		}
		
		if($("#id_corpNm").val() == ""){
			alert("기업 명을 입력해주세요.");
			$("#id_corpNm").focus();
			return false;
		}
		
		if($("#id_zip").val() == ""){
			alert("우편번호를 입력해주세요.");
			$("#id_zip").focus();
			return false;
		}
		
		if($("#id_bplAddr").val() == ""){
			alert("주소를 입력해주세요.");
			$("#id_bplAddr").focus();
			return false;
		}
		
		if($("#id_cmptncBrffcIdx option:selected").val() == ""){
			alert("소속기관을 선택해주세요.");
			$("#id_cmptncBrffcIdx").focus();
			return false;
		}
		
		if($("#id_corpPicNm").val() == ""){
			alert("기업 담당자명을 입력해주세요.");
			$("#id_corpPicNm").focus();
			return false;
		}
		
		if($("#id_corpPicTelNo").val() == ""){
			alert("연락처를 입력해주세요.");
			$("#id_corpPicTelNo").focus();
			return false;
		}
		
		if($("#id_corpPicEmail").val() == ""){
			alert("담당자 이메일을 입력해주세요.");
			$("#id_corpPicEmail").focus();
			return false;
		}
		
		if (!$("#agreement02").is(":checked")) {
			$("#agreement02").each(function() {
				if ( !$(this).is(":checked") ) {
					alert("개인정보 수집 및 이용에 대한 동의에 체크해주세요.");
					$(this).focus();
					return false;
				}
			});
			return false;
		}
		
		$("#frm").submit();
	}
	
	</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>