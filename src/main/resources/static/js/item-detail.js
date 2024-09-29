document.addEventListener('DOMContentLoaded', function () {
    const itemId = window.location.pathname.split('/').pop(); // URL에서 itemId 추출

    // 아이템 상세 정보 불러오기
    async function loadItemDetails() {
        const response = await fetch(`/api/v1/item/${itemId}`);
        const item = await response.json();

        document.getElementById('itemName').innerText = item.name;
        document.getElementById('itemDescription').innerText = item.description;
        const imageList = document.getElementById('imageList');
        imageList.innerHTML = '';
        item.imageList.forEach(image => {
            const li = document.createElement('li');
            const img = document.createElement('img');
            img.src = image.imageUrl;  // 이미지 URL
            img.alt = 'Description Image';
            img.width = 500;  // 이미지 크기 설정
            li.appendChild(img);
            imageList.appendChild(li);
        });
    }


    // 이미지 업로드 처리
    const uploadImageForm = document.getElementById('uploadImageForm');
    uploadImageForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const formData = new FormData();
        const image = document.getElementById('image').files[0];
        formData.append('image', image);

        const response = await fetch(`/api/v1/item/create_description_image/${itemId}`, {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            alert('Image uploaded successfully');
            loadItemDetails(); // 이미지 목록 다시 로드
        } else {
            alert('Failed to upload image');
        }
    });

    // 페이지 로드 시 아이템 정보와 이미지 목록 불러오기
    loadItemDetails();
});
