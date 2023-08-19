document.addEventListener('DOMContentLoaded', function() {
	var orderTable = document.getElementById('orderTable');
	var loadMoreBtn = document.getElementById('loadMoreBtn');
	var rowCount = orderTable.rows.length - 1; // Trừ đi hàng header

	// Ẩn các hàng thứ 6 trở đi ban đầu
	for (var i = 5; i < rowCount; i++) {
		orderTable.rows[i].style.display = 'none';
	}

	// Xử lý sự kiện khi nhấp vào nút "Xem thêm"
	loadMoreBtn.addEventListener('click', function() {
		for (var i = 5; i < rowCount; i++) {
			if (orderTable.rows[i].style.display === 'none') {
				orderTable.rows[i].style.display = '';
			} else {
				break; // Dừng hiển thị nếu đã đạt tới số hàng tối đa
			}
		}
	});
});
