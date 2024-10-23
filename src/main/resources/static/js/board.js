$(document).ready(function(){
    const quill = new Quill('#editor', {
       modules: {
            toolbar: [
                  ['bold', 'italic', 'underline'],
                  ['image'],
                  ['blockquote'],
            ],
      },
      placeholder: '여기부터 글 작성..',
      theme: 'snow',
    });


    $('.submit').on('click', function(){
        const delta = quill.getContents();
        const html = quill.root.innerHTML.trim();
        console.log(delta);
        console.log(html);
        quill.root.innerHTML = html + '<p>테스트하는 중입니다</p>';
    });

    $('.cancel').on('click', function(){
        console.log('취소버튼');
    });



});

function clicked_Category(li){
    $('.category').css('font-size', '0.5em');
    $('.category').css('color', '');
    $(li).css('color', 'RGB(28, 120, 153)');
    $(li).css('font-size', '1em');
}
