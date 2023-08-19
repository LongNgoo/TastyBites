let pathDashboard = "http://localhost:8080/rest";
app.controller("ctrl-dashboard", function($scope, $http, $filter){
	$scope.items = [];
	$scope.form = {};
    $scope.selectedCreatedDate = '';
	
	// Add a variable to track the number of visible rows
    $scope.visibleRows = 4;

    // Function to show more rows when the "Xem thêm" button is clicked
    $scope.showMoreRows = function() {
        $scope.visibleRows = $scope.items.length;
    };
	
	// Hàm xử lý sự kiện khi giá trị của trường input thay đổi
    $scope.handleChange = function() {
        // Định dạng giá trị của $scope.selectedCreatedDate thành yyyy-MM-dd
        $scope.formattedDate = $filter('date')($scope.selectedCreatedDate, 'yyyy-MM-dd');
        $http.get(`/rest/order-by-day/` + $scope.formattedDate).then(resp =>{
			$scope.items = resp.data;
		});	
    };
    
    $scope.load_orderAll = function() {
		var url = `${pathDashboard}/orderAll`;
		$http.get(url).then(resp => {
			$scope.items = resp.data;
			console.log("Success", resp)
		}).catch(errors => {
			console.log("Error", errors)
		});
	};
	
	// Hàm để thay đổi giá trị trong thẻ h3
        function updateTotalOrder(totalOrder) {
            const h3Element = document.getElementById('totalOrder');
            h3Element.textContent = totalOrder;
        }

        // Gọi API để lấy tổng số lượng đơn hàng và cập nhật giá trị trong thẻ h3
        fetch('/totalOrder') // Đảm bảo rằng đường dẫn '/totalOrder' phù hợp với API của bạn
            .then(response => response.text())
            .then(data => updateTotalOrder(data))
            .catch(error => console.error('Error fetching data:', error));
	
	$scope.load_orderAll();
	
	
	// Hàm để thay đổi giá trị trong thẻ h3 (totalprice)
        function updateTotalPriceOrder(totalPriceOrder) {
            const h3Element = document.getElementById('totalPriceOrder');
            
            h3Element.textContent = totalPriceOrder;
        }

        // Gọi API để lấy tổng số lượng đơn hàng và cập nhật giá trị trong thẻ h3
        fetch('/totalPriceOrder') // Đảm bảo rằng đường dẫn '/totalPriceOrder' phù hợp với API của bạn
            .then(response => response.text())
            .then(data => updateTotalPriceOrder(data))
            .catch(error => console.error('Error fetching data:', error));
	
	// Hàm để thay đổi giá trị trong thẻ h3 (visitor)
	function updateViewVistor(viewVistor) {
	    const h3Element = document.getElementById('viewVistor');
	    const intValue = parseInt(viewVistor);
	    h3Element.textContent = intValue.toString();
	}

      // Gọi API để lấy tổng số lượng đơn hàng và cập nhật giá trị trong thẻ h3
	fetch('/viewVistor') // Đảm bảo rằng đường dẫn '/viewVistor' phù hợp với API của bạn
	    .then(response => response.text())
	    .then(data => updateViewVistor(data))
	    .catch(error => console.error('Error fetching data:', error));

	// Hàm để thay đổi giá trị trong thẻ h3 (totalprice)
	function updateTotalPriceOrder(totalPriceOrder) {
	    const h3Element = document.getElementById('totalPriceOrder');
	
	    // Kiểm tra nếu totalPriceOrder là null hoặc không có giá trị
	    if (totalPriceOrder === null || totalPriceOrder === '') {
	        h3Element.textContent = '0';
	    } else {
	        // Chuyển đổi totalPriceOrder thành số
	        const totalPrice = parseFloat(totalPriceOrder);
	
	        // Kiểm tra nếu totalPrice là một số hợp lệ
	        if (!isNaN(totalPrice)) {
	            // Định dạng số và chia thành phần nghìn bằng dấu '.'
	            const formattedPrice = totalPrice.toLocaleString('vi-VN');
	
	            // Cập nhật giá trị trong thẻ h3
	            h3Element.textContent = formattedPrice + " đ";
	        } else {
	            // Nếu totalPrice không hợp lệ, hiển thị số 0
	            h3Element.textContent = '0 đ';
	        }
	    }
	}

	// Gọi API để lấy tổng số lượng đơn hàng và cập nhật giá trị trong thẻ h3
	fetch('/totalPriceOrder') // Đảm bảo rằng đường dẫn '/totalPriceOrder' phù hợp với API của bạn
	    .then(response => response.text())
	    .then(data => updateTotalPriceOrder(data))
	    .catch(error => console.error('Error fetching data:', error));
	
		
	
	 // Function để tăng số lượt truy cập và hiển thị nó trong thẻ có id là "visitorCount"
    
	
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
	
	
	
	// APEXCHART
	var options = {
	  series: [{
	  name: 'series1',
	  data: [31, 40, 28, 51, 42, 109, 100]
	}, {
	  name: 'series2',
	  data: [11, 32, 45, 32, 34, 52, 41]
	}],
	  chart: {
	  height: 350,
	  type: 'area'
	},
	dataLabels: {
	  enabled: false
	},
	stroke: {
	  curve: 'smooth'
	},
	xaxis: {
	  type: 'datetime',
	  categories: ["2018-09-19T00:00:00.000Z", "2018-09-19T01:30:00.000Z", "2018-09-19T02:30:00.000Z", "2018-09-19T03:30:00.000Z", "2018-09-19T04:30:00.000Z", "2018-09-19T05:30:00.000Z", "2018-09-19T06:30:00.000Z"]
	},
	tooltip: {
	  x: {
	    format: 'dd/MM/yy HH:mm'
	  },
	},
	};
	
	var chart = new ApexCharts(document.querySelector("#chart"), options);
	chart.render();
	
	
	
	// MENU
	const allMenu = document.querySelectorAll('main .content-data .head .menu');
	
	allMenu.forEach(item=> {
		const icon = item.querySelector('.icon');
		const menuLink = item.querySelector('.menu-link');
	
		icon.addEventListener('click', function () {
			menuLink.classList.toggle('show');
		})
	})

	window.addEventListener('click', function (e) {
		if(e.target !== imgProfile) {
			if(e.target !== dropdownProfile) {
				if(dropdownProfile.classList.contains('show')) {
					dropdownProfile.classList.remove('show');
				}
			}
		}
	
		allMenu.forEach(item=> {
			const icon = item.querySelector('.icon');
			const menuLink = item.querySelector('.menu-link');
	
			if(e.target !== icon) {
				if(e.target !== menuLink) {
					if (menuLink.classList.contains('show')) {
						menuLink.classList.remove('show')
					}
				}
			}
		})
	})
	
});