function checkSessionStatus() {
	
    $.ajax({
        url: 'check_session_status', // 세션 확인을 처리하는 서버 측 URL
        success: function(response) {
            if (response === "expired") {
                alert("세션이 만료되어 로그아웃 합니다.");
                window.location.href = 'main.jsp'; // 만료 시 리다이렉트할 페이지
            }
        }
    });
}

$(document).ready(function() {

	$.ajax({
        url: 'check_login_status', // 로그인 상태를 확인하는 서버 측 API URL
        success: function(response) {
            var isLoggedIn = response.loggedIn; // 서버 측 API에서 받은 로그인 상태
            if (isLoggedIn == 1 || isLoggedIn == 0) {
                // 주기적으로 세션 상태 확인 (로그인 상태에서만 실행)
                setInterval(checkSessionStatus, 60000); // 1분(60초)마다 함수 호출
            }
        },
        error: function(xhr, status, error) {
            // Ajax 요청이 실패한 경우에 대한 처리
            console.error("Ajax 요청 실패:", error);
        }
    });
});