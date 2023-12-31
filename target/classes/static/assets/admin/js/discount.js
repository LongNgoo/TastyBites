// discount.js

let pathDiscount = "http://localhost:8080/rest";

app.controller("ctrl-discount", function($scope, $http) {
  $scope.form = {};
  $scope.discounts = [];
  
  
  $scope.formatDate = function(dateString) {
		if (!dateString) return null;
		var date = new Date(dateString);
		var formattedDate = moment(date).format('YYYY-MM-DD');
		return formattedDate;
	};


  $scope.load_all = function() {
    var url = `${pathDiscount}/discount`;
    $http.get(url).then(resp => {
      $scope.discounts = resp.data;
      console.log("Success", resp);
    }).catch(errors => {
      console.log("Error", errors);
    });
  }

  $scope.editDiscount = function(id) {
    var url = `${pathDiscount}/discount/${id}`;
    $http.get(url).then(resp => {
      $scope.form = resp.data;
      $scope.form.applyDay = new Date(resp.data.applyDay);
      $scope.form.expiration = new Date(resp.data.expiration);
      $scope.form.createdate = new Date(resp.data.createdate);
      console.log("Success", resp);
    }).catch(errors => {
      console.log("Error", errors);
    });
  }

  $scope.updateDiscount = function() {
    var discount = angular.copy($scope.form);
    var url = `${pathDiscount}/discount/${$scope.form.id}`;
    $http.put(url, discount).then(resp => {
      var index = $scope.discounts.findIndex(item => item.id == $scope.form.id);
      $scope.discounts[index] = resp.data;
      $scope.reset();
      $scope.load_all();
      console.log("Success", resp);
      alert("Cập nhật mã giảm thành công");
    }).catch(error => {
      console.log("Error", error);
    });
  }

  $scope.createDiscount = function() {
    var discount = angular.copy($scope.form);
    let check = false

    // Set the createDate field to the current date
    discount.createdate = new Date();
    
    console.log(discount);
    if ($scope.myForm.$valid) {
      // Validate your form fields here if needed
      // ...

      if (check) {
        console.log('Vẫn còn Lỗi');
        return;
      } else {
        var url = `${pathDiscount}/discount`;
        console.log('Bắt đầu gửi dữ liệu');
        $http.post(url, discount).then(resp => {
          $scope.discounts.push(discount);
          $scope.reset();
          $scope.load_all();
          console.log("Success", resp);
          alert("Tạo mã giảm thành công");
        }).catch(error => {
          console.log("Error", error);
        });
      }
    }
  }

  $scope.deleteDiscount = function(discountId) {
	var url = `${pathDiscount}/discount/${discountId}`;
	var xacnhan = confirm('Bạn có chắc muốn xóa!');
	if(xacnhan) {
		$http.delete(url).then(resp => {
	      // tìm ra phần tử tại vị trí sẽ xóa.
	      var index = $scope.discounts.findIndex(item => item.id == discountId);
	      $scope.discounts.splice(index, 1); // tại vị trí đó và xóa 1 phần tử
	      $scope.reset();
	      console.log("Success", resp);
	      alert("Xóa mã giảm thành công");
	    }).catch(error => {
	      console.log("Error", error);
	    });
	}else{
		console.log("Cancel");
	}
    
    
  }

  $scope.reset = function() {
    $scope.form = {
      // Initialize your form fields here
      name: "",
      code: "",
      price: "",
      quality: "",
      applyDay: "",
      expiration: "",
      createdate: ""
    };
  }

  $scope.load_all();
  $scope.reset();
});
