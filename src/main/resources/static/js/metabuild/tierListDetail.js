fetch("/jsonData/category.json")
    .then(response => response.json())
    .then(data => {
    const itemListDiv = document.querySelector('.itemListDiv');
    Object.keys(data).forEach(category => {

    });
})
    .catch(error => console.log(`에러: ${error}`));


function appendComment(userId, comment, writeDate, commentId){

    const num = document.getElementById('commentNum');
    const num2 = document.getElementById('commentNum1');
    num2.innerText = Number(num2.innerText) + 1;
    num.innerText = Number(num.innerText) + 1;

    if(userId === ""){
        userId = '익명';
    }

    // 새로운 댓글 요소 생성.
    const commentDiv = document.createElement("div");
    commentDiv.className = "flex-row bg-comment padding-sm align-center";
    commentDiv.innerHTML = `
        <div class="flex-column w-1 writer">
            <p>${userId}</p>
        </div>
        <div class="flex-row w-7 comment">
            <p>${comment}</p>
        </div>
        <div class="flex-row w-2 date">
            <p>${formatDate(writeDate)}</p>
            <button class="link deleteBtn" value="${commentId}" onclick="clickDeleteBtn(this)">❌</button>
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
        commentId = text.slice(1);
        if(text.startsWith("O")){
            const writeDate = new Date();
            appendComment(userId, comment, writeDate, commentId);
        }else if(text.startsWith('X')){
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
        const id = document.getElementById('tierListId').value;

        if(getCookie(`${id}-tierList`) === ""){
            fetch('/tierList/detail/up', {
                method : 'POST',
                headers : {
                    'Content-Type' : 'application/json'
                },
                body : JSON.stringify({
                    id: id
                })
            }).then(response => response.text())
                .then(data => {
                if(data === 'X'){
                    showAlert('추천작업 중 에러가 발생하였습니다. 관리자에게 문의해주세요.');
                    return;
                }else{
                    setCookie(`${id}-tierList`, "done" , 1);
                    child.innerHTML = Number(child.innerHTML) + 1;
                    const beforeUp = document.getElementById('up').innerText;
                    document.getElementById('up').innerText = Number(beforeUp) + 1;
                }
            });
        }else{
            showAlert("추천은 24시간에 한번씩 누를 수 있습니다.");
            return;
        }
    });

});

// 댓글 삭제 버튼을 클릭했을 때
function clickDeleteBtn(e){
    const commentId = e.value;

    $.confirm({
        theme: 'supervan',
        title: '정말로 댓글을 삭제하시겠습니까?',
        content: '<p>삭제를 원하신다면 <u>비밀번호</u>를 입력하고 확인버튼을 누르세요.</p>' +
        '<p>버튼 클릭시 곧 바로 <span class="red">삭제</span>처리되니 신중하게 눌러주세요.</p>' +
        '<input class="inputPw" type="password" name="inputPassword" />',
        buttons: {
            '확인': function () {
                inputPw = this.$content.find('.inputPw').val();
                fetch('/tierList/detail/delete', {
                    method : 'POST',
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    body : JSON.stringify({
                        id: commentId,
                        pw: inputPw
                    })
                }).then(response => response.text())
                    .then(s => {
                    if(s.startsWith("O")){
                        location.reload(false);
                    }else{
                        showAlert('패스워드가 맞지 않습니다. 다시 시도해주세요.');
                    }
                }).catch(error => {
                    conosole.log(`error : ${error}`);
                    showAlert('알수없는 에러가 발생하였습니다. 잠시후에 다시 시도해주세요.');
                });
            },
            '돌아가기': function () {}
        }
    });

}


/* 쿠키 Set */
function setCookie( name, value, exDay ) {
    var today = new Date();
    today.setDate( today.getDate() + exDay );
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";"
}

/* 쿠키 GET */
function getCookie(name)
{
    var obj = name + "=";
    var x = 0;
    while ( x <= document.cookie.length )
    {
        var y = (x+obj.length);
        if ( document.cookie.substring( x, y ) == obj )
        {
            if ((endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
            endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring( y, endOfCookie ) );
        }
        x = document.cookie.indexOf( " ", x ) + 1;

        if ( x == 0 ) break;
    }
    return "";
}
