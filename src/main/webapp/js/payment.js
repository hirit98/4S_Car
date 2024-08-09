// 모달 요소 가져오기
var creditModal = document.getElementById("paymentModal");
var typeModal = document.getElementById("payment_type_Modal");
var cashModal = document.getElementById("cash_Modal");

// 모달 열기 버튼 가져오기
var popup_payment = document.getElementById("popup_credit");
var type_btn = document.getElementById("popup_payment_type");
var cash_btn = document.getElementById("popup_cash");

// 모달을 닫는 데 사용되는 <span> 요소 가져오기
var closeSpan = document.getElementsByClassName("close")[0];
var type_close = document.getElementsByClassName("type_close")[0];
var cash_close = document.getElementsByClassName("cash_close")[0];

// 버튼을 클릭하면 모달 열기
popup_payment.onclick = function() {
   typeModal.style.display = "none";
    creditModal.style.display = "block";
}
cash_btn.onclick = function() {
   typeModal.style.display = "none";
    cashModal.style.display = "block";
}
type_btn.onclick = function() {
    typeModal.style.display = "block";
}

// <span> (x)를 클릭하면 모달 닫기
closeSpan.onclick = function() {
    creditModal.style.display = "none";
}
type_close.onclick = function() {
    typeModal.style.display = "none";
}
cash_close.onclick = function() {
    cashModal.style.display = "none";
}

// 모달 외부를 클릭하면 모달 닫기
window.onclick = function(event) {
    if (event.target == creditModal) {
        creditModal.style.display = "none";
    }
    if (event.target == typeModal) {
        typeModal.style.display = "none";
    }
    if (event.target == cashModal) {
        cashModal.style.display = "none";
    }
}

function inputCardNum1() {
   let cardNum1 = document.getElementById("cardNumber1").value;
    if(cardNum1.length === 4){
        document.getElementById("cardNumber2").focus();
    }
}
function inputCardNum2() {
   let cardNum2 = document.getElementById("cardNumber2").value;
    if(cardNum2.length === 4){
        document.getElementById("cardNumber3").focus();
    }
}
function inputCardNum3() {
   let cardNum3 = document.getElementById("cardNumber3").value;
    if(cardNum3.length === 4){
        document.getElementById("cardNumber4").focus();
    }
}

$(document).ready(function() {

         const carPrice = document.getElementById("carPrice").value;

         var selectedOption = 12; // select박스 기본값 '12개월'
         var finalPrice = carPrice / selectedOption;
         var price = Math.round(finalPrice);
         var textHtml = "";
         textHtml += "<p><strong style='font-size: 30px; color:rgb(92 159 236 / 100%);'>월 "
               + price.toLocaleString() + "</strong>";
         textHtml += " 원의</p>" + "<p>" + "납부금액이 예상됩니다."
               + "</p>";
         $(".detail-box02 .div_price").html(textHtml);

         // select 태그 변경 시 이벤트 처리
         $("#selectMonth").change(function() {
                  var selectedOption = $(this).val();
                  var finalPrice = carPrice / selectedOption;
                  var price = Math.round(finalPrice);
                  var textHtml = "";
                  textHtml += "<p><strong style='font-size: 30px;color:rgb(92 159 236 / 100%);'>월 "
                        + price.toLocaleString()
                        + "</strong>";
                  textHtml += " 원의</p>" + "<p>" + "납부금액이 예상됩니다." + "</p>";
                  $(".detail-box02 .div_price").html(textHtml);
         });
});