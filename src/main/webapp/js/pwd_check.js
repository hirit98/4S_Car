/*비밀번호 유효성 검사 제이쿼리 코드*/
$(function () {
	$("#password").keyup(function () {
		$("#password_Error").hide();
		
		let userPwd = $("#password").val();
		
		// 비밀번호 입력 길이 체크
		if($.trim($("#password").val()).length < 4) {
			
			let warningTxt = '<font style="color : red;">비밀번호는 4자리 이상 16자리 미만이어야 합니다.</font>';
			
			$("#password_Error").text("");
			$("#password_Error").show();
			$("#password_Error").append(warningTxt);
		
			return false;
		}
		if($.trim($("#password").val()).length > 16) {
			
			let warningTxt = '<font style="color : red;">비밀번호는 4자리 이상 16자리 미만이어야 합니다.</font>';
			
			$("#password_Error").text("");
			$("#password_Error").show();
			$("#password_Error").append(warningTxt);
		
			return false;
		}
		
	})
})

/*비밀번호 더블체크 제이쿼리 코드*/
$(function () {
	$("#passwordcheck").keyup(function () {
		$("#pwdcheck_Error").hide();
		
		let userPwd = $("#password").val();
		let checkPwd = $("#passwordcheck").val();
		
		if($.trim(userPwd) !== $.trim(checkPwd)) {
			
			let warningTxt = '<font style="color : red;">비밀번호가 일치하지 않습니다.</font>';
			
			$("#pwdcheck_Error").text("");
			$("#pwdcheck_Error").show();
			$("#pwdcheck_Error").append(warningTxt);
		
			return false;
		}
		if($.trim(userPwd) == $.trim(checkPwd)) {
			
			let warningTxt = '<font style="color : green;">비밀번호 일치</font>';
			
			$("#pwdcheck_Error").text("");
			$("#pwdcheck_Error").show();
			$("#pwdcheck_Error").append(warningTxt);
		
			return true;
		}
	})
})