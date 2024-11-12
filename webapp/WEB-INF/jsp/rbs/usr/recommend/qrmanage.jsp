<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="cPath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="ko">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<!-- url공유 썸네일 이미지 지정-->
	<meta property="og:image" content="${cPath}/pub/img/sns.png">
	<link rel="image_src" href="${cPath}/pub/img/sns.png" />
	<meta name="twitter:image" content="${cPath}/pub/img/sns.png" />
	<meta name="nate:image" content="${cPath}/pub/img/sns.png" />

	<!-- url공유 썸네일 이미지 지정-->

	<meta name="subject" content="기업직업훈련 지원시스템" />
	<meta name="author" content="기업직업훈련 지원시스템" />
	<meta name="keywords" content="기업직업훈련 지원시스템" />
	<meta name="description" content="기업직업훈련 지원시스템" />

	<meta property="og:type" content="website">
	<meta property="og:title" content="기업직업훈련 지원시스템">
	<meta property="og:site_name" content="기업직업훈련 지원시스템">
	<meta property="og:description" content="기업직업훈련 지원시스템">
	<meta property="og:image" content="http://ch.ac.kr/img/sns.png">
	<meta property="og:url" content="" />

	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css" />

	<link rel="stylesheet" href="${cPath }/assets/css/reset_pc.css">
	<link rel="stylesheet" href="${cPath}/assets/css/board.css">

	<link rel="stylesheet" href="${cPath}/pub/css/owl.carousel.min.css">
	<link rel="stylesheet" href="${cPath}/pub/css/common.css">
	<link rel="stylesheet" href="${cPath}/pub/css/contents.css">
	<link rel="stylesheet" href="${cPath}/pub/css/popup.css">

	<script src="${cPath}/assets/js/jquery.min.js"></script>
	<script src="${cPath}/assets/js/jquery.easing.1.3.js"></script>
	<script src="${cPath}/assets/js/jquery-migrate-1.2.1.min.js"></script>

	<script src="${cPath}/pub/js/owl.carousel.min.js"></script>
	<script src="${cPath}/pub/js/common.js"></script>
	<script src="${cPath}/pub/js/popup.js"></script>


	<script src="${cPath}/assets/js/jquery-ui.min.js"></script>
	<script src="${cPath}/assets/js/calendar.js"></script>
	<script src="${cPath}/assets/js/jquery.ui.monthpicker.js"></script>

	<link rel="stylesheet" href="${cPath }/assets/js/jquery-ui.min.css">
	<link rel="stylesheet" href="${cPath }/assets/js/datepicker-custom.css" type="text/css">
        <title>
            기초진단 QR관리 &lt; 기업진단 &lt; 기업직업훈련 지원시스템 (관리자)
        </title>
</head>

<body>
    <!-- skip navigation -->
    <p class="skip-navigation">
        <a href="#contents">본문 바로가기</a>
    </p>
    <!-- //skip navigation -->

    <!-- wrapper -->
    <div class="wrapper" id="wrapper">

        <!-- header -->
        <header>
<div class="header" id="header">
    <!-- gnb wrapper -->
    <div class="gnb-wrapper">
        <div class="gnb-area">

            <div class="right-gnb-list">
                <ul>
                    <li>
                        <a href="#">
                            로그인                
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- //gnb wrapper -->

    <div class="header-wrapper">
        <div class="bg"></div>
        <div class="header-area">
            <h1>
                <a href="../main/index.html">
                    <img src="${cPath}/pub/img/common/logo01_white.svg" alt="" />

                    <strong>
                        기업직업훈련 지원시스템
                    </strong>
                </a>
            </h1>

            <div class="top-menu-wrapper">
				<ul>
					<li>
						<a href="#" class="topmenu1">
							<span class="title">
								<span>
									기업진단
								</span>
							</span>
						</a>
						<div class="top-submenu topmenu1">
							<h2>
								<a href="#" class="topmenu1">
									기업진단
									<span class="arrow"></span>
								</a>
							</h2>
							<ul>
								<li>
									<a href="#" class="topmenu1-1">
										<span class="title">기초진단</span>
										<span class="arrow"></span>
									</a>
									<ul class="topmenu1-1">
										<li>
											<a href="#" class="topmenu1-1-1">
												<span class="title">기초진단 관리</span>
											</a>
										</li>
										<li>
											<a href="#" class="topmenu1-1-2">
												<span class="title">기초진단지</span>
											</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="#" class="topmenu1-2">
										<span class="title">기업HRD이음컨설팅</span>
										<span class="arrow"></span>
									</a>
									<ul class="topmenu1-2">
										<li>
											<a href="#" class="topmenu1-2-1">
												<span class="title">기업HRD이음컨설팅 신청</span>
											</a>
										</li>
										<li>
											<a href="#" class="topmenu1-2-2">
												<span class="title">기업HRD이음컨설팅 관리</span>
											</a>
										</li>
									</ul>
								</li>
							</ul>
						</div>
					</li>

					<li>
						<a href="#" class="topmenu2">
							<span class="title">
								<span>
									기초진단
								</span>
							</span>
						</a>
						<div class="top-submenu topmenu2">
							<h2>
								<a href="#" class="topmenu2">
									기업진단
									<span class="arrow"></span>
								</a>
							</h2>
							<ul>
								<li>
									<a href="#" class="topmenu2-1">
										<span class="title">QR 및 훈련동향 관리</span>
										<span class="arrow"></span>
									</a>
									<ul class="topmenu2-1">
										<li>
											<a href="#" class="topmenu2-1-1">
												<span class="title">기초진단 QR 관리</span>
											</a>
										</li>
										<li>
											<a href="#" class="topmenu2-1-2">
												<span class="title">최근훈련동향 관리</span>
											</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="#" class="topmenu2-2">
										<span class="title">참여가능사업</span>
									</a>
								</li>
							</ul>
						</div>
					</li>

					<li>
						<a href="#" class="topmenu3">
							<span class="title">
								<span>
									기업바구니
								</span>
							</span>
						</a>
						<div class="top-submenu topmenu3">
							<h2>
								<a href="#" class="topmenu3">
									기업바구니
									<span class="arrow"></span>
								</a>
							</h2>
							<ul>
								<li>
									<a href="#" class="topmenu3-1">
										<span class="title">기업 관리</span>
										<span class="arrow"></span>
									</a>
									<ul class="topmenu3-1">
										<li>
											<a href="#" class="topmenu3-1-1">
												<span class="title">기업 정보 조회</span>
											</a>
										</li>
										<li>
											<a href="#" class="topmenu3-1-2">
												<span class="title">기업 분류 관리</span>
											</a>
										</li>
										<li>
											<a href="#" class="topmenu3-1-3">
												<span class="title">기업 해시태그 관리</span>
											</a>
										</li>
										<li>
											<a href="#" class="topmenu3-1-4">
												<span class="title">기업 데이터 업로드</span>
											</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="#" class="topmenu3-2">
										<span class="title">분류 관리</span>
										<span class="arrow"></span>
									</a>
									<ul class="topmenu3-2">
										<li>
											<a href="#" class="topmenu3-2-1">
												<span class="title">분류 관리</span>
											</a>
										</li>
										<li>
											<a href="#" class="topmenu3-2-2">
												<span class="title">분류 예약</span>
											</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="#" class="topmenu3-3">
										<span class="title">해시태그 관리</span>
									</a>
								</li>
							</ul>
						</div>
					</li>
				</ul>
            </div>

            <div class="right-btns">
                <button type="button" class="btn-totalmenu">
                    <span class="menu">
                        <span></span>
                        <span></span>
                        <span></span>
                    </span>
                </button>
            </div>
        </div>
    </div>
</div>


<!-- 모바일 전체메뉴 -->
<div class="mask-totalmenu"></div>
<div class="totalmenu-wrapper">
    <div class="mobile-gnb-wrapper">
        <ul class="gnb-menu">
            <li>
                <a href="#">
                    <img src="${cPath}/pub/img/icon/icon_rock.png" alt="" />
                    <strong>
                        로그아웃
                    </strong>
                </a>
            </li>
        </ul>
    </div>


    <div class="totalmenu-area">
		<ul>
			<li>
				<a href="#" class="topmenu1">
					<span class="title">
						<span>
							기업진단
						</span>
					</span>
				</a>
				<div class="top-submenu topmenu1">
					<h2>
						<a href="#" class="topmenu1">
							기업진단
							<span class="arrow"></span>
						</a>
					</h2>
					<ul>
						<li>
							<a href="#" class="topmenu1-1">
								<span class="title">기초진단</span>
								<span class="arrow"></span>
							</a>
							<ul class="topmenu1-1">
								<li>
									<a href="#" class="topmenu1-1-1">
										<span class="title">기초진단 관리</span>
									</a>
								</li>
								<li>
									<a href="#" class="topmenu1-1-2">
										<span class="title">기초진단지</span>
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#" class="topmenu1-2">
								<span class="title">기업HRD이음컨설팅</span>
								<span class="arrow"></span>
							</a>
							<ul class="topmenu1-2">
								<li>
									<a href="#" class="topmenu1-2-1">
										<span class="title">기업HRD이음컨설팅 신청</span>
									</a>
								</li>
								<li>
									<a href="#" class="topmenu1-2-2">
										<span class="title">기업HRD이음컨설팅 관리</span>
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</li>

			<li>
				<a href="#" class="topmenu2">
					<span class="title">
						<span>
							기초진단
						</span>
					</span>
				</a>
				<div class="top-submenu topmenu2">
					<h2>
						<a href="#" class="topmenu2">
							기업진단
							<span class="arrow"></span>
						</a>
					</h2>
					<ul>
						<li>
							<a href="#" class="topmenu2-1">
								<span class="title">QR 및 훈련동향 관리</span>
								<span class="arrow"></span>
							</a>
							<ul class="topmenu2-1">
								<li>
									<a href="#" class="topmenu2-1-1">
										<span class="title">기초진단 QR 관리</span>
									</a>
								</li>
								<li>
									<a href="#" class="topmenu2-1-2">
										<span class="title">최근훈련동향 관리</span>
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#" class="topmenu2-2">
								<span class="title">참여가능사업</span>
							</a>
						</li>
					</ul>
				</div>
			</li>

			<li>
				<a href="#" class="topmenu3">
					<span class="title">
						<span>
							기업바구니
						</span>
					</span>
				</a>
				<div class="top-submenu topmenu3">
					<h2>
						<a href="#" class="topmenu3">
							기업바구니
							<span class="arrow"></span>
						</a>
					</h2>
					<ul>
						<li>
							<a href="#" class="topmenu3-1">
								<span class="title">기업 관리</span>
								<span class="arrow"></span>
							</a>
							<ul class="topmenu3-1">
								<li>
									<a href="#" class="topmenu3-1-1">
										<span class="title">기업 정보 조회</span>
									</a>
								</li>
								<li>
									<a href="#" class="topmenu3-1-2">
										<span class="title">기업 분류 관리</span>
									</a>
								</li>
								<li>
									<a href="#" class="topmenu3-1-3">
										<span class="title">기업 해시태그 관리</span>
									</a>
								</li>
								<li>
									<a href="#" class="topmenu3-1-4">
										<span class="title">기업 데이터 업로드</span>
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#" class="topmenu3-2">
								<span class="title">분류 관리</span>
								<span class="arrow"></span>
							</a>
							<ul class="topmenu3-2">
								<li>
									<a href="#" class="topmenu3-2-1">
										<span class="title">분류 관리</span>
									</a>
								</li>
								<li>
									<a href="#" class="topmenu3-2-2">
										<span class="title">분류 예약</span>
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#" class="topmenu3-3">
								<span class="title">해시태그 관리</span>
							</a>
						</li>
					</ul>
				</div>
			</li>
		</ul>
    </div>


    <button type="button" class="btn-mobile-close">
        <img src="${cPath}/pub/img/common/btn_totalmenu_close_mobile.png"alt="전체메뉴 닫기"/>
    </button>
</div>
<!-- //모바일 전체메뉴 -->
        </header>
        <!-- //header -->

        <!-- container -->
        <section>
            <div class="container" id="container">
                <!-- contents navigation, content options -->
<div class="contents-navigation-wrapper">
    <div class="contents-navigation">
        <a href="#" class="home">
            <span>HOME</span>
        </a>
        <ul>
            <li>
                <button type="button">기업진단</button>
                <ul>
                    <li>
                        <a href="#" class="topmenu1">
                            <strong>기업진단</strong>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="topmenu2">
                            <strong>기초진단</strong>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="topmenu3">
                            <strong>기초진단 신청</strong>
                        </a>
                    </li>
                </ul>
            </li>

            <li>
                <button type="button">기초진단 관리</button>

                <ul>
                    <li>
                        <a href="#" class="topmenu2-1">
                            <strong>기초진단</strong>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="topmenu2-2">
                            <strong>기업HRD이음컨설팅</strong>
                        </a>
                    </li>
                </ul>
            </li>

            <li>
                <button type="button">기초진단 관리</button>

                <ul>
                    <li>
                        <a href="#" class="topmenu2-1-1">
                            <strong>기초진단 관리</strong>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="topmenu1-1-2">
                            <strong>기초진단지</strong>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
                    <!-- contents navigation, content options -->
                    <div class="container-wrapper">
                        <div class="lnb-wrapper">
                            <div class="lnb-area">
<h2>
    <a href="#" class="topmenu2">
		기업진단
		<span class="arrow"></span>
	</a>
</h2>
<ul>
    <li>
        <a href="#" class="topmenu2-1">
            <span class="title">QR 및 훈련동향 관리</span>
            <span class="arrow"></span>
        </a>
        <ul class="topmenu2-1">
            <li>
                <a href="#" class="topmenu2-1-1">
                    <span class="title">기초진단 QR 관리</span>
                </a>
            </li>
            <li>
                <a href="#" class="topmenu2-1-2">
                    <span class="title">최근훈련동향 관리</span>
                </a>
            </li>
        </ul>
    </li>
    <li>
        <a href="#" class="topmenu2-2">
            <span class="title">참여가능사업</span>
        </a>
    </li>
</ul>
                            </div>
                        </div>
                        <!-- contents  -->
                        <article>
                            <div class="contents" id="contents">
                                <h2 class="contents-title">
                                    기초진단 QR관리
                                </h2>
                                <div class="contents-wrapper">

                                    <!-- CMS 시작 -->

                                    <p class="word-type01 point-color01 mb10">
                                        HRD4U와 HRD-Net을 가입하면 더욱 다양한 직업능력개발훈련 콘텐츠를 만나보실 수 있습니다!
                                    </p>
                                    <form id="qr-form">
                                        <div class="contents-area">
                                            <div class="table-type02 horizontal-scroll">
                                                <table>
                                                    <caption>
                                                        기초진단 QR관리표 : QR이미지, 링크,. 제목, 사용여부에 관한 정보표
                                                    </caption>
                                                    <colgroup>
                                                        <col style="width: 10%" />
                                                        <col style="width: 22.5%" />
                                                        <col style="width: 22.5%" />
                                                        <col style="width: 22.5%" />
                                                        <col style="width: 22.5%" />
                                                    </colgroup>
                                                    <tbody>
                                                        <tr>
                                                            <th scope="row" rowspan="2">
                                                                QR이미지
                                                            </th>
                                                            <c:forEach items="${qrs }" var="item" varStatus="status">
                                                            	<td>
	                                                                <img id="qr-image${status.index }" src="${item.image}" alt="QR이미지" class="qrcode" />
	                                                            </td>
                                                            </c:forEach>
                                                        </tr>
                                                        <tr>
                                                        	<c:forEach items="${qrs }" var="item" varStatus="status">
	                                                        	<td>
	                                                        		<div id="file-uploader">
	                                                        			<button type="button" id="file-upload-btn${status.index }" class="btn-m03 btn-color03">수정</button>
	                                                        			<input type="file" id="image-file-input${status.index }" hidden />
	                                                        		</div>
	                                                            </td>
                                                        	</c:forEach>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">
                                                                링크
                                                            </th>
                                                            <c:forEach items="${qrs }" var="item" varStatus="status">
	                                                           	<td>
	                                                                <div class="input-linked-wrapper">
	                                                                    <input type="search" id="qr-url${status.index }" name="" value="${item.url }" class="input-type01" placeholder="링크 주소를 입력하세요." />
	                                                                </div>
	                                                            </td>
                                                            </c:forEach>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">
                                                                제목
                                                            </th>
                                                            <c:forEach items="${qrs }" var="item" varStatus="status">
	                                                            <td>
	                                                                <input type="search" id="qr-title${status.index }" name="" value="${item.title }" class="input-type01 w100" placeholder="제목을 입력하세요." />
	                                                            </td>
                                                            </c:forEach>
                                                        </tr>

                                                        <tr>
                                                            <th scope="row">
                                                                사용여부
                                                            </th>
                                                            <c:forEach items="${qrs }" var="item" varStatus="status">
                                                            	<td>
	                                                                <input type="checkbox" name="" value="" class="checkbox-type01"> <input type="hidden" name="manage_idx" id="qr-idx${status.index }" value="${item.manageIdx }" />
	                                                            </td>                                                            
                                                            </c:forEach>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>


                                        <div class="btns-area mt60">
                                            <button type="button" id="submit-btn" class="btn-b01 round01 btn-color03 left">
                                                <span>
                                                    저장
                                                </span>
                                                <img src="${cPath}/pub/img/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
                                            </button>
                                        </div>
                                    </form>
                                    <!-- //CMS 끝 -->
                                </div>
                            </div>
                        </article>
                    </div>
                    <!-- //contents  -->

            </div>
        </section>
        <!-- //container -->

        <!-- footer -->
        <footer>
	<div class="footer">
		<div class="footer-left-wrapper">
			<ul class="footer-menu">
				<li>
					<a href="#">
						개인정보처리방침
					</a>
				</li>
				<li>
					<a href="#">
						이용약관
					</a>
				</li>
				<li>
					<a href="#">
						이메일주소 무단수집거부
					</a>
				</li>
			</ul>
			<address>
				(44538) 울산광역시 중구 종가로 345(교동) 한국산업인력공단 한국산업인력공단 고객센터 : 1644-8000
			</address>
			<p>
				고객센터(사이트 이용 문의) 052-714-8288 이메일 hrdportal@hrdkorea.or.kr
			</p>
			<p class="copyright">
				COPYRIGHT 2021 SAMSUNG MEDICAL CENTER. ALL RIGHTS RESERVED.
			</p>
		</div>


		<div class="footer-right-wrapper">
			<div class="footer-site-wrapper">
				<select id="select-agency01" name="">
					<option value="">
						관련사이트
					</option>
				</select>

				<button type="button" onclick="go_url01()">
					이동
				</button>
			</div>
		</div>


		<!-- 상단으로 이동 -->
		<a href="#btn-top-go" class="btn-top-go">
			TOP
		</a>

		<a href="javascript:history.back(-1)" class="btn-mobile-back">
			BACK
		</a>
		<!-- //상단으로 이동 -->
	</div>
        </footer>
        <!-- //footer -->
    </div>
    <!-- //wrapper -->
    <script>
        menuOn(2, 1, 1, 0);
        for(let i=0; i<${fn:length(qrs)}; i++) {
        	document.getElementById('file-upload-btn'+i).addEventListener('click', function() {
            	console.log('click')
            	document.getElementById('image-file-input'+i).click();
            })
            document.getElementById('image-file-input'+i).addEventListener('change', function() {
            	let file = this.files[0];
            	let reader = new FileReader();
            	let img = document.getElementById('qr-image'+i)
            	reader.onloadend = function() {
            		img.src = reader.result
            	}
            	if(file) {
            		reader.readAsDataURL(file)
            	}
            })
        }
        
        document.getElementById('submit-btn').addEventListener('click', function() {
        	let qrs = []
        	for(let i=0; i<${fn:length(qrs)}; i++) {
        		let m_ = {image: '', url: '', title: '', manageIdx: 0}
        		let image = document.getElementById('qr-image'+i);
        		let title = document.getElementById('qr-title'+i);
        		let url = document.getElementById('qr-url'+i);
        		let manageIdx = document.getElementById('qr-idx'+i);
        		m_.image = image.src;
        		m_.title = title.value;
        		m_.url = url.value;
        		m_.manageIdx = manageIdx.value;
        		qrs.push(m_)
        	}
        	console.log(qrs);
        	send_data(qrs);
        })
        async function send_data(data) {
        	let response = await fetch('${pageContext.request.contextPath }/recommend/updateQR.do', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data)})
        	let result = await response;
        	console.log(result)
        }
    </script>
</body>

</html>