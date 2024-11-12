<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="cPath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="ko">
<head></head>
<body>
    <h1>hi</h1>
    <form method="post" action="/doctor/plagiarism/test.do">
    	<input type="text" id="source" name="source">
    	<input type="submit">
    </form>
<script>
(() => {
	let text = [
	    "CSK에 적합한 문제 해결 프로세스 구현 연습과 실행력 향상을 위한 문제 기반 학습(Problem-Based Leaning), 팀 활동의 컨텐츠 제작 연습",
	    "설계 단계에서 원가경쟁력 향상, 과제도출훈련 및 실천 능력 향상", 
	    "문제해결프로세스 필요항목 제시 및 프로세스 항목별 목적, 방법, 내용 이해",
	    "CSK에 적합한 프로세스 항목 압축 / CSK 독자적 문제해결 프로세스 정립",
	    "현상파악, 도식화, 물리적 표현 등을 통한 현상표현 기법 / 현상의 시간,공간적 분석기법 / 메커니즘의 시간,공간적 분석 기법 (사리적,물리적 인과관계)",
	    "개선 대책방향 설정방법 원인제거방향 vs 원인흡수방향 / 대책 발상 기법 44항목",
	    "OOO-DUAL 제품 설계 컨셉 정리 (현재시점기준) / 개선 아이템 발굴, 적용 전후 비교 / 예상문제도출 및 대책수립 ★",
	    "실행계획 수립 / 보완점 도출 및 대책실행"
	];
	let source_ = text.join(' ')
	const elem = document.querySelector('input#source')
	elem.value = source_;
	console.log(source_);
})();
</script>
</body>
</html>