-- File này dùng để insert data master cho ứng dụng khi chạy chương trình.Mỗi khi chạy lại, ứng dụng sẽ kiểm tra trong DATABASE có bản ghi như ở file này chưa,nếu có rồi thì skip, chưa có thì nó sẽ insert lại.
-- Lưu ý file này chỉ có thể chứa các câu lệnh DML chứ k chứa DDL.
INSERT INTO `role` (`role_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `description`, `role_code`, `role_level`, `role_name`, `status`, `is_delete`) VALUES ('1', 'System Generated', '2021-03-01 13:49:19', 'admin', '2021-10-26 16:58:15', 'àasf', 'ADMIN', '1', 'ADMIN', '1', '0');
INSERT INTO `role` (`role_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `description`, `role_code`, `role_name`, `status`, `is_delete`) VALUES ('15', 'admin', '2021-10-21 09:23:19', 'admin', '2021-10-22 14:56:40', 'USER', 'USER', 'User', '1', '0');

