# Live login broadcast

Ứng dụng demo dùng Spring Boot cho HTTP và `netty-socketio` cho Socket.IO. Tài khoản và token chỉ nằm trong bộ nhớ; không sử dụng cơ sở dữ liệu.

## Chạy

Yêu cầu Java 17+ và Maven:

```sh
mvn spring-boot:run
```

Mở `http://localhost:8080`. Đăng nhập bằng một trong các tài khoản. Nếu đã đổi cổng Socket.IO, mở trang với query `?socketPort=<cổng>` tương ứng:

| Tên đăng nhập | Mật khẩu | Quyền |
| --- | --- | --- |
| `admin` | `admin123` | ADMIN |
| `binh` | `binh123` | USER |

HTTP chạy trên cổng `8080`, Socket.IO trên cổng `9092`. Có thể đổi cổng Socket.IO bằng thuộc tính `app.socket.port` và cổng HTTP bằng `server.port`. Đăng nhập admin thành công sẽ phát event `log` (`message`, `time`) tới các client Socket.IO đang kết nối hợp lệ. Token mới được tạo trong RAM cho mỗi lần đăng nhập và mất khi ứng dụng khởi động lại.

## API

`POST /login` nhận JSON:

```json
{"username":"binh","password":"binh123"}
```

Đăng nhập thành công trả về token cùng tên đăng nhập và quyền. Kết nối Socket.IO phải gửi token trong query string; token không hợp lệ sẽ bị từ chối ở bước bắt tay.
