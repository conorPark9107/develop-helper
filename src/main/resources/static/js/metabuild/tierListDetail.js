const clickedSubmitBtn = () => {
    const userId = document.getElementById('userId');
    const password = document.getElementById('password');
    const comment = document.getElementById('comment');
    fetch('/tierList/detail', {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify({
            userId : userId,
            password : password,
            comment : comment
        })
    }).then(response => response.text())
    .then(text => {
        showAlert(text);
        window.location.href = '/tierList/detail'
    })
    .catch(error => console.log('Error : ', error));
}
document.addEventListener("DOMContentLoaded", () => {
    const tiers = document.querySelectorAll('.tier');

    const submitBtn = document.getElementById('submitBtn');
    submitBtn.addEventListender('click', clickedSubmitBtn);
});