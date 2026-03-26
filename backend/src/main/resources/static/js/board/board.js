$(document).ready(function () {

    const quill = new Quill('#editor', {
        modules: {
            toolbar: [
                ['bold', 'italic', 'underline', 'blockquote'],
                [{ 'color': [] }, { 'background': [] }], 
                [{ 'align': [] }, { 'list': 'ordered'}, { 'list': 'bullet' }, { 'list': 'check' }, { 'indent': '-1'}, { 'indent': '+1' }],
                ['image', 'video'],
            ],
        },
        theme: 'snow',
    });

    quill.on('text-change', function () {
        $("#hidden_input").val(quill.root.innerHTML);
        let img = $('img');
        if (img.length > 0) {
            let src = img[img.length - 1].src;
            let file = base64toFile(src, "image");
            console.log((file.size / 1024 / 1024).toFixed(2) +"MB");
            checkTheFileSize(file);
        }
    });


    $('.submit').on('click', function () {
        turnLoading();
        $('.submit').prop('disabled', true); // 중복 클릭 방지

        let images = $('img');
        let title = $('#title').val();
        let password = $('#password').val();

        // validation
        if (title.trim() === '') {
            $("#title").val('');
            showMsgForTitle();
            finish();
            return;
        }

        if (password.trim() === '') {
            $("#password").val('');
            showMsgForPw();
            finish();
            return;
        }

        let promiseList = [];

        // 이미지 업로드
        for (let i = 0; i < images.length; i++) {
            let fileName = uuidv4() + ".png";
            let src = images[i].src;
            let file = base64toFile(src, fileName);

            const uploadPromise = uploadFile(file, fileName)
                .then(uploadData => {
                    return downloadFile(uploadData.data.path);
                })
                .then(downloadData => {
                    return downloadData.data.publicUrl;
                })
                .catch(error => {
                    console.error("이미지 업로드 실패:", error);
                    throw error; // 전체 실패 처리
                });

            promiseList.push(uploadPromise);
        }

        // 이미지 없는 경우
        if (promiseList.length === 0) {
            submitPost();
            return;
        }

        // 이미지 처리 후
        Promise.all(promiseList)
            .then(urlList => {
                let imgs = document.getElementsByTagName('img');

                for (let i = 0; i < urlList.length; i++) {
                    imgs[i].src = urlList[i];
                }

                submitPost();
            })
            .catch(err => {
                alert("이미지 업로드 중 오류가 발생했습니다.");
                console.error(err);
                finish();
            });

        // POST 요청.
        function submitPost() {
            const data = {
                category: $('.clicked').attr('value'),
                nickName: $('#nickName').val(),
                title: $('#title').val(),
                contents: quill.root.innerHTML,
                password: $('#password').val()
            };

            fetch('/board/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(res => {
                if (!res.ok) {
                    if (res.status === 429) {
                        alert("1분 후 다시 시도해주세요.");
                    } else if (res.status === 400) {
                        alert("입력값을 확인해주세요.");
                    } else {
                        alert("서버 오류가 발생했습니다.");
                    }
                    throw new Error("POST 실패");
                }
                return res;
            })
            .then(() => {
                alert("작성 완료!");
                window.location.href = "/board";
            })
            .catch(err => {
                console.error(err);
            })
            .finally(() => {
                finish();
            });
        }

        // 종료 처리
        function finish() {
            turnLoading();
            $('.submit').prop('disabled', false);
        }
    });

    function showMsgForTitle() {
        $('.title-warning-message').show();
        setTimeout(() => {
            $('.title-warning-message').delay(3000).hide();
        }, 3000);
    }

    function showMsgForPw() {
        $('.password-warning-message').show();
        setTimeout(() => {
            $('.password-warning-message').delay(3000).hide();
        }, 3000);
    }

    $('.cancel').on('click', function () {
        $.confirm({
            theme: 'supervan',
            title: '',
            content: '글 작성을 취소하시겠습니까?',
            buttons: {
                '네': function () {
                    javascript: history.go(-2);
                },
                '아니오': function () {

                }
            }
        });
    });

    function checkTheFileSize(file) {        
        if (file.size > (1024 ** 2) *5) { // 5 MB
            $.confirm({
                theme: 'supervan',
                title: '',
                content: '한 이미지당 5MB까지 이미지등록을 허용합니다.',
                buttons: {
                    '돌아가기': function () {
                        $("img:last-child").remove();
                        return;
                    }
                }
            });
        }
    }

}); // end of jquery 

/* supabase */
const SUPABASE_URL = 'https://umzwbuofpryvyimxrxem.supabase.co';
const SUPABASE_ANON_KEY = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVtendidW9mcHJ5dnlpbXhyeGVtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjk4Mjg3MzQsImV4cCI6MjA0NTQwNDczNH0.-023sQ4iwStD9lIQggNwwtYyolpQiF8tNdZo8gAfkwk';
const PUBLIC_STORAGE_BUCKET = 'AH_Board_Images';
const client = supabase.createClient(SUPABASE_URL, SUPABASE_ANON_KEY);

async function uploadFile(file, fileName) {
    return await client
                .storage
                .from(PUBLIC_STORAGE_BUCKET)
                .upload(fileName, file);
}

async function downloadFile(fileName) {
    return await client
                .storage
                .from(PUBLIC_STORAGE_BUCKET)
                .getPublicUrl(fileName, {});
}

/* 카테고리 클릭 */
function clickedCategory(li){
    $('.category').removeClass('clicked');
    $(li).addClass('clicked');
    let clickedNow = $('.clicked').attr('value')
    location.href = `/board?category=${clickedNow}`;
}

function base64toFile(base_data, filename) {

    let arr = base_data.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);

    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }

    return new File([u8arr], filename, { type: mime });
}

function clicked_Category(li) {
    $('.category').removeClass('clicked');
    $(li).addClass('clicked');
}




