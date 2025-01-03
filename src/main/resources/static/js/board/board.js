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
        let images = $('img'); // 이미지들.
        let title = $('#title').val();
        let password = $('#password').val();

        if (title.trim() == '') {
            $("#title").val('');
            showMsgForTitle();
            turnLoading();
            return;
        }
        if (password.trim() == '') {
            $("#password").val('');
            showMsgForPw();
            turnLoading();
            return;
        }

        let promiseList = [];
        for (let i = 0; i < images.length; i++) {
            let fileName = uuidv4() + ".png";
            let src = images[i].src;
            let file = base64toFile(src, fileName);
            const uploadPromise = uploadFile(file, fileName);
            promiseList[i] = uploadPromise.then(uploadData => {
                return downloadFile(uploadData.data.path);
            }).then(downloadData => {
                return downloadData;
            }).catch(error => {
                console.log(error);
            });
        }

        Promise.all(promiseList).then(dataArr => {
            let images = document.getElementsByTagName('img');
            for (let i = 0; i < dataArr.length; i++) {
                images[i].src = dataArr[i].data.publicUrl;
            }
            const category = $('.clicked').attr('value');
            const nickName = $('#nickName').val();
            const title = $('#title').val();
            const password = $('#password').val();
            const requestHTML = quill.root.innerHTML;
            
            $('#h_category').val(category);
            $('#h_nickName').val(nickName);
            $('#h_title').val(title);
            $('#h_contents').val(requestHTML);
            $('#h_password').val(password);
            $('#submitForm').submit();
        });


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




