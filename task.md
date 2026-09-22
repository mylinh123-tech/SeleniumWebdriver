# UI Flow Automation Progress
- [x] Buoc 1: Chuan bi - doc source Bai22_ThucHanhPOM
- [x] Buoc 2: Doi chieu locator co san trong CustomersPage
- [x] Buoc 3: Sua Page Object + CustomersTest
- [x] Buoc 4: Chay test + auto-heal lan 1 - sua wait login redirect

## Ket qua tam thoi
- CustomersPage dung `WebUI` thay cho `ActionKeyword` khong ton tai.
- CustomersTest con 2 testcase: Add New Customer, Delete Customer.
- Delete Customer thao tac tren UI bang link delete va confirm alert.
- Test status: 2 lan lien tiep PASS, moi lan 2/2 test PASS.

## Add New Project
- [x] Doc source ProjectsPage hien tai
- [x] Them action Add New Project theo POM
- [x] Tao ProjectsTest doc customer name tu JSON
- [x] Chay test ProjectsTest lan 1 - fail vi customer dropdown dung live-search
- [x] Sua select customer bang thao tac dropdown search that
- [x] Chay test ProjectsTest lan 2 - fail o assert input sau khi save
- [x] Sua assert sau save theo trang profile Project
- [x] Chay test ProjectsTest lan 3 - AJAX endpoint khong tra customer moi
- [x] Lay customerId tu Customers page bang customerName trong JSON
- [x] Chon customer tren Add Project bang clientid + customerName
- [x] Chay test ProjectsTest lan 4 - PASS
- [x] Chay test ProjectsTest lan 5 - PASS on dinh
