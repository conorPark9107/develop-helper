let itemList;
document.addEventListener("DOMContentLoaded", () => {
    function loadItemList() {
        //    fetch("/static/jsonData/category.json")
        fetch("/jsonData/category.json")
            .then(response => response.json())
            .then(data => {
            itemList = data;
            const itemListDiv = document.querySelector('.itemListDiv');
            Object.keys(data).forEach(category => {
                data[category].forEach(e => {
                    const div = document.createElement('div');
                    div.classList.add('image-container', 'draggable');
                    div.dataset.title = e.name;
                    div.draggable = true;

                    const img = document.createElement('img');
                    img.src = `https://render.albiononline.com/v1/item/${e.id}.png?quality=4`;
                    img.alt = e.name;
                    img.classList.add('img', category);
                    img.id = e.id;
                    img.draggable = false;

                    div.appendChild(img);
                    itemListDiv.appendChild(div);
                });
            });

            addDragAndDropEvents();
        })
        .catch(error => console.log(`에러: ${error}`));
    }

    function addDragAndDropEvents() {
        const draggables = document.querySelectorAll('.image-container');
        const tierLists = document.querySelectorAll('.rankArea');
        const itemDiv = document.querySelector('.itemListDiv');

        let placeholder = document.createElement('div');
        placeholder.classList.add('img-placeholder');


        draggables.forEach(draggable => {
            draggable.addEventListener("dragstart", e => {
                e.target.classList.add("dragging");
                setTimeout(() => e.target.classList.add("hide"), 0);
            });

            draggable.addEventListener("dragend", e => {
                e.target.classList.remove("dragging");
                e.target.classList.remove("hide");
                placeholder.remove();
            });
        });

        itemDiv.addEventListener("dragover", e => {
            e.preventDefault();
        });

        itemDiv.addEventListener("drop", e => {
            e.preventDefault();
            const draggedItem = document.querySelector(".dragging");
            if (draggedItem) {
                itemDiv.appendChild(draggedItem);
            }
        });

        // 각 tierList에 드래그 앤 드롭 이벤트 추가
        tierLists.forEach(tier => {
            tier.addEventListener("dragover", e => {
                e.preventDefault();
                const afterElement = getDragAfterElement(tier, e.clientX);
                const draggedItem = document.querySelector(".dragging");

                const length = tier.children.length;
                if(length >= 7){
                    return;
                }

                if (draggedItem) {
                    tier.classList.add("dragover-active");
                    if (afterElement) {
                        tier.insertBefore(placeholder, afterElement);
                    } else if (!afterElement && tier.lastChild !== placeholder) {
                        tier.appendChild(placeholder);
                    }
                }
            });

            tier.addEventListener("dragleave", () => {
                tier.classList.remove("dragover-active");
                placeholder.remove();
            });

            tier.addEventListener("drop", e => {
                e.preventDefault();
                const draggedItem = document.querySelector(".dragging");
                if (draggedItem) {
                    placeholder.replaceWith(draggedItem); // placeholder를 드래그한 이미지로 교체
                }
            });
        });
    }

    function getDragAfterElement(container, x) {
        const draggableElements = [...container.querySelectorAll(".image-container:not(.dragging)")];

        let closest = { offset: Number.POSITIVE_INFINITY, element: null };
        const threshold = 40; // 작은 움직임이면 변화 없음

        draggableElements.forEach(child => {
            const box = child.getBoundingClientRect();
            const centerX = box.left + (box.width / 2);
            const offset = centerX - x; // 마우스 위치와 중앙값 비교

            if (Math.abs(offset) > threshold && offset > 0 && offset < closest.offset) {
                closest = { offset: offset, element: child };
            }
        });

        return closest.element;
    }
//    const observer = new MutationObserver(mutations => {
//        mutations.forEach(mutation => {
//            if (mutation.type === "childList") {
//                mutation.addedNodes.forEach(node => {
//                    if (node.tagName === "IMG") {
//                        addAltText(node);
//                    }
//                });
//            }
//        });
//    });
//
//    observer.observe(document.querySelector(".rankArea"), { childList: true, subtree: true });
//
//    function addAltText(img) {
//        const text = document.createElement("p");
//        text.textContent = img.alt;
//        text.classList.add("alt-text");
//        img.after(text);
//    }


    window.addEventListener("DOMContentLoaded", loadItemList);
});



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

// 티어 추가 버튼 클릭시.
function addTier() {

    const inputTierData = document.getElementById('tierStr').value;

    if (inputTierData === '' || inputTierData === undefined) {
        showAlert('티어명을 입력해주세요.');
        return;
    }else if(inputTierData.length > 10){
        showAlert('10글자보다 초과하여 입력할수 없습니다.');
        return;
    }

    if (index >= 1 && index < maxIndex) {
        inputData[index] = inputTierData;
        let textSpan = document.getElementById(`${index}t`);
        textSpan.innerText = inputData[index];

        const tierElement = document.getElementById(index);
        tierElement.classList.add('show');

        const parent = textSpan.parentNode;
        parent.classList.add(`${tier[index]}b`);
        parent.parentNode.classList.add(`${tier[index]}`);

        index++;
    } else if (index == maxIndex) {
        showAlert('더 이상 추가할 수 없습니다.')
    }

    document.getElementById('tierStr').value = '';
}

// 특정 아이템 카테고리를 클릭했을 때
function changePart(e) {
    let category = e.innerText;
    e.classList.add('clicked'); // 클릭한 요소에 'clicked' 클래스 추가

    let categories = document.getElementsByClassName('content-item');
    Array.from(categories).forEach(cate => {
        if (cate !== e) {
            cate.classList.remove('clicked'); // 클릭되지 않은 요소에서 'clicked' 제거
        }
    });

    const itemImages = document.querySelectorAll('.itemListDiv > .image-container > .img');

    category = getCategory(category);

    // 선택된 카테고리의 이미지만 보이도록 설정
    Array.from(itemImages).forEach(img => {
        if (img.classList.contains(category)) {
            img.parentElement.classList.remove('hide'); // 해당 카테고리 이미지 보이기
        } else {
            img.parentElement.classList.add('hide'); // 다른 이미지 숨기기
        }
    });
}

// 카테고리에 맞는 객체명을 리턴
function getCategory(category){
    // 카테고리에 맞게 설정.
    switch (category) {
        case '전체': category = 'img'; break;
        case '무기': category = 'weapon'; break;
        case '투구': category = 'helmet'; break;
        case '갑옷': category = 'armor'; break;
        case '신발': category = 'shoes'; break;
        case '망토': category = 'cape'; break;
        case '포션': category = 'potion'; break;
        case '음식': category = 'food'; break;
        case '탈것': category = 'mount'; break;
    }
    return category;
}

// input에 사용자가 아이템명을 검색하고자 입력했을때 발생(keyUp)
function inputKeyUp(e){
    const inputFromUser = e.value;
    const categoryNow = document.getElementsByClassName('clicked')[0].innerText;
    const category = getCategory(categoryNow);
    const itemImages = document.querySelectorAll('.itemListDiv > .image-container > .img');
    let filteredItems = []; // 검색된 아이템을 담을 배열

    if(category === 'img'){ // 카테고리가 현재 전체일 경우.
        Object.keys(itemList).forEach(category => {
            filteredItems = filteredItems.concat(
                itemList[category].filter(item => item.name.includes(inputFromUser))
            );
        });
    }else{ // 카테고리가 전체가 아닌 다른것이 선택되어있는 경우.
        filteredItems = itemList[category].filter(item => item.name.includes(inputFromUser));
    }

    // 검색된 아이템 목록에 해당하는 이미지만 'hide'를 없애줌.
    Array.from(itemImages).forEach(img => {
        if(!filteredItems.some(item => item.name === img.alt)){
            img.parentElement.classList.add('hide');
        }else{
            img.parentElement.classList.remove('hide');
        }
    });
}

// 티어 삭제 버튼 클릭시
function removeTier(){
    index = Math.max(1, index - 1);
    const tier = document.getElementById(index);
    const imgArea = document.querySelectorAll('.itemListDiv')[0];

    tier.classList.remove('show');
    const removedImges = tier.children[1].querySelectorAll('img');
    removedImges.forEach(img => {
        imgArea.prepend(img);
    });
}

function submit(){
    const tierList = [...document.querySelectorAll('.rankArea')];
    const writer = document.getElementById('writer');
    const title = document.getElementById('input');
    const category = document.querySelector('input[name="category"]:checked').value;

    if(title.value === ''){
        title.focus();
        showAlert('게시글 제목을 입력해주세요.');
        return;
    }else if (tierList.filter(e => e.parentElement.classList.contains('show')).length <= 0){
        showAlert('하나 이상의 티어가 존재해야 합니다. 티어를 추가해주세요.');
        return;
    }

    const userId = writer.value === ''? '익명' : writer.value;

    const t1Name = document.getElementById('1t').innerHTML;
    const t2Name = document.getElementById('2t').innerHTML;
    const t3Name = document.getElementById('3t').innerHTML;
    const t4Name = document.getElementById('4t').innerHTML;
    const t5Name = document.getElementById('5t').innerHTML;
    const t6Name = document.getElementById('6t').innerHTML;
    const t7Name = document.getElementById('7t').innerHTML;
    const t8Name = document.getElementById('8t').innerHTML;
    const t9Name = document.getElementById('9t').innerHTML;
    const t10Name = document.getElementById('10t').innerHTML;

    const t1 = [...tierList[0].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t2 = [...tierList[1].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t3 = [...tierList[2].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t4 = [...tierList[3].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t5 = [...tierList[4].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t6 = [...tierList[5].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t7 = [...tierList[6].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t8 = [...tierList[7].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t9 = [...tierList[8].querySelectorAll('div .img')].map(img => img.id).join(" ");
    const t10 = [...tierList[9].querySelectorAll('div .img')].map(img => img.id).join(" ");

    console.log(JSON.stringify({
        userId : userId,
        title : title.value,
        category : category,
        t1 : t1,
        t2 : t2,
        t3 : t3,
        t4 : t4,
        t5 : t5,
        t6 : t6,
        t7 : t7,
        t8 : t8,
        t9 : t9,
        t10 : t10,
        t1Name : t1Name,
        t2Name : t2Name,
        t3Name : t3Name,
        t4Name : t4Name,
        t5Name : t5Name,
        t6Name : t6Name,
        t7Name : t7Name,
        t8Name : t8Name,
        t9Name : t9Name,
        t10Name : t10Name
    }));

    fetch("/tierList/write", {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify({
            userId : userId,
            title : title.value,
            category : category,
            t1 : t1,
            t2 : t2,
            t3 : t3,
            t4 : t4,
            t5 : t5,
            t6 : t6,
            t7 : t7,
            t8 : t8,
            t9 : t9,
            t10 : t10,
            t1Name : t1Name,
            t2Name : t2Name,
            t3Name : t3Name,
            t4Name : t4Name,
            t5Name : t5Name,
            t6Name : t6Name,
            t7Name : t7Name,
            t8Name : t8Name,
            t9Name : t9Name,
            t10Name : t10Name
        })
    })
    .then(response => response.text())
    .then(data => {
        $.confirm({
            theme: 'supervan',
            title: '',
            content: '작성 완료하였습니다.',
            buttons: {
                '네': function (e) {
                    window.location.href = '/tierList';
                }
            }
        });
    })
    .catch(error => {
        showAlert('알수없는 에러 발생하였습니다. 관리자 문의에 문의 남겨주세요.');
        console.log('Error : ', error)
    });
}



