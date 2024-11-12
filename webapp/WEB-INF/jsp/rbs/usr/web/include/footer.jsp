	<!-- footer -->
<%@page import="com.woowonsoft.egovframework.util.MenuUtil"%>
<footer>
<div class="footer">
	<div class="footer-left-wrapper">
		<ul class="footer-menu">
			<li>
				<a href="https://hrd4u.or.kr/hrd4u_new/contents.do?menuNo=0610" target="_blank">개인정보처리방침</a>
			</li>
			<li>
				<a href="https://hrd4u.or.kr/hrd4u_new/contents.do?menuNo=0613" target="_blank">저작권보호정책</a>
			</li>
			<li>
				<a href="https://hrd4u.or.kr/portal/main/contents.do?menuNo=200022" target="_blank">오시는길</a>
			</li>
		</ul>
		<address>
			(44538) 울산광역시 중구 종가로 345(교동) 한국산업인력공단 한국산업인력공단 고객센터 : 1644-8000
		</address>
		<p>
			고객센터(사이트 이용 문의) 052-714-8288 이메일 hrdportal@hrdkorea.or.kr
		</p>
		<p class="copyright">
			COPYRIGHT 2023 HRD4U.OR.KR ALL RIGHTS RESERVED.
		</p>
	</div>
	<div class="footer-right-wrapper">
		<div class="footer-site-wrapper">
			<select id="select-agency01" name="">
				<option value="">
					관련사이트
				</option>
				<option value="https://hrd4u.or.kr/hrd4u/main.do">
					HRD4U 메인
				</option>
				<option value="https://hrd4u.or.kr/portal/main.do">
					HRD 콘텐츠
				</option>
				<option value="https://hrd4u.or.kr/hrdfestival/main.do">
					직업능력의 달
				</option>
				<option value="https://hrd4u.or.kr/hrdconference/main.do">
					인적자원개발컨퍼런스
				</option>
				<option value="https://hrd4u.or.kr/expertpool/main.do">
					전문가인력풀
				</option>
				<option value="https://hrd4u.or.kr/studyorg/main.do#">
					학습조직화 사업
				</option>
				<option value="https://hrd4u.or.kr/hrdcert/main.do">
					인적자원개발 우수기관인증
				</option>
				<option value="https://hrd4u.or.kr/hrdesgsprt"> 
					청년친화형 기업 ESG 지원사업
				</option>
				<option value="https://hrd4u.or.kr/sojt"> 
					현장맞춤형 체계적훈련지원사업
				</option>
			</select>
			<button type="button" onclick="go_url01()">
				이동
			</button>
		</div>
	</div>
	<!-- 상단으로 이동 -->
	<a href="#btn-top-go" class="btn-top-go">TOP</a>
	<a href="javascript:history.back(-1)" class="btn-mobile-back">BACK</a>
</div>
	<!-- //footer -->
	<%@ include file="../../../include/login_check.jsp"%>
</footer>
