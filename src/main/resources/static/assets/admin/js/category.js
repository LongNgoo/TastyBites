let pathCategory = "http://localhost:8080/rest";

app.controller("ctrl-category", function($scope, $http) {
    $scope.form = {};
    $scope.items = {};

    $scope.load_all = function() {
        var url = `${pathCategory}/category`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            console.log("Success", resp);
        }).catch(errors => {
            console.log("Error", errors);
        });
    };

    $scope.edit = function(id) {
        var url = `${pathCategory}/category/${id}`;
        $http.get(url).then(resp => {
            $scope.form = angular.copy(resp.data);
            console.log("Success", resp);
        }).catch(errors => {
            console.log("Error", errors);
        });
    };

    $scope.update = function() {
        var category = angular.copy($scope.form);
        var url = `${pathCategory}/category/${$scope.form.id}`;
        $http.put(url, category).then(resp => {
            var index = $scope.items.findIndex(item => item.id == $scope.form.id);
            $scope.items[index] = resp.data;
            $scope.reset();
            $scope.load_all();
            console.log("Success", resp);
            alert("Cập nhật danh mục thành công");
        }).catch(error => {
            console.log("Error", error);
        });
    };

    $scope.create = function() {
        var item = angular.copy($scope.form);
        item.id = $scope.items.length + 1; // Lấy giá trị ID dựa trên độ dài của mảng items
        var url = `${pathCategory}/category`;
        $http.post(url, item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
            console.log("Success", resp);
            alert("Tạo danh mục thành công");
        }).catch(error => {
            console.log("Error", error);
        });
    };

    $scope.delete = function(id) {
        var url = `${pathCategory}/category/${id}`;
        $http.delete(url).then(resp => {
            var index = $scope.items.findIndex(item => item.id == id);
            $scope.items.splice(index, 1);
            $scope.reset();
            console.log("Success", resp);
            alert("Xóa danh mục thành công");
        }).catch(error => {
            console.log("Error", error);
        });
    };

    $scope.imageChanged = function(files) {
        var data = new FormData();
        data.append("file", files[0]);
        $http.post("/rest/upload/images", data, {
            transformRequest: angular.identity,
            headers: { "Content-Type": undefined },
        }).then(resp => {
            $scope.form.image = resp.data.name;
        }).catch(error => {
            alert("Lỗi upload hình ảnh!");
            console.log("Error", error);
        });
    };

    $scope.reset = function() {
        $scope.form = {};
    };

    $scope.load_all();
    $scope.reset();
});
