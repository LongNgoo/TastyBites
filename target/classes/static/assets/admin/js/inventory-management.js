app.controller("ctrl-inventory-management", function($scope, $http){
	$scope.items = [];
	$scope.categories = []; // container value of categoryId
	
	// load categoryId into select box	
	$scope.load_all = function(id){
        $http.get("/rest/category").then(resp => {
			$scope.categories = resp.data;
		});
    }
    
    // Hiển thị thông tin số lượng sản phẩm
    $scope.showProductQuantity = function(){
		$http.get(`/rest/product-quantity/` + $scope.selectedCategoryId).then(resp =>{
			$scope.items = resp.data;
		});
	}

    $scope.load_all();

});