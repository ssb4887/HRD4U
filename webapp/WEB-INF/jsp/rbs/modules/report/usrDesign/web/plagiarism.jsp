<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Content-Script-Type" content="text/javascript" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
    <meta name="subject" content="" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link rel="stylesheet" href="../../assets/css/reset_pc.css">
    <link rel="stylesheet" href="../../assets/css/board_pc.css">
    <link rel="stylesheet" href="../css/contents_pc.css">
    <link rel="stylesheet" href="../css/contents02_pc.css">
    <link rel="stylesheet" href="../css/common_pc.css">


    <script src="../../assets/js/jquery.min.js"></script>
    <script src="../../assets/js/jquery.easing.1.3.js"></script>
    <script src="../../assets/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="../../assets/js/board.js"></script>
    <script src="../js/common.js"></script>

    <title>유사도 검사 &gt; 기업직업훈련 지원시스템</title>
</head>
<body>
<style type="text/css">
.modal-wrapper {
    position: static;
    display: block;
    width: 100%;
    margin-left: 0;
    background-color: #fff;
    box-shadow: initial;
    z-index: 1010;
    transform: none;
}
.modal-area {
    max-height: calc(100vh - 60px) !important;
}
</style>
<div class="modal-wrapper">
	<h2>유사도 검사 결과</h2>
	<div class="modal-area">
		<div class="contents-area">
			<div class="contents-box pl0">
				<div class="table-type01">
					<table>
						<caption>
							유사도 검사 결과표 : 유사도 검사 결과, 유사 구문, 유사어에 관한 정보 제공표
						</caption>
						<colgroup>
							<col style="width: 180px" />
							<col style="width: *" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									유사도 검사 결과
								</th>
								<td class="left">
									<strong id="result" style="font-size:24px"></strong>
									<p id="alert" class="mt05">
										작성한 프로파일을 다시 수정하여 유사도 검사를 재실시 해주시기 바랍니다.
									</p>
								</td>
							</tr>
							<tr>
								<th scope="row">
									가장 유사한 구문
								</th>
								<td id="source" class="left"></td>
							</tr>
							<tr>
								<th scope="row">
									유사어
								</th>
								<td id="similar" class="left"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="btn-area center 0 ">
				<button type="button" class="btn-b02 btn-color03" onclick="window.close();">확인</button>
			</div>
		</div>
	</div>
	<button type="button" class="btn-modal-close " onclick="window.close(); ">팝업 창 닫기</button>
</div>
<script>
	window.onload = e => {
		const text_data = localStorage.getItem('plagiarism')
		if(text_data != null) {
			const { rate, matched, source } = JSON.parse(text_data)
			const result_e = document.querySelector('strong#result')
			const source_e = document.querySelector('td#source')
			const alert_e = document.querySelector('p#alert');
			const similar_e = document.querySelector('td#similar');
			const similars = matched.filter(e=>e != '').sort().join(' , ')
			console.log(rate);
			console.log(JSON.parse(text_data));
			result_e.textContent = `\${parseFloat(rate).toFixed(1)} %`
			source_e.innerHTML = marking(source, matched);
			similar_e.textContent = similars;
			if(parseFloat(rate) >= 50) {
				result_e.classList.add('point-color04')
				alert_e.style.display='block';
			} else {
				result_e.classList.add('point-color01')
				alert_e.style.display='none';
			}
		}
	}
	function escapeRegExp(string) {
		return string.replace(/[.*+?^&{}()|[\]\\]/g, '\\\$&')
	}
	function marking(source, similar) {
		let result = source;
		similar.forEach(e => {
			const escapedWord = escapeRegExp(e)
			const regex = new RegExp(`(^|\\s)(\${escapedWord})(\\s|$)`, 'gi')
			result = result.replace(regex, '$1<span style="color:red;">$2</span>$3')
		});
		return result;
	}
	window.addEventListener('message', e => {
		
	})
</script>
</body>
</html>