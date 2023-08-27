const registrationButton = document.getElementById("register");
const loginButton = document.getElementById("login");
const container = document.getElementById("container");

registrationButton.addEventListener("click",() => {
    container.classList.add("right-panel-active");
});

loginButton.addEventListener("click",() => {
    container.classList.remove("right-panel-active");
});


// Bắt sự kiện khi biểu mẫu đăng nhập được gửi
document.querySelector(".login-container form").addEventListener("submit", function(event) {
    event.preventDefault(); // Ngăn chặn gửi biểu mẫu mặc định

    // Lấy giá trị từ các trường nhập
    var email = this.querySelector("input[name='username']").value;
    var password = this.querySelector("input[name='password']").value;

    // Kiểm tra và xử lý lỗi
    var errorMessage = document.getElementById("error-message");
    errorMessage.innerHTML = ""; // Xóa thông báo lỗi hiện tại

    if (email.trim() === "") {
        errorMessage.innerHTML = "Vui lòng nhập tên đăng nhập.";
        return;
    }

    if (password.trim() === "") {
        errorMessage.innerHTML = "Vui lòng nhập mật khẩu.";
        return;
    }

    // Gửi biểu mẫu nếu không có lỗi
    this.submit();
});



// Bắt sự kiện khi người dùng nhấp vào nút "Login" trên overlay
document.getElementById("login").addEventListener("click", function() {
    document.getElementById("container").classList.remove("right-panel-active");
});