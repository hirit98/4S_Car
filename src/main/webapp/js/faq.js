
// 모든 아이콘 요소와 해당 내용 요소를 가져옴
const arrowIcons = document.querySelectorAll('.icon-arrow-right');
const itemContents = document.querySelectorAll('.item-wrap');

// 아이콘 클릭 시 실행될 함수
function handleIconClick(event) {
    // 현재 클릭된 아이콘 요소
    const clickedIcon = event.target;

    // 해당 아이콘에 대응하는 내용 요소
    const content = clickedIcon.parentElement.parentElement.nextElementSibling;

    // 현재 내용 요소의 상태를 확인하여 토글
    const isContentVisible = content.style.display === 'block';
    
    // 모든 내용 요소를 숨김
    itemContents.forEach(itemContent => {
        itemContent.style.display = 'none';
    });

    // 클릭된 아이콘에 대응하는 내용 요소가 숨겨져 있으면 보이게 함, 보이고 있다면 숨김
    content.style.display = isContentVisible ? 'none' : 'block';
}

// 각 아이콘에 클릭 이벤트 추가
arrowIcons.forEach(icon => {
    icon.addEventListener('click', handleIconClick);
});


////////////// 질문 누르면 내용 나오게 하는 코드

// 탭 요소들과 내용 요소들을 가져옴
const tabs = document.querySelectorAll('.el-tabs__item');
const tabContents = document.querySelectorAll('.el-tabs-content');
// 각 탭을 클릭할 때 실행되는 함수


// 각 질문 요소를 선택합니다.
const questionItems = document.querySelectorAll('.el-collapse-item');

// 질문을 클릭할 때 실행될 함수
function handleQuestionClick(event) {
    // 해당 질문의 내용 요소를 선택합니다.
    const content = event.currentTarget.querySelector('.item-wrap');
    
    
    // 내용이 숨겨진 경우 보이도록 하고, 보이는 경우 숨깁니다.
    if (content.style.display === 'none') {
        content.style.display = 'block';
    } else {
        content.style.display = 'none';
    }
}

// 각 질문에 클릭 이벤트 리스너를 추가합니다.
questionItems.forEach(question => {
    question.addEventListener('click', handleQuestionClick);
});


// 초기에 내 차 사기 탭이 선택되도록 설정
document.addEventListener("DOMContentLoaded", function() {
    showTab('pane-C0002');
});

// 탭을 클릭할 때 해당 탭의 내용을 보여주는 함수
function showTab(tabId) {
    // 모든 탭 숨기기
    var tabs = document.getElementsByClassName('el-tab-pane');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].style.display = 'none';
    }

    // 선택된 탭 보여주기
    var selectedTab = document.getElementById(tabId);
    if (selectedTab) {
        selectedTab.style.display = 'block';
    }
}

// 내 차 사기 버튼을 누르면 해당 탭 내용 표시
var buyCarButton = document.querySelector('.el-tabs__item.is-active');
buyCarButton.addEventListener('click', function() {
    showTab('pane-C0002');
    tabContents[0].style.display = 'block';
});

var sellCarButton = document.querySelector('.el-tabs__item:nth-child(3)'); // 2번째 버튼 선택
sellCarButton.addEventListener('click', function() {
    showTab('pane-C0003');
    tabContents[0].style.display = 'none';
});


var manageMemberButton = document.querySelector('.el-tabs__item:nth-child(4)'); // 3번째 버튼 선택
manageMemberButton.addEventListener('click', function() {
    showTab('pane-C0004');
    tabContents[0].style.display = 'none';
});


var financeButton = document.querySelector('.el-tabs__item:nth-child(5)'); // 4번째 버튼 선택
financeButton.addEventListener('click', function() {
    showTab('pane-C0005');
    tabContents[0].style.display = 'none';
});


var rentButton = document.querySelector('.el-tabs__item:nth-child(6)'); // 5번째 버튼 선택
rentButton.addEventListener('click', function() {
    showTab('pane-C0006');
    tabContents[0].style.display = 'none';
});




var etcButton = document.querySelector('.el-tabs__item:nth-child(7)'); // 7번째 버튼 선택
etcButton.addEventListener('click', function() {
    showTab('pane-C0008');
});

////////////////


// content요소를 누르면 p태그 내용이 보이게 하는 코드
document.addEventListener("DOMContentLoaded", function() {
    const questions = document.querySelectorAll(".collapse-header");
  
    questions.forEach(function(question) {
      question.addEventListener("click", function() {
        const answer = this.parentElement.nextElementSibling;
        const paragraphs = answer.querySelectorAll(".faqA p");
  
        paragraphs.forEach(function(paragraph) {
          paragraph.style.display = paragraph.style.display === "block" ? "none" : "block";
        });
      });
    });
  });


  document.addEventListener("DOMContentLoaded", function() {
    const tabItems = document.querySelectorAll('.el-tabs__item');

    tabItems.forEach(function(item) {
        item.addEventListener('click', function() {
            // 기존에 선택된 탭에 active 클래스를 제거
            document.querySelector('.el-tabs__item.is-active').classList.remove('is-active');
            // 현재 클릭한 탭에 active 클래스 추가
            this.classList.add('is-active');
        });
    });
});