function appendComment(userId, comment, writeDate){

    // 새로운 댓글 요소 생성.
    const commentDiv = document.createElement("div");
    commentDiv.className = "flex-row background padding-sm";
    commentDiv.innerHTML = `
        <div class="flex-column w-1 writer">
            <p>${userId}</p>
            <p>${formatDate(writeDate)}</p>
        </div>
        <div class="flex-row w-9 comment">
            <p>${comment}</p>
        </div>
    `;

    // 댓글 리스트에 추가.
    document.querySelector(".tierListCommentsList").appendChild(commentDiv);
    // 입력란 내용 제거.
    document.getElementById("userId").value = "";
    document.getElementById("password").value = "";
    document.getElementById("comment").value = "";
}

const clickedSubmitBtn = () => {
    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;
    const comment = document.getElementById('comment').value.trim();
    const tierListId = document.getElementById('tierListId').value;

    if(comment === ""){
        showAlert('댓글내용을 입력하세요.');
        return;
    }

    fetch('/tierList/detail', {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify({
            userId : userId,
            password : password,
            comment : comment,
            tierListId : tierListId
        })
    }).then(response => response.text())
    .then(text => {
        if(text === 'O'){
            const writeDate = new Date().toLocaleString();
            appendComment(userId, comment, writeDate);
        }else if(text === 'X'){
            showAlert('알수없는 에러가 발생하였습니다.');
        }
    })
    .catch(error => console.log('Error : ', error));
}
document.addEventListener("DOMContentLoaded", () => {
    const tiers = document.querySelectorAll('.tier');

    const submitBtn = document.getElementById('submitBtn');
    submitBtn.addEventListener('click', clickedSubmitBtn);

    const upBtn = document.getElementById('upBtn');
    upBtn.addEventListener('click', function(){
        const child = upBtn.firstElementChild;

    });

});