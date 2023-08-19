let pathAccount = "http://localhost:8080/rest";
app.controller("ctrl-accountadmin", function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.items2 = [];
	
	//Hien Pass
	$scope.showPassword = false;

    $scope.toggleShowPassword = function() {
        $scope.showPassword = !$scope.showPassword;
    };


	$scope.load_all = function() {
		var url = `${pathAccount}/accountadmin`;
		$http.get(url).then(resp => {
			$scope.items = resp.data;
			console.log("Success", resp)
		}).catch(errors => {
			console.log("Error", errors)
		});
	}

	$scope.load_Customer = function() {
		var url = `${pathAccount}/accountcustomer`;
		$http.get(url).then(resp => {
			$scope.items2 = resp.data;
			console.log("Success", resp)
		}).catch(errors => {
			console.log("Error", errors)
		});
	}

	$scope.edit = function(id) {
		var url = `${pathAccount}/accountadmin/${id}`;
		$http.get(url).then(resp => {
			$scope.form = resp.data;
			console.log("Success", resp);
		}).catch(errors => {
			console.log("Error", errors);
		});
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `${pathAccount}/accountadmin/${$scope.form.username}`;
		$http.put(url, item).then(resp => {
			var index = $scope.items.findIndex(item => item.username == $scope.form.username);
			$scope.items[index] = resp.data;
			$scope.reset();
			$scope.load_all();
			$scope.load_Customer();
			console.log("Success", resp);
			alert("Cập nhật tài khoản thành công");
		}).catch(error => {
			console.log("Error", error);
		});
	}

	$scope.create = function() {
		var item = angular.copy($scope.form);
		let check = false;
		console.log(item)
				var url = `${pathAccount}/accountadmin`;
				console.log('Bắt đầu gửi dữ liệu')
				$http.post(url, item).then(resp => {
					$scope.items.push(item);
					$scope.reset();
					$scope.load_all();
					alert("Tạo tài khoản thành công");
					console.log("Success", resp);
				}).catch(error => {
					console.log("Error", error);
				});
			}


	$scope.delete = function(id) {
		var url = `${pathAccount}/accountadmin/${id}`;
		$http.delete(url).then(resp => {
			// tìm ra phần tử tại vị trí sẽ xóa.
			var index = $scope.items.findIndex(item => item.id == id);
			$scope.items.splice(index, 1); // tại vị trí đó và xóa 1 phần tử
			$scope.reset();
			console.log("Success", resp);
			alert("Xóa tài khoản thành công");
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
		$http.post('/rest/upload/img', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.photo = resp.data.name;
		}).catch(error => {
			alert("Error upload image");
			console.log("Error", error);
		})
	}

	$scope.reset = function() {
		$scope.form = {
			photo: 'default.png',
		};
		
	}

	$scope.load_all();
	
	$scope.load_Customer();
	
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

	$scope.getCurrentItems2 = function() {
		var startIndex = ($scope.currentPage - 1) * $scope.itemsPerPage;
		var endIndex = startIndex + $scope.itemsPerPage;
		return $scope.items2.slice(startIndex, endIndex);
	};

	$scope.setPageFirst = function() {
		$scope.currentPage = 1;
	};

	$scope.setPageLast = function() {
		$scope.currentPage = $scope.totalPages();
	};

});