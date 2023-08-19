var app = angular.module("myapp", ["ngRoute"]);

app.config(function($routeProvider){
	$routeProvider
	.when("/category", {
		templateUrl: "/assets/admin/layout/category.html"
	})
	.when("/product", {
		templateUrl: "/assets/admin/layout/product.html"
	})
	.when("/authority", {
		templateUrl: "/assets/admin/layout/authority.html"
	})
	.when("/favourites", {
		templateUrl: "/assets/admin/layout/favourites.html"
	})
	.when("/inventory-management", {
		templateUrl: "/assets/admin/layout/inventory-management.html"
	})
	.when("/order_Confirm", {
		templateUrl: "/assets/admin/layout/order_Confirm.html"
	})
	.when("/order_Shipping", {
		templateUrl: "/assets/admin/layout/order_Shipping.html"
	})
	.when("/order_Success", {
		templateUrl: "/assets/admin/layout/order_Success.html"
	})
	.when("/order_Cancel", {
		templateUrl: "/assets/admin/layout/order_Cancel.html"
	})
	.when("/dashboard", {
		templateUrl: "/assets/admin/layout/dashboard.html"
	})
	.when("/discount", {
		templateUrl: "/assets/admin/layout/discount.html"
	})
	.when("/account", {
		templateUrl: "/assets/admin/layout/account.html"
	})
	.otherwise({
		redirectTo: "/dashboard"
	});
});

app.run(function ($rootScope) {
    $rootScope.$on('$routeChangeStart', function () {
        $rootScope.loading = true;
    });
    $rootScope.$on('$routeChangeSuccess', function () {
        $rootScope.loading = false;
    });
    $rootScope.$on('$routeChangeError', function () {
        $rootScope.loading = false;
        alert("Lỗi");
    });
});