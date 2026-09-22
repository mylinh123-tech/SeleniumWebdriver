---
description: Sinh manual test cases nhanh từ requirements (QUICK mode — không qua quy trình 6 bước).
skills:
  - rbt_manual_testing
---

> **BẮT BUỘC (MANDATORY SKILL):** Bạn PHẢI nạp và đọc kỹ nội dung của skill **`rbt_manual_testing`** (tại `.claude/skills/rbt_manual_testing/SKILL.md`) trước khi bắt đầu thực hiện tác vụ này. Sử dụng **Mode QUICK** của skill.

# Command: Sinh Manual Test Cases Nhanh từ Requirements

Command này sử dụng **Mode QUICK** của skill `rbt_manual_testing` để sinh test cases nhanh từ requirements đã sẵn có.

## ⚠️ Nguyên tắc

- **Mode:** QUICK (1 lượt duy nhất, không chờ user giữa chừng)
- Phù hợp cho module đơn giản, requirements đã rõ ràng
- Nếu phát hiện requirements quá phức tạp hoặc mơ hồ → **tự động chuyển sang FULL RBT** và thông báo user
- Tất cả output bằng **Tiếng Việt**

## Các bước thực hiện

1. **Đọc và hiểu requirements** được user cung cấp
2. **Xác định các luồng chính:** Happy Path, Negative Path, Boundary Cases, Edge Cases
3. **Áp dụng kỹ thuật thiết kế test case tự động:**
   - Equivalence Partitioning (EP)
   - Boundary Value Analysis (BVA)
   - Decision Table (nếu có nhiều rules)
   - State Transition (nếu có workflow)
4. **Validation chuyên biệt từng trường (Field-Level Validation - 15 Field Types):**
   - Liệt kê tất cả input fields trên form/UI
   - Sinh validation TCs **riêng cho TỪNG trường** theo đặc tính riêng (Text, Email, Phone, Date, Number, Dropdown, Checkbox/Radio, File Upload, Password, Textarea, OTP/MFA, Date Range, Rich Text, Multi-Select, Range Slider)
   - Áp dụng **Bảng Field-Level Validation Checklist** trong skill `rbt_manual_testing`
   - **KHÔNG** gộp validation nhiều trường vào 1 test case
5. **Bao phủ Scenarios Chuyên Sâu & Non-Functional:**
   - Double Submit / Race Condition (click liên tiếp nút submit, concurrent edit).
   - Session & Network Resilience (session timeout mid-form, loss of network, slow 3G).
   - Localization & UTF-8 / Emoji (tiếng Việt có dấu, emoji, ký tự đa ngôn ngữ).
   - Keyboard Accessibility (Tab order, Enter/Space trigger, Focus state).
   - HTTP Status Codes (cho API TCs).
6. **Sinh test cases đầy đủ fields & Automation Metadata:**
   - `TC ID` (format: `[DỰ_ÁN]_[MODULE]_TC_[SỐ]`)
   - `Module`
   - `Risk Level` (High / Medium / Low)
   - `Test Scenario` / `Test Case Title`
   - `Pre-Condition`
   - `Test Steps` (đánh số)
   - `Expected Result` (đánh số tương ứng)
   - `Test Data` (**phải cụ thể**, không placeholder)
   - `Priority` (Critical / High / Medium / Low)
   - `Automatable` (Yes / No / Partial)
   - `Auto Type` (UI / API / Unit / N/A)
   - `Tags` (`@Smoke`, `@Regression`, `@CriticalPath`...)
7. **Chạy Self-Quality Gate (5 Tiêu chí):** Unique TC ID, 1-to-1 Step-Expected matching, Concrete Test Data, Field Validation Coverage, Automation Metadata Ready.
8. **Xuất ra bảng Markdown chuẩn**

## Bảng Output

```markdown
| TC ID | Module | Risk Level | Test Scenario | Pre-Condition | Test Steps | Test Data | Expected Result | Priority | Automatable | Auto Type | Tags |
```

## Quy tắc quan trọng

- Test Data phải cụ thể: `test_login_01@domain.com`, không phải "email hợp lệ"
- Phải bao gồm cả Positive, Negative, Boundary, và Edge cases
- Mỗi trường input phải có validation TCs riêng (không gộp nhiều trường vào 1 TC)
- TC ID theo format thống nhất do user quy ước hoặc mặc định `[DỰ_ÁN]_[MODULE]_TC_[SỐ]`
- Nếu quá nhiều TCs → chia thành Part 1, Part 2 và hỏi user

## Khi nào chuyển sang FULL RBT

Agent **tự động đề xuất chuyển mode** nếu phát hiện:
- Requirements mơ hồ, cần hỏi Q&A
- Scope lớn (>3 modules)
- Logic nghiệp vụ phức tạp, nhiều điều kiện chồng chéo
- User yêu cầu Traceability Matrix hoặc Risk Assessment