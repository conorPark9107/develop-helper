function appendComment(userId, comment, writeDate){

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
            const writeDate = new Date();
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