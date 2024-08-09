// 탭을 눌렀을 때 작동하는 메서드
function openTab(evt, tabName) {
    var eventBar = document.querySelector('.event-bar');
    var noticeBar = document.querySelector('.notice-bar');
    var qnaBar = document.querySelector('.qna-bar');
    var tabBtn1 = document.querySelector('.tab-item-1');
    var tabBtn2 = document.querySelector('.tab-item-2');
    var tabBtn3 = document.querySelector('.tab-item-3');

    // 모든 탭 내용을 숨김
    var tabcontent = document.getElementsByClassName("tab-item");
    for (var i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // 해당 탭 내용을 표시하고, 탭 버튼을 활성화 상태로 변경
    document.getElementById(tabName).style.display = "block";
    
    if (tabName === "tab1") {
        tabBtn1.style.display = "block";
        tabBtn1.style.color = "#5c9fec";
        noticeBar.style.backgroundColor = "rgb(92, 159, 236)";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#16171d";
        eventBar.style.backgroundColor = "#cccccc";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#16171d";
        qnaBar.style.backgroundColor = "#cccccc";
        
   }else if(tabName === "tab2"){
        tabBtn1.style.display = "block";
        tabBtn1.style.color = "#16171d";
        noticeBar.style.backgroundColor = "#cccccc";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#5c9fec";
        eventBar.style.backgroundColor = "rgb(92, 159, 236)";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#16171d";
        qnaBar.style.backgroundColor = "#cccccc";
   }else{
        tabBtn1.style.display = "block";
        tabBtn1.style.color = "#16171d";
        noticeBar.style.backgroundColor = "#cccccc";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#16171d";
        eventBar.style.backgroundColor = "#cccccc";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#5c9fec";
        qnaBar.style.backgroundColor = "rgb(92, 159, 236)";
   }
}

// 페이지 로드 시 초기 설정
document.addEventListener("DOMContentLoaded", function(event) {
    var eventBar = document.querySelector('.event-bar');
    var noticeBar = document.querySelector('.notice-bar');
    var qnaBar = document.querySelector('.qna-bar');
    var tabBtn1 = document.querySelector('.tab-item-1');
    var tabBtn2 = document.querySelector('.tab-item-2');
    var tabBtn3 = document.querySelector('.tab-item-3');
    
    // 초기에 공지사항 버튼이 선택되어 있으므로 파란선이 있고, 이벤트 버튼 아래에는 회색선이 있도록 설정
   document.getElementById("tab1").style.display = "block";
    noticeBar.style.display = "block";
    noticeBar.style.backgroundColor = "rgb(92, 159, 236)";
    tabBtn1.style.color = "rgb(92, 159, 236)";
    eventBar.style.backgroundColor = "#cccccc";
    
    if (window.location.href.includes('event')) {
      // 이벤트 버튼을 클릭하면 파란선 표시, 공지사항, 문의함 버튼 아래에 회색선 표시
        tabBtn1.style.display = "block";
        tabBtn1.style.color = "#16171d";
        noticeBar.style.backgroundColor = "#cccccc";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#5c9fec";
        eventBar.style.backgroundColor = "rgb(92, 159, 236)";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#16171d";
        qnaBar.style.backgroundColor = "#cccccc";
        
        return;
        
   }else if (window.location.href.includes('qna')){
        tabBtn1.style.display = "block";
        tabBtn1.style.color = "#16171d";
        noticeBar.style.backgroundColor = "#cccccc";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#16171d";
        eventBar.style.backgroundColor = "#cccccc";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#5c9fec";
        qnaBar.style.backgroundColor = "rgb(92, 159, 236)";
        
        return;
   }
  
    // 이벤트 리스너 등록
    tabBtn1.addEventListener('click', function() {
          tabBtn1.style.display = "block";
          tabBtn1.style.color = "#5c9fec";
        noticeBar.style.backgroundColor = "#5c9fec";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#16171d";
        eventBar.style.backgroundColor = "#cccccc";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#16171d";
        qnaBar.style.backgroundColor = "#cccccc";
    });

    tabBtn2.addEventListener('click', function() {
        tabBtn1.style.display = "block";
        tabBtn1.style.color = "#16171d";
        noticeBar.style.backgroundColor = "#cccccc";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#5c9fec";
        eventBar.style.backgroundColor = "rgb(92, 159, 236)";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#16171d";
        qnaBar.style.backgroundColor = "#cccccc";
    });
    
    tabBtn3.addEventListener('click', function() {
        tabBtn1.style.display = "block";
        tabBtn1.style.color = "#16171d";
        noticeBar.style.backgroundColor = "#cccccc";
        tabBtn2.style.display = "block";
        tabBtn2.style.color = "#16171d";
        eventBar.style.backgroundColor = "#cccccc";
        tabBtn3.style.display = "block";
        tabBtn3.style.color = "#5c9fec";
        qnaBar.style.backgroundColor = "rgb(92, 159, 236)";
    });
    
});

// 선택한 탭에 맞게 목록을 표시
document.addEventListener("DOMContentLoaded", function() {
    var tabItems = document.querySelectorAll('.notice .tab-item'); // 모든 탭 아이템 요소를 선택합니다.
    
    function openTab(tabName) {
        // 모든 탭 아이템을 숨깁니다.
        tabItems.forEach(function(tab) {
            tab.style.display = 'none';
        });

        // 해당 탭 아이템을 보여줍니다.
        document.getElementById(tabName).style.display = 'block';

        // 모든 탭 버튼의 활성화 상태 제거
        document.querySelectorAll('.tab-btns button').forEach(function(btn) {
            btn.classList.remove('active');
        });

        // 선택된 탭 버튼의 활성화 상태 추가
        document.querySelector('[data-tab="' + tabName + '"]').classList.add('active');
    }

    // 초기에 첫 번째 탭을 활성화합니다.
    
    if (window.location.href.includes('event')) {
       openTab('tab2');
   }else if(window.location.href.includes('qna')){
       openTab('tab3');
   }

    // 탭 버튼 클릭 이벤트를 처리합니다.
    document.querySelectorAll('.tab-btns button').forEach(function(btn) {
        btn.addEventListener('click', function() {
            var tabName = this.getAttribute('data-tab');
            openTab(tabName);
        });
    });
});