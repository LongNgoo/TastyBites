const app = angular.module("shopping-cart", []);

app.controller("shopping-cart-ctrl", function ($scope, $http) {

	//Profile
	let pathProfile = "http://localhost:8080/rest";
	
	$scope.form = {};
	$scope.items = [];
	
	$scope.load_one = function(){
    var usernameElement = document.querySelector('#username2');
	    if (usernameElement) {
	        var username = usernameElement.innerText.trim();
	        var url = `${pathProfile}/profile/${username}`;
	        $http.get(url).then(resp => {
	            $scope.form = resp.data;
	            console.log("Success", resp);
	        }).catch(errors => {
	            console.log("Error", errors);
	        });
	    } else {
	        console.log("#username element not found");
	    }
	}
	
	$scope.changePassword = function() {
    var username = document.querySelector('#username2').innerText.trim();
    var newPassword = $scope.passwordData.newPassword;
    
    $http.put(`/rest/account/${username}/change-password`, newPassword)
        .then(resp => {
            console.log("Password changed successfully", resp);
            alert("Đổi Mật Khẩu Thành Công !");
        })
        .catch(error => {
            console.log("Error changing password", error);
            alert("Đổi Mật Khẩu Thất Bại");
        });
};

	
	$scope.load_one();
	
	$scope.update = function(){
        var item = angular.copy($scope.form);
        var username = document.querySelector('#username2').innerText.trim();
        var url = `${pathProfile}/profile/${username}`;
        $http.put(url, item).then(resp => {
            var index = $scope.items.findIndex(item => item.username == $scope.form.username);
            $scope.items[index] = resp.data;
            console.log("Success", resp);
            alert("Lưu Thông Tin Thành Công !");
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
		$http.post('/rest/upload/img', data, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		}).then(resp => {
			$scope.form.photo = resp.data.name; 
		}).catch(error => {
			alert("Error upload image");
			console.log("Error", error);
		})
	}
    
	//Profile
	
	
	 // Function to update the views in the Visitor table
    $scope.updateViews = function () {
        // Get the current visitor ID from some source (e.g., session or cookies)
        // Replace 'YOUR_VISITOR_ID' with the actual way of getting the visitor ID.
        var visitorId = '1';

        // Send a PUT request to update the views
        $http.put(`/rest/visitor/${visitorId}`, { views: 1 })
            .then(function (response) {
                console.log("Views updated successfully");
            })
            .catch(function (error) {
                console.error("Error updating views", error);
            });
    };

    // Call the updateViews function when the page loads
    $scope.updateViews();
	
  // Quản lý sản phẩm được yêu thích
	var $like = $scope.like = {
	//	items: [],
		change(id){
			$http.get(`/rest/favourite/${id}`).then(resp => {
				$scope.favourite = resp.data;
				if($scope.favourite.isLiked){
					// Đang ở trạng thái like. User unlike => cập nhật isLike = 0
					$http.put(`/rest/favourite/${id}`).then(resp =>{
						console.log("Success", resp);
						location.href = "/product/detail/" + id  ;
					}).catch(error => {
						console.log("Error", error);
					})
				}else{
					// Đang ở trạng thái unlike. User like => cập nhật isLike = 1
					$http.put(`/rest/favourite/${id}`).then(resp =>{
						console.log("Success", resp);
						location.href = "/product/detail/" + id  ;
					}).catch(error => {
						console.log("Error", error);
					})
				}
				
			})
		},
	};
  
  // Trong AngularJS controller
	$scope.preventEmptyInput = function(event) {
	    if (event.keyCode === 8 || event.keyCode === 46) { // Kiểm tra phím backspace và phím delete
	        event.preventDefault();
	    }
	};
	
	
	
	
  
  	// Thêm sự kiện tăng giảm số lượng
	$scope.increaseQuantity = function(item) {
		// Kiểm tra số lượng trong kho
		if (item.qty < item.productQuantity) {
			// Nếu số lượng của item chưa vượt quá số lượng trong kho
			// thì tăng số lượng của item lên 1
			item.qty += 1;
		}else{
			alert("Số lượng sản phẩm vượt quá số lượng trong kho!");
		}
		$scope.cart.saveToLocalStorage();
	};

	$scope.decreaseQuantity = function(item) {
		if (item.qty > 1) {
			item.qty -= 1;
			$scope.cart.saveToLocalStorage();
		}
	};
  	
  // Trong controller hoặc script tương ứng
	$scope.getSubtotal = function() {
    	    var subtotal = 0;
    	    for (var i = 0; i < $scope.cart.items.length; i++) {
    	        var item = $scope.cart.items[i];
    	        subtotal += item.price * item.qty;
    	    }
    	    return subtotal;
    };
    $scope.getTotal = function() {
    	var discount = document.getElementById('discountPrice').innerText;
    	    var subtotal = 0;
    	    var shippingFee = 15000;
    	    for (var i = 0; i < $scope.cart.items.length; i++) {
    	        var item = $scope.cart.items[i];
    	        subtotal += item.price * item.qty;
    	    }
    	    return (subtotal + shippingFee) - discount;
    	};
  $scope.cart = {
    items: [],
    add(id) {
      var item = this.items.find((item) => item.id == id);
      if (item) {
		if(item.qty < item.productQuantity){
			// Kiểm tra số lượng sản phẩm trong giỏ hàng với số lượng sản phẩm trong kho
			item.qty++;
        	this.saveToLocalStorage();
		}else{
			alert("Số lượng sản phẩm vượt quá số lượng trong kho!");
		}
      } else {
        $http.get(`/rest/products/${id}`).then((resp) => {
          resp.data.qty = 1;
          this.items.push(resp.data);
          this.saveToLocalStorage();
        });
      }
      alert("Thêm vào giỏ thành công!");
    },
    add2(id) {
	  var quantityInput = document.getElementById("quantityInput");
      var quantityValue = parseInt(quantityInput.value);
      var item = this.items.find((item) => item.id == id);
      if (item) {
		if(item.qty < item.productQuantity){
			item.qty += quantityValue;
        	this.saveToLocalStorage();
		}else{
			alert("Số lượng sản phẩm vượt quá số lượng trong kho!");
		}
      } else {
        $http.get(`/rest/products/${id}`).then((resp) => {
          resp.data.qty = quantityValue;
          this.items.push(resp.data);
          this.saveToLocalStorage();
        });
      }
      alert("Thêm vào giỏ thành công!");
    },
    remove(id) {
	  var xacNhan = confirm("Bạn có muốn xóa không?");
	  if(xacNhan) {
		  var index = this.items.findIndex((item) => item.id == id);
	      this.items.splice(index, 1);
	      this.saveToLocalStorage();
	  } else {
		  // Người dùng chọn "Cancel", không thực hiện xóa
		  console.log("Xóa bị hủy");
	  }
      
    },
    clear() {
      this.items = [];
      this.saveToLocalStorage();
    },
    amt_of() {},
    get count() {
      return this.items
        .map((item) => item.qty)
        .reduce((total, qty) => (total += qty), 0);
    },
    get amount() {
      return this.items
        .map((item) => item.qty * item.price)
        .reduce((total, qty) => (total += qty), 0);
    },
    saveToLocalStorage() {
      var json = JSON.stringify(angular.copy(this.items));
      localStorage.setItem("cart", json);
    },
    loadFormLocalStrorage() {
      var json = localStorage.getItem("cart");
      this.items = json ? JSON.parse(json) : [];
    },
  };
  $scope.cart.loadFormLocalStrorage();
  $scope.order = {
    createDate: new Date(),
    address: "",
    account: { username: $("#username").text() },
    discount: null,
    get orderDetails() {
      return $scope.cart.items.map((item) => {
        return {
          product: { id: item.id },
          price: item.price,
          quantity: item.qty,
        };
      });
    },
    purchase() {
      var discountId = document.getElementById("discountId").value;
      if (discountId) {
        $scope.order.discount = { id: discountId };
      }

      var order = angular.copy(this);

      console.log(order.discount)
      // thực hiện đặt hàng, orders này là giá trị truyền vào JsonNode orderData bên Controller

      var isPaypal = document.getElementById("paypal");
      if (isPaypal.checked == true) {
      console.log(isPaypal.checked);
        alert("Đặt hàng thành công!");
        $http.post("/rest/checkout", order).then((resp) => {
           $scope.cart.clear();
           location.href = resp.data.url;
        }).catch(error=>{
          alert("Đặt hàng lỗi!");
        });
      } else {
        $http.post("/rest/orders", order).then((resp) => {
          alert("Đặt hàng thành công!");
          $scope.cart.clear();
          location.href = "/order/detail/" + resp.data.id;
        }).catch(error=>{
          alert("Đặt hàng lỗi!!");
        });
      }
    }
  };
});
