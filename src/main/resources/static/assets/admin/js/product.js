let pathProduct = "http://localhost:8080/rest";

app.controller("ctrl-product", function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.categories = []; // container value of categoryId

	// load categoryId into select box
	$http.get("/rest/category").then(resp => {
		$scope.categories = resp.data;
	});


	$scope.load_all = function() {
		var url = `${pathProduct}/product`;
		$http.get(url).then(resp => {
			$scope.items = resp.data;
			console.log("Success", resp)
		}).catch(errors => {
			console.log("Error", errors)
		});
	}

	$scope.edit = function(id) {
		var url = `${pathProduct}/product/${id}`;
		$http.get(url).then(resp => {
			$scope.form = resp.data;
			console.log("Success", resp);
		}).catch(errors => {
			console.log("Error", errors);
		});
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `${pathProduct}/product/${$scope.form.id}`;
		$http.put(url, item).then(resp => {
			var index = $scope.items.findIndex(item => item.productId == $scope.form.productId);
			$scope.items[index] = resp.data;
			$scope.reset();
			$scope.load_all();
			console.log("Success", resp);
			alert("Cập nhật sản phẩm thành công");
		}).catch(error => {
			console.log("Error", error);
		});
	}

	$scope.create = function() {
		var item = angular.copy($scope.form);
		let check = false;
		console.log(item)
		if ($scope.myForm.$valid) {
			function showError(elementSelector, errorMessage) {
				var errorElement = document.querySelector(elementSelector);
				errorElement.innerText = errorMessage;
				errorElement.style.display = 'block';

				setTimeout(function() {
					errorElement.style.display = 'none';
				}, 5000); // 5000 milliseconds = 5 seconds
			}

			if (item.name == '' || !item.name) {
				showError(".errName", "Chưa Điền Tên");
				check = true;
			} else {
				document.querySelector(".errName").style.display = 'none';
				check = false;
			}

			if (item.price == '' || !item.price) {
				showError(".errPrice", "Chưa Điền Giá");
				check = true;
			} else if (!/^\d+$/.test(item.price) || item.price < 0) {
				showError(".errPrice", "Giá phải là số dương");
				check = true;
			} else {
				angular.element(document.querySelector(".errPrice")).css('display', 'none');
				check = false;
			}

			if (item.productQuantity == '' || !item.productQuantity) {
				showError(".errproductQuantity", "Chưa Điền Số Lượng");
				check = true;
			} else if (!/^\d+$/.test(item.productQuantity) || item.productQuantity < 0) {
				showError(".errproductQuantity", "Số Lượng phải là số dương");
				check = true;
			} else {
				angular.element(document.querySelector(".errproductQuantity")).css('display', 'none');
				check = false;
			}

			if (item.id == '' || !item.id) {
				showError(".erridProduct", "Chưa Điền ID Sản Phẩm");
				check = true;
			} else if (!/^\d+$/.test(item.id) || item.id < 0) {
				showError(".erridProduct", "ID Sản Phẩm phải là số dương");
				check = true;
			} else {
				angular.element(document.querySelector(".erridProduct")).css('display', 'none');
				check = false;
			}

			if (item.available == '' || !item.available) {
				showError(".erravailable", "Chưa Điền Available");
				check = true;
			} else {
				document.querySelector(".erravailable").style.display = 'none';
				check = false;
			}
			if (item.status == '' || !item.status) {
				showError(".errstatus", "Chưa Điền Trạng Thái");
				check = true;
			} else {
				document.querySelector(".errstatus").style.display = 'none';
				check = false;
			}
			if (item.description == '' || !item.description) {
				showError(".errdescription", "Chưa Điền Nội Dung");
				check = true;
			} else {
				document.querySelector(".errdescription").style.display = 'none';
				check = false;
			}

			if (!item.category || !item.category.id) {
				showError(".errcategory", "Vui Lòng Chọn ID Sản Phẩm");
				check = true;
			}





			if (check) {
				console.log('Vẫn còn Lỗi')
				return
			} else {

				var url = `${pathProduct}/product`;
				console.log('Bắt đầu gửi dữ liệu')
				$http.post(url, item).then(resp => {
					$scope.items.push(item);
					$scope.reset();
					$scope.load_all();
					console.log("Success", resp);
					alert("Tạo sản phẩm thành công");
				}).catch(error => {
					console.log("Error", error);
				});
			}

		}
	}

	$scope.delete = function(id) {
		var url = `${pathProduct}/product/${id}`;
		$http.delete(url).then(resp => {
			// tìm ra phần tử tại vị trí sẽ xóa.
			var index = $scope.items.findIndex(item => item.id == id);
			$scope.items.splice(index, 1); // tại vị trí đó và xóa 1 phần tử
			$scope.reset();
			console.log("Success", resp);
			alert("Xóa sản phẩm thành công");
		}).catch(error => {
			console.log("lỗi xoá", error);
		});
	}

	// Upload img
	$scope.imageChanged = function(files) {
		// tạo 1 đối tượng FormData
		var data = new FormData();
		// lấy file đã chọn bỏ vào FormData
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			alert("Error upload image");
			console.log("Error", error);
		})
	}

	$scope.reset = function() {
		$scope.form = {
			image: 'default.jpg',
		};
		
	}

	$scope.load_all();
	$scope.reset();

	// Pagination
	$scope.currentPage = 1; // Khởi tạo trang hiện tại là trang 1
	$scope.itemsPerPage = 5; // Thiết lập số sản phẩm hiển thị trên mỗi trang

	// Lấy tổng số trang
	$scope.totalPages = function() {
		var totalPages = 0;
		for (var i = 0; i < $scope.items.length; i += $scope.itemsPerPage) {
			totalPages++;
		}
		return totalPages;

	};

	// begin <=> currentPage
	$scope.prev = function() {
		if ($scope.currentPage > 0) {
			$scope.currentPage -= itemsPerPage;
		}
	}
	$scope.setPage = function(pageNum) {
		$scope.currentPage = pageNum;
	};


	// Lấy các sản phẩm của trang hiện tại
	$scope.getCurrentItems = function() {
		var startIndex = ($scope.currentPage - 1) * $scope.itemsPerPage;
		var endIndex = startIndex + $scope.itemsPerPage;
		return $scope.items.slice(startIndex, endIndex);
	};

	$scope.setPageFirst = function() {
		$scope.currentPage = 1;
	};

	$scope.setPageLast = function() {
		$scope.currentPage = $scope.totalPages();
	};

});