app.controller("ctrl-favourite", function($scope, $http){

	$scope.favourites = []; // container value of favourites
	$scope.accounts = [];
	
	$scope.products = []; // container value of productId
	$http.get("/rest/product").then(resp => {
		$scope.products = resp.data;
	});
	
	// Hiển thị những người đã thích theo mã sản phẩm
	$scope.showUserLikedProduct = function(){
		$http.get(`/rest/user-info-by-productId/` + $scope.selectedProductId).then(resp =>{
			$scope.accounts = resp.data;
		});	
	}
	
	// load categoryId into select box
	$scope.load_all = function(){
		$http.get("/rest/total-like-of-products").then(resp => {
			$scope.favourites = resp.data;
			console.log("Success", resp)
		}).catch(errors => {
			console.log("Error", errors)
		});
	}
	$scope.load_all();
});