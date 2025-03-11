document.addEventListener("DOMContentLoaded", () => {
    function loadItemList() {
        fetch("/static/jsonData/category.json")
            .then(response => response.json())
            .then(data => {
                const itemListDiv = document.querySelector('.itemListDiv');
                Object.keys(data).forEach(category => {
                    data[category].forEach(e => {
                        const img = document.createElement('img');
                        img.src = `https://render.albiononline.com/v1/item/${e}.png`;
                        img.classList.add('img', 'draggable', category);
                        img.id = e;
                        img.draggable = true;
                        itemListDiv.appendChild(img);
                    });
                });

                addDragAndDropEvents();
            })
            .catch(error => console.log(`에러: ${error}`));
    }

    function addDragAndDropEvents() {
        const draggables = document.querySelectorAll('.img');
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
        const draggableElements = [...container.querySelectorAll(".img:not(.dragging)")];
    
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
    const itemImages = document.querySelectorAll('.itemListDiv > .img');

    let obj;
    switch (category) {
        case '전체':
            category = 'img';
            break;
        case '무기':
            category = 'weapon';
            break;
        case '투구':
            category = 'helmet';
            break;
        case '갑옷':
            category = 'armor';
            break;
        case '신발':
            category = 'shoes';
            break;
        case '망토':
            category = 'cape';
            break;
        case '포션':
            category = 'potion';
            break;
        case '음식':
            category = 'food';
            break;
        case '탈것':
            category = 'mount';
            break;
    }

    Array.from(itemImages).forEach(img => {
        if (img.classList.contains(category)) {
            img.classList.remove('hide');
        } else {
            img.classList.add('hide');
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



