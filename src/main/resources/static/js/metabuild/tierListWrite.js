let itemList = null;
const tier = [
    
];
const maxIndex = 11;
let index = 1;

fetch('/static/jsonData/items.json')
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.log(error));



function addTier(){

    const inputTierData = document.getElementById('tierStr').value;
    
    if(inputTierData === '' || inputTierData === undefined){
        showAlert('티어명을 입력해주세요.');
        return;
    }

    if(index >= 1 && index < maxIndex){
        tier[index] = inputTierData;
        let textSpan = document.getElementById(`${index}t`);
        textSpan.innerText = tier[index];

        const tierElement = document.getElementById(index);
        tierElement.className += ' show';
        index++;
    }else if(index == maxIndex){
        showAlert('더 이상 추가할 수 없습니다.')
    }

    document.getElementById('tierStr').value = '';
}