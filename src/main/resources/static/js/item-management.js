document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('createItemForm').addEventListener('submit', async function (event) {
        event.preventDefault();

        // form 필드에서 입력값 가져오기
        const name = document.getElementById('name').value;
        const category = document.getElementById('category').value;
        const description = document.getElementById('description').value;
        const image = document.getElementById('image').files[0]; // 파일 선택

        // createItemDto 객체 생성
        const createItemDto = {
            name: name,
            category: category,
            description: description
        };

        // FormData 객체 생성
        const formData = new FormData();

        // JSON으로 직렬화된 createItemDto를 formData에 추가
        formData.append('createItemDto', new Blob([JSON.stringify(createItemDto)], { type: 'application/json' }));

        // 이미지 파일 formData에 추가 (파일이 있을 경우에만)
        if (image) {
            formData.append('image', image);
        }

        // 디버깅: formData의 내용 확인
        for (let [key, value] of formData.entries()) {
            console.log(`${key}: ${value}`);
        }

        // 서버로 데이터 전송
        const response = await fetch('/api/v1/item/create', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            alert('Item created successfully');
        } else {
            alert('Failed to create item');
        }
    });

    async function loadItems() {
        const response = await fetch('/api/v1/item');
        const items = await response.json();

        const itemList = document.getElementById('itemList');
        if (itemList) {
            itemList.innerHTML = '';

            items.forEach(item => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <strong>${item.name}</strong> - ${item.description}
                    <button onclick="startItem(${item.id})">Start</button>
                    <button onclick="stopItem(${item.id})">Stop</button>
                    <button onclick="deleteItem(${item.id})">Delete</button>
                    <button onclick="window.location.href='/admin/item/${item.id}'">상세정보</button> 
                `;
                itemList.appendChild(li);
            });
        }
    }
    // startItem 함수 전역으로 선언
    window.startItem = async function (itemId) {
        const response = await fetch(`/api/v1/item/start/${itemId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({})
        });

        if (response.ok) {
            alert('Item started successfully');
            loadItems(); // 다시 목록 불러오기
        } else {
            alert('Failed to start item');
        }
    }

    window.stopItem = async function (itemId) {
        const response = await fetch(`/api/v1/item/stop/${itemId}`, { method: 'POST' });

        if (response.ok) {
            alert('Item stopped successfully');
            loadItems();
        } else {
            alert('Failed to stop item');
        }
    }

    window.deleteItem = async function (itemId) {
        const response = await fetch(`/api/v1/item/delete/${itemId}`, { method: 'DELETE' });

        if (response.ok) {
            alert('Item deleted successfully');
            loadItems();
        } else {
            alert('Failed to delete item');
        }
    }

    // 페이지가 로드될 때 아이템 목록을 불러옵니다.
    loadItems();
});
