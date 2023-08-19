var app = angular.module("account", []);
let pathAccount = "http://localhost:8080/rest";

app.controller("ctrl-account", function($scope, $http){
	$scope.form = {};
	$scope.items = [];
	$scope.loading = false;
	$scope.loadingSuccess = false; // Thêm biến loadingSuccess
	
	$scope.load_all = function(){
        var url = `${pathAccount}/account`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            console.log("Success", resp)
        }).catch(errors => {
            console.log("Error", errors)
        });
    }
    
    $scope.edit = function(username){
		var url = `${pathAccount}/account/${username}`;
		$http.get(url).then(resp => {
			$scope.form = resp.data;
			console.log("Success", resp);
		}).catch(errors => {
			console.log("Error", errors);
		});
	}
	
	$scope.update = function(){
        var item = angular.copy($scope.form);
        var url = `${pathAccount}/account/${$scope.form.username}`;
        $http.put(url, item).then(resp => {
            var index = $scope.items.findIndex(item => item.username == $scope.form.username);
            $scope.items[index] = resp.data;
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }
    
    //Hien Pass
    $scope.showPassword = false;

    $scope.toggleShowPassword = function() {
        $scope.showPassword = !$scope.showPassword;
    };
    
$scope.create = function () {
    // Bắt đầu hiệu ứng loading
    $scope.loading = true;

    // Reset error and success messages
    $scope.errorMessage = null;
    $scope.successMessage = null;

    // Kiểm tra các trường input trước khi gửi yêu cầu đăng ký
    if (!$scope.form.username || !$scope.form.fullname || !$scope.form.email || !$scope.form.phone || !$scope.form.password) {
        $scope.errorMessage = "Vui lòng điền đầy đủ thông tin.";
        // Dừng hiệu ứng loading nếu có lỗi
        $scope.loading = false;
        return; // Dừng thực hiện tiếp
    }

    // Kiểm tra định dạng email hợp lệ
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test($scope.form.email)) {
        $scope.errorMessage = "Email không hợp lệ.";
        // Dừng hiệu ứng loading nếu có lỗi
        $scope.loading = false;
        return; // Dừng thực hiện tiếp
    }

    // Kiểm tra mật khẩu có ít nhất 6 ký tự
    if ($scope.form.password.length < 6) {
        $scope.errorMessage = "Mật khẩu phải có ít nhất 6 ký tự.";
        // Dừng hiệu ứng loading nếu có lỗi
        $scope.loading = false;
        return; // Dừng thực hiện tiếp
    }

    var item = angular.copy($scope.form);
    var url = `${pathAccount}/account`;

    $http.post(url, item)
        .then(function (response) {
            // Xử lý kết quả trả về từ API hoặc backend
            $scope.items.push(item);
            $scope.successMessage = "Đăng ký thành công";
            console.log("Success", response);

            // Dừng hiệu ứng loading sau khi xử lý thành công
            $scope.loading = false;
        })
        .catch(function (error) {
            // Xử lý lỗi nếu có
            console.error('Error:', error);
            $scope.errorMessage = "Error creating account. Please try again.";

            // Dừng hiệu ứng loading sau khi xử lý thất bại
            $scope.loading = false;
        });
};


    
    $scope.delete = function(username){
        var url = `${pathAccount}/product/${username}`;
        $http.delete(url).then(resp => {
            // tìm ra phần tử tại vị trí sẽ xóa.
            var index = $scope.items.findIndex(item => item.username == username);
            $scope.items.splice(index, 1); // tại vị trí đó và xóa 1 phần tử
            $scope.reset();
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }
    
    // Upload img
    $scope.imageChanged = function(files){
		// tạo 1 đối tượng FormData
		var data = new FormData();
		// lấy file đã chọn bỏ vào FormData
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		}).then(resp => {
			$scope.form.productImage = resp.data.name; 
		}).catch(error => {
			alert("Error upload image");
			console.log("Error", error);
		})
	}
    
    $scope.reset = function(){
		$scope.form = {
			productImage: 'cloud-upload.jpg',
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
	$scope.prev = function(){
		if($scope.currentPage > 0){
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