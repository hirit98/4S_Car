function changePhone1(){
    let phone1 = document.getElementById("phone1").value
    if(phone1.length === 3){
        document.getElementById("phone2").focus();
    }  
}

function changePhone2(){
    let phone2 = document.getElementById("phone2").value
    if(phone2.length === 4){
        document.getElementById("phone3").focus();
    }
}
function changePhone3(){
    let phone3 = document.getElementById("phone3").value
    if(phone3.length === 4){
        document.getElementById("sendMessage").focus();
        document.getElementById("sendMessage").setAttribute("style","background-color:red;, color:white;")
        document.getElementById("sendMessage").disabled = false;
    }
}

/*// 문자인증 + 타이머

function  initButton(){
    document.getElementById("phone1").disabled = true;
    document.getElementById("phone2").disabled = true;
    document.getElementById("phone3").disabled = true;
    document.getElementById("sendMessage").disabled = true;
    document.getElementById("completion").disabled = true;
    document.getElementById("certificationNumber").innerHTML = "000000";
    document.getElementById("timeLimit").innerHTML = "3:00";
    document.getElementById("sendMessage").setAttribute("style","background-color:none;")
    document.getElementById("completion").setAttribute("style","background-color:none;")
    
}

let processID = -1;

const getToken = () => {
    //인증확인 버튼 활성화

    document.getElementById("completion").setAttribute("style","background-color:red")
    document.getElementById("completion").disabled = false;

    if(processID != -1) clearInterval(processID);
    const token = String(Math.floor(Math.random()* 1000000)).padStart(6,"0");
    document.getElementById("certificationNumber").innerText = token;


    let time = 180;

    processID = setInterval(function() {
        if(time < 0 || document.getElementById("sendMessage").disabled){
            clearInterval(processID);
            initButton();
         return;
        }
        let mm =String(Math.floor(time / 60)).padStart(1,"0");
        let ss =String(time % 60).padStart(2,"0");
        document.getElementById("timeLimit").innerText = (mm + ":" + ss);
        time --;
        
        
    }, 1000);
};

function checkCompletion(){
    alert("문자 인증이 완료되었습니다.")
    initButton();
    document.getElementById("completion").innerHTML = "인증완료";
    document.getElementById("signUpButton").disabled = false;
    document.getElementById("signUpButton").setAttribute("style","background-color:#e32b30;")


    let name = document.getElementById("name").value
    let id = document.getElementById("id").value
    let email = document.getElementById("email").value
    let password = document.getElementById("password").value
    let passwordCheck = document.getElementById("passwordcheck").value
    let phone1 = document.getElementById("phone1").value
    let phone2 = document.getElementById("phone2").value
    let phone3 = document.getElementById("phone3").value
    
    var send = document.getElementById("signUpButton");
	send.addEventListener("click", function () {
		  var form = document.getElementById("form");
		  var id = document.getElementById("name");
		  var pw = document.getElementById("password");
		  var email = document.getElementById("email");
		  var pwcheck = document.getElementById("passwordcheck");
		  
	
		  if (pw.value.trim() == "" || id.value.trim() == "" ) {
		    alert("회원가입 폼 페이지 작성란을 확인해 주세요");
		    return false;
		  }
	
		  form.action = "./signup_ok";
		  form.method = "post";
		  form.submit();
	});
    
}*/


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

/*아이디 중복체크 제이쿼리 코드*/
$(function () {
		$("#id_check").click(function () {
			$("#id_Error").hide();
			
			let userId = $("#id").val();
			
			// 아이디 입력 길이 체크하는 방법
			if($.trim($("#id").val()).length < 4) {
				
				let warningTxt = '<font style="color : red;">아이디는 4자리 이상 16자리 미만이어야 합니다.</font>';
				
				$("#id_Error").text(""); 
				$("#id_Error").show();   
				$("#id_Error").append(warningTxt);
				return false;
			}
			
			// 아이디 입력 길이 체크하는 방법
			if($.trim($("#id").val()).length > 16) {
				let warningTxt = '<font style="color : red;">아이디는 4자리 이상 16자리 미만이어야 합니다.</font>';
				
				$("#id_Error").text("");
				$("#id_Error").show();
				$("#id_Error").append(warningTxt);
				return false;
			}
				
			$.ajax({
				type : "post",
				url : "signup_idcheck",
				data : {paramId : userId},	// 변수명 : 값
				dataType : "json",
				success : function (data) {
					if(data == -1){
						let warningTxt = '<font style="color : red;">중복된 아이디입니다.</font>';
						
						$("#id_Error").text("");
						$("#id_Error").show();
						$("#id_Error").append(warningTxt);
						return false;
					} else{
						let warningTxt = '<font style="color : green;">사용 가능한 아이디입니다.</font>';
						
						$("#id_Error").text("");
						$("#id_Error").show();
						$("#id_Error").append(warningTxt);
					}
				},
				error : function () {
					alert("데이터 통신 오류입니다.");
				}
			})
	})
}) 