<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="memberInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/agree.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
		<jsp:param name="joinStep" value="1"/>
	</jsp:include>
</c:if>
<form name="${inputFormId}" id="${inputFormId}" method="post" action="<c:out value="${URL_AGREEPROC}"/>">
	<div class="contents-area">
	    <div class="contents-box">
	        <h3 class="title-type01">
	            	개인정보 수집&middot;이용 동의서 및 보안서약서
	        </h3>
	        <p class="word-type01 mb20 left">
	            <strong class="">한국산업인력공단은 <span class="underline">현장맞춤형 체계적훈련지원사업 참여</span></strong>를 위하여 귀하의 소중한 <strong class="">개인정보(고유식별정보 포함)</strong>를 <strong class="">수집&middot;이용</strong>하고자
	            	하오니 아래의 내용을 확인하신 후 <strong class="">동의 여부</strong>를 결정하여 주시기 바랍니다.
	        </p>
	    </div>
	
	    <div class="contents-box">
	        <h4 class="title-type02">
	            	개인정보 수집 및 이용 동의
	        </h4>
	
	        <div class="agreement-area" style="justify-content: right;">
	            <div class="table-type02 horizontal-scroll">
	                <table>
	                    <caption>
	                        	개인정보 수집 및 이용 동의표 : 수집항목, 수집&middot;이용 목적, 보유기간에 관한 정보 제공표
	                    </caption>
	                    <colgroup>
	                        <col style="width: 45%" />
	                        <col style="width: 45%" />
	                        <col style="width: 20%" />
	                    </colgroup>
	                    <thead>
	                        <tr>
	                            <th scope="col">
	                               	 수집항목
	                            </th>
	                            <th scope="col">
	                                	수집&middot;이용 목적
	                            </th>
	                            <th scope="col">
	                                	보유기간
	                            </th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr>
	                            <td>
	                                	성명, 소속기관, 직위, 주소, 전화번호(휴대폰/사무실), 계좌정보(은행명, 계좌번호), 학력, 경력, 자격, HRD4U ID
	                            </td>
	                            <td>
	                                	현장맞춤형 체계적훈련지원 사업참여 확인 및 수당 지급
	                            </td>
	                            <td>
	                                <strong class=" underline">신청일부터 10년</strong>
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	
	            <p class="word-notice01 mt10">
	                	위의 개인정보 수집‧이용에 대한 동의를 거부할 권리가 있습니다. 그러나 개인정보 수집․이용에 대하여 동의를 거부할 경우 수당 지급이 제한될 수 있습니다.
	            </p>
	        </div>
	
	        <div class="agree-check-box right">
	            <p class="word-type03 mr15">정보 수집 및 이용에 동의합니다.</p>
	            <div class="input-radio-wrapper">
	                <div class="input-radio-area">
	                    <input type="radio" id="y_agreement01" name="agreement01" value="Y" title="개인정보 수집 및 이용 동의" class="radio-type01">
	                    <label for="y_agreement01">
	                        	동의함
	                    </label>
	                </div>
	
	                <div class="input-radio-area">
	                    <input type="radio" id="n_agreement01" name="agreement01" value="N" title="개인정보 수집 및 이용 동의안함" class="radio-type01">
	                    <label for="n_agreement01">
	                        	동의안함
	                    </label>
	                </div>
	            </div>
	        </div>
	    </div>
	
	
	    <div class="contents-box">
	        <h4 class="title-type02">
	            	동의 없이 수집&middot;이용하는 개인정보 내역 고지
	        </h4>
	
	        <div class="agreement-area">
	            <p class="mb10">
	                	「개인정보 보호법」제15조제1항제2호에 따라 아래의 사항을 처리하기 위해 아래 수집근거에 의해 <strong class="">주민등록번호</strong>를 <strong class=""></strong>귀하의 동의 없이 수집·이용할 수 있음</strong>을 알려드립니다.
	            </p>
	            <div class="table-type02 horizontal-scroll">
	                <table>
	                    <caption>
	                        	동의 없이 수집&middot;이용하는 개인정보 내역 고지표 : 개인정보 항목, 수집&middot;이용 목적, 수집 근거에 관한 정보 제공표
	                    </caption>
	                    <colgroup>
	                        <col style="width: 20%" />
	                        <col style="width: 35%" />
	                        <col style="width: 45%" />
	                    </colgroup>
	                    <thead>
	                        <tr>
	                            <th scope="col">
	                                	개인정보 항목
	                            </th>
	                            <th scope="col">
	                                	수집&middot;이용 목적
	                            </th>
	                            <th scope="col">
	                                	수집 근거
	                            </th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr>
	                            <td>
	                                <strong class=" underline">
	                                    	주민등록번호
	                                </strong>
	                            </td>
	                            <td>
	                                	현장맞춤형 체계적훈련지원사업 관련 경력확인, 수당지급 및 소득세 신고
	                            </td>
	                            <td>
	                                	｢국세기본법｣시행령 제68조(민감정보 및 고유식별정보의 처리).<br /> ｢소득세법｣ 제21조(기타소득), 제127조(원천징수의무)
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	        </div>
	
	        <div class="agree-check-box right">
	            <p class="word-type03 mr15">개인정보 수집&middot;이용에 동의하십니까? </p>
	            <div class="input-radio-wrapper">
	                <div class="input-radio-area">
	                    <input type="radio" id="y_agreement02" name="agreement02" value="Y" title="동의 없이 수집·이용하는 개인정보 내역 고지 동의" class="radio-type01">
	                    <label for="y_agreement02">
	                        	동의함
	                    </label>
	                </div>
	
	                <div class="input-radio-area">
	                    <input type="radio" id="n_agreement02" name="agreement02" value="N" title="동의 없이 수집·이용하는 개인정보 내역 고지 동의안함" class="radio-type01">
	                    <label for="n_agreement02">
	                        	동의안함
	                    </label>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="contents-box">
	        <h4 class="title-type02">
	            	외부위원 위촉관련 정보공유 동의서
	        </h4>
	
	        <div class="agreement-area">
	            <p class="mb10">
	                	공단 내 타 사업 위촉 시 활용하고자 위촉배제 등 특이사항 발생 시 귀하의 능력개발사업 관련정보를 공단 내 타 사업에 제공하고자 함을 알려드립니다.
	            </p>
	            <div class="table-type02 horizontal-scroll">
	                <table>
	                    <caption>
	                       	 외부위원 위촉관련 정보공유 동의서 정보표 : 제공받는 기관(공단 내 타 사업 담당자 - 능력개발사업, 능력평가사업, 국가직무능력 표준사업, 숙련기술진흥사업), 수집&middot;이용&middot;제공 목적, 수집&middot;이용&middot;제공할 개인정보의 항목, 정보를 제공받는 자의 개인정보 보유 및 이용기간, 제공주기에 관한 정보 제공표
	                    </caption>
	                    <colgroup>
	                        <col style="width: 20%" />
	                        <col style="width: 30%" />
	                        <col style="width: 25%" />
	                        <col style="width: 25%" />
	                        <col style="width: 25%" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" rowspan="2">
	                                    	제공받는 기관 (공단 내 타 사업 담당자)
	                                </th>
	                                <th scope="row">
	                                    	능력개발사업
	                                </th>
	                                <th scope="row">
	                                    	능력평가사업
	                                </th>
	                                <th scope="row">
	                                    	국가직무능력표준사업
	                                </th>
	                                <th scope="row">
	                                    	숙련기술진흥사업
	                                </th>
	                            </tr>
	                            <tr>
	                                <td>
	                                    	인적자원개발 우수기관 인증, 직업방송 송출 및 운영, HRD콘텐츠 서비스, 사업주 직업능력개발 훈련 지원, 체계적 현장훈련, 중소기업훈련지원센터, 중소기업 학습조직화 지원, 일학습병행 사업, 컨소시엄사업 사업, 지역산업 맞춤형 인력양성, 산업별 인적자원개발위원회(ISC) 사업 담당자
	                                </td>
	                                <td>
	                                    	국가기술자격, 과정평가자격, 전문자격 출제, 시행, 채점 담당자
	                                </td>
	                                <td>
	                                    NCS 개발·개선, NCS활용 컨설팅, NCS품질관리 담당자
	                                </td>
	                                <td>
	                                    	숙련기술전수, 대한민국명장, 우수숙련기술자, 숙련기술전수자, 숙련기술장려모범사업체 등 선정·포상, 숙련기술인 창업지원, 산업현장교수제도 운영 지원, 기능경기(국내&middot;국제)
	                                </td>
	                            </tr>
	                            <tr>
	                                <th scope="row">
	                                    	수집&middot;이용&middot;제공 목적
	                                </th>
	                                <td colspan="4">
	                                    	공단 내 능력개발, 능력평가, 국가직무능력표준, 숙련기술진흥 사업에 공유 및 위촉여부에 활용
	                                </td>
	                            </tr>
	                            <tr>
	                                <th scope="row">
	                                    	수집&middot;이용&middot;제공할 개인정보의 항목
	                                </th>
	                                <td colspan="4">
	                                    	성명, 생년월일, <strong class="">위촉배제기간</strong>, 위촉배제사유, 직업능력개발사업 업무수행 평가결과
	                                </td>
	                            </tr>
	                            <tr>
	                                <th scope="row">
	                                    	정보를 제공받는 자의 개인정보 보유 및 이용기간
	                                </th>
	                                <td colspan="4">
	                                    <strong class=" underline">
	                                        	현장맞춤형 체계적훈련지원사업의 위촉배제 기간
	                                    </strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th scope="row">
	                                    	제공주기
	                                </th>
	                                <td colspan="4">
	                                    	위촉배제 사유 발생 시
	                                </td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	            </div>
	
	            <div class="agree-check-box right">
	                <p class="word-type03 mr15">개인정보 수집&middot;이용에 동의하십니까?
	                </p>
	                <div class="input-radio-wrapper">
	                    <div class="input-radio-area">
	                        <input type="radio" id="y_agreement03" name="agreement03" value="Y" title="외부위원 위촉관련 정보공유 동의서 동의" class="radio-type01">
	                        <label for="y_agreement03">
	                            	동의함
	                        </label>
	                    </div>
	
	                    <div class="input-radio-area">
	                        <input type="radio" id="n_agreement03" name="agreement03" value="N" title="외부위원 위촉관련 정보공유 동의서 동의안함" class="radio-type01">
	                        <label for="n_agreement03">
	                            	동의안함
	                        </label>
	                    </div>
	                </div>
	            </div>
	        </div>
	
	        <div class="contents-box">
	            <h4 class="title-type02">
	                	현장맞춤형 체계적훈련지원사업 참여서약서
	            </h4>
	
	            <div class="agreement-area">
	                <p class="mb10">
	                    	현장맞춤형 체계적훈련지원사업에 참여하기 위하여 다음 사항을 준수하여야 함을 알려드립니다.
	                </p>
	                <div class="table-type02">
	                    <table>
	                        <caption>
	                            	현장맞춤형 체계적훈련지원사업 참여서약서표 : 준수사항에 관한 정보 제공표
	                        </caption>
	                        <thead>
	                            <tr>
	                                <th scope="col">
	                                    	준수사항
	                                </th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                            <tr>
	                                <td class="left">
	                                    <p class="mb10">
	                                        	본인은 202X년도 현장맞춤형 체계적훈련지원사업에 외부전문가로 참여하여 담당 기업의 컨설팅 또는 훈련이 완수될 수 있도록 아래 사항을 준수한다.
	                                    </p>
	
	                                    <ul class="ul-list01">
	                                        <li class="mb10">
	                                            	외부전문가는 지원기관(한국산업인력공단 또는 중소기업훈련지원센터) 담당자의 안내에 따라 각종 행정처리 및 단계별 업무처리 기한을 준수하여 과업을 완수한다. 그 과정에서 변경사항 발생 시 단계별 변경 신고 기한에 따라 지원기관(한국산업인력공단 또는 중소기업훈련지원센터)이 인지할 수 있도록 변경사항을 신고해야 한다.
	                                        </li>
	                                        <li class="mb10">
	                                            	담당기업의 요구를 반영하여 컨설팅을 수행해야 하며, 컨설팅 내용은 직업능력개발훈련과 연계되는 내용으로 구성하여야 한다.
	                                        </li>
	                                        <li class="mb10">
	                                            	체계적 현장훈련(S-OJT)의 경우 과정개발 컨설팅 최종 종료일로부터 1개월 이내에 훈련을 실시해야 한다. 1개월 이내 훈련이 실시되지 않을 경우 지원기관(한국산업인력공단 또는 중소기업훈련지원센터)에서는 해당 외부전문가를 교체할 수 있다. (단, 기업의 요청이 있는 경우 지원기관의 승인을 통해 연장할 수 있다)
	                                        </li>
	                                        <li class="mb10">
	                                            	과정개발 컨설팅보고서를 허위로 작성하거나 타 보고서에 기재된 내용을 표절하여 활용한 경우, 추후 사업참여 제한 및 수당 미지급 등 불이익을 감수한다.
	                                        </li>
	                                        <li class="mb10">
	                                            	사업참여 과정에서 취득한 기업 내부 정보에 대해 절대 비밀을 준수하며, 기업 내부 정보 유출시 민‧형사상 책임을 진다.
	                                        </li>
	                                        <li>
	                                           	 각종 보고서(훈련 과정 포함)의 소유권은 한국산업인력공단으로 귀속한다.
	                                        </li>
	
	                                    </ul>
	                                </td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	            </div>
	
	            <div class="agree-check-box right">
	                <p class="word-type03 mr15">사업참여 서약에 동의하십니까?</p>
	                <div class="input-radio-wrapper">
	                    <div class="input-radio-area">
	                        <input type="radio" id="y_agreement04" name="agreement04" value="Y" title="현장맞춤형 체계적훈련지원사업 참여서약서 동의" class="radio-type01">
	                        <label for="y_agreement04">
	                            	동의함
	                        </label>
	                    </div>
	
	                    <div class="input-radio-area">
	                        <input type="radio" id="n_agreement04" name="agreement04" value="N" title="현장맞춤형 체계적훈련지원사업 참여서약서 동의안함" class="radio-type01">
	                        <label for="n_agreement04">
	                            	동의안함
	                        </label>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	
	    <div class="btns-area">
	        <button type="submit" id="btn-step-next" name="" class="btn-b01 btn-color03 round01 pl0 pr0">
	            	확인
	        </button>
	    </div>
	</form>
	<!-- //CMS 끝 -->

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>