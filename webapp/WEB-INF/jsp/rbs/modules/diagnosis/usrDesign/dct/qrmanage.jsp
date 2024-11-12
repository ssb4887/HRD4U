<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<style>
	#overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,0.5);
		display: none;
		z-index: 9999;
	}
	
	.loader {
		border:4px solid #f3f3f3;
		border-top: 4px solid #3498db;
		border-radius: 50%;
		width: 50px;
		height: 50px;
		animation: spin 2s linear infinite;
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		z-index: 10000;
	}
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
</style>
<div id="overlay"></div>
<div class="loader"></div>

<div class="contents-wrapper">

	<!-- CMS 시작 -->
	<p class="word-type01 point-color01 mb10">HRD4U와 HRD-Net을 가입하면 더욱 다양한 직업능력개발훈련 콘텐츠를 만나보실 수 있습니다!</p>
	<form id="qr-form">
		<div class="contents-area">
			<div class="table-type02 horizontal-scroll">
				<table>
					<caption>기초진단 QR관리표 : QR이미지, 링크,. 제목, 사용여부에 관한 정보표</caption>
					<colgroup>
						<col style="width: 10%" />
						<col style="width: 22.5%" />
						<col style="width: 22.5%" />
						<col style="width: 22.5%" />
						<col style="width: 22.5%" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" rowspan="2">QR이미지<br/><span style="font-size:13px;">(권장 100*100)</span></th>
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
							<th scope="row">링크</th>
							<c:forEach items="${qrs }" var="item" varStatus="status">
								<td>
									<div class="input-linked-wrapper">
										<input type="search" id="qr-url${status.index }" name="" value="${item.url }" class="input-type01 w100" placeholder="링크 주소를 입력하세요." />
									</div>
								</td>
							</c:forEach>
						</tr>
						<tr>
							<th scope="row">제목</th>
							<c:forEach items="${qrs }" var="item" varStatus="status">
								<td>
									<input type="search" id="qr-title${status.index }" name="" value="${item.title }" class="input-type01 w100" placeholder="제목을 입력하세요." />
									<input type="hidden" name="manage_idx" id="qr-idx${status.index }" value="${item.manageIdx }" />
								</td>
							</c:forEach>
						</tr>
					</tbody>
				</table>
			</div>
		</div>


		<div class="btns-area mt60">
			<button type="button" id="submit-btn" class="btn-b01 round01 btn-color03 left">
				<span> 저장 </span>
				<img src="${cPath}/pub/img/icon/icon_arrow_right03.png" alt="" class="arrow01" />
			</button>
		</div>
	</form>
	<!-- //CMS 끝 -->
	
</div>
<!-- //contents  -->
    <script>
        menuOn(2, 1, 1, 0);
        hideLoading();
        
        function showLoading() {
        	const loader = document.querySelector('.loader');
        	const overlay = document.getElementById('overlay');
        	loader.style.display = 'block';
        	overlay.style.display = 'block';
        }

        function hideLoading() {
        	const loader = document.querySelector('.loader');
        	const overlay = document.getElementById('overlay');
        	loader.style.display = 'none';
        	overlay.style.display = 'none';
        }
        
        
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
            		img.src = reader.result;
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
        	showLoading();
        	let response = await fetch('${pageContext.request.contextPath }/dct/diagnosis/updateQR.do?mId=36', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data)})
        	let result = await response;
        	if(response.ok) {
        		alert('QR변경을 완료했습니다.');
        	} else {
        		alert('QR변경에 실패했습니다.');
        	}
        	hideLoading();
        	window.location.reload();
        }
    </script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>s