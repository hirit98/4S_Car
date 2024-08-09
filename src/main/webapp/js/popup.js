// 모달 요소 가져오기
var modal = document.getElementById("popup_layer");

// 오늘 안 보기 버튼 클릭 시 모달 숨기기
document.getElementById("chk_today").addEventListener("click", function() {
  // 오늘 날짜 계산
  var today = new Date();
  var dd = String(today.getDate()).padStart(2, '0');
  var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
  var yyyy = today.getFullYear();
  today = yyyy + '-' + mm + '-' + dd;

  // 오늘 날짜를 쿠키에 저장
  document.cookie = "hideModal=true; expires=" + today + " 23:59:59 UTC; path=/";

  // 모달 숨기기
  modal.style.display = "none";
});

// 닫기 버튼 클릭 시 모달 숨기기
document.getElementById("chk_close").addEventListener("click", function() {
  modal.style.display = "none";
});

// 페이지가 열릴 때 모달 보이기
window.onload = function() {
  // 오늘 안 보기 쿠키 확인
  var hideModal = document.cookie.replace(/(?:(?:^|.*;\s*)hideModal\s*\=\s*([^;]*).*$)|^.*$/, "$1");

  // 쿠키가 없으면 모달 보이기
  if (hideModal !== "true") {
    modal.style.display = "block";
  }
};
