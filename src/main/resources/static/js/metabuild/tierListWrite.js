let itemList = {};
let keys = ["weapon", "helmet", "armor", "shoes", "cape", "potion", "food", "mount"];
function loadItemList(){
    fetch("/static/jsonData/category.json")
    .then(response => response.json())
    .then(data => {
        itemList = data;
        keys.forEach(category => {
            let items = itemList;
            const div = document.getElementsByClassName('itemListDiv')[0];
            
            items[category].forEach(e => {
                const img = document.createElement('img');
                img.src = `https://render.albiononline.com/v1/item/${e}.png`;
                img.className += category;
                div.appendChild(img);
            });
        });
    })
    .catch(error => {
        console.log(`에러 : ${error}`);
    });
}
window.addEventListener("DOMContentLoaded", loadItemList);

const inputData = [];
const tier = [
    '',
    't1',
    't2',
    't3',
    't4',
    't5',
    't6',
    't7',
    't8',
    't9',
    't10'
];
const maxIndex = 11;
let index = 1;

function addTier(){

    const inputTierData = document.getElementById('tierStr').value;
    
    if(inputTierData === '' || inputTierData === undefined){
        showAlert('티어명을 입력해주세요.');
        return;
    }

    if(index >= 1 && index < maxIndex){
        inputData[index] = inputTierData;
        let textSpan = document.getElementById(`${index}t`);
        textSpan.innerText = inputData[index];

        const tierElement = document.getElementById(index);
        tierElement.className += ' show';

        const parent = textSpan.parentNode;
        parent.className += ` ${tier[index]}b`;
        parent.parentNode.className += ` ${tier[index]}`;

        index++;
    }else if(index == maxIndex){i
        showAlert('더 이상 추가할 수 없습니다.')
    }

    document.getElementById('tierStr').value = '';
}

function changePart(e){
    document.querySelectorAll('.content-item').forEach(btn => btn.classList.remove('clicked'));
    e.classList.add('clicked');
}

// 특정 아이템 카테고리를 클릭했을 때
function changePart(e){
    const category = e.innerText;
    let obj;
    switch (category) {
        case '무기':
            
            break;
        case '투구':
        
        break;
        case '갑옷':
            
            break;
        case '신발':
        
        break;
        case '망토':
        
        break;
        case '포션':
            
            break;
        case '음식':
        
        break;
        case '탈것':
            
        break;
    }

}