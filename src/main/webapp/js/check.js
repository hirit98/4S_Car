document.addEventListener("DOMContentLoaded", function() {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            var label = this.parentElement;
            if (this.checked) {
                label.classList.add('my_clicked');
            } else {
                label.classList.remove('my_clicked');
            }
        });
    });
    
    var radios = document.querySelectorAll('input[type="radio"]');
   radios.forEach(function(radio) {
    radio.addEventListener('change', function() {
           var label = this.parentElement;
   
           // 모든 부모 요소에서 my_clicked 클래스를 제거합니다.
           var allLabels = document.querySelectorAll('.sequence_content .my_clicked');
           allLabels.forEach(function(item) {
               item.classList.remove('my_clicked');
           });
   
           // 현재 선택된 라디오 버튼의 부모 요소에 my_clicked 클래스를 추가합니다.
           label.classList.add('my_clicked');
       });
   });
});


document.addEventListener("DOMContentLoaded", function() {
  // 각 carsort 클래스 요소를 가져옴
  var carSorts = document.querySelectorAll('.carsort');

  // 각 carsort 요소에 대해 이벤트 리스너 설정
  carSorts.forEach(function(carSort) {
    // 화살표 아이콘을 가져옴
    var arrowIcon = carSort.querySelector('.carHead');
    // 클릭 이벤트 리스너 설정
    arrowIcon.addEventListener('click', function() {
      // 해당 carsort 요소에서 다음 형제 요소를 찾아서 상세 내역을 토글함
      var carItem = carSort.querySelector('.caritem');
      if (carItem.style.display === 'none' || carItem.style.display === '') {
        carItem.style.display = 'block';
      } else {
        carItem.style.display = 'none';
      }
    });
  });
});


document.addEventListener("DOMContentLoaded", function() {
  const scrollbarWrap = document.querySelector(".scrollerbarWrap");
  const scrollbarView = document.querySelector(".scrollerbarView");
  const modelList = document.querySelector(".modelList");

  const scrollerbarHeight = scrollbarWrap.clientHeight / modelList.scrollHeight * scrollbarWrap.clientHeight;
  scrollbarView.style.height = scrollerbarHeight + "px";

  modelList.addEventListener("scroll", function() {
    const scrollPercentage = modelList.scrollTop / (modelList.scrollHeight - modelList.clientHeight);
    const scrollbarTop = scrollPercentage * (scrollbarWrap.clientHeight - scrollerbarHeight);
    scrollbarView.style.top = scrollbarTop + "px";

  });
});


function toggleBorderColor(button) {
  button.classList.toggle('clicked'); // 'clicked' 클래스를 토글합니다.
}

// car_list에서 가격을 입력하면 10000을 곱해서 반환해주는 메서드
function multiplyPrice(event) {
   event.preventDefault();
   
   var min_price = document.getElementById("min_price").value
   var max_price = document.getElementById("max_price").value
   
   if(min_price){
      document.getElementById("min_price").value = min_price * 10000;
   }
   if(max_price){
      document.getElementById("max_price").value = max_price * 10000;
   }
   
   document.getElementById("myForm").submit();
   return true;
}