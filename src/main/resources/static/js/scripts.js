function submitForm() {
    // 여행 예산 입력값 가져오기
    var budgetInput = document.getElementById("emailAddress").value;

    // 결과를 결과 창에 표시
    var resultElement = document.getElementById("result");
    resultElement.innerHTML = "예산이 선택되었습니다: " + budgetInput;
}

function scrollToFeature() {
    var targetSection = document.querySelector("#fu");

    if (targetSection) {
        targetSection.scrollIntoView({ behavior: 'smooth' });
    }
}

function submitForm(event) {
    event.preventDefault(); // 양식 제출을 중지하여 페이지가 새로 고치지 않도록 합니다.
    var resultElement = document.getElementById("result");
    resultElement.innerHTML = "예산이 선택되었습니다.";
}

function showNumber() {
    // 입력한 숫자를 가져옵니다.
    var number = document.getElementById("numberInput").value;

    // 결과를 출력할 요소를 가져옵니다.
    var outputElement = document.getElementById("output");

    // 결과를 출력합니다.
    outputElement.textContent = number;
}