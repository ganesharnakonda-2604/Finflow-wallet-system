# FinFlow – Wallet & Transaction Processing System

## 📌 Overview
FinFlow is a FinTech-grade backend system built using Java and Spring Boot that supports secure wallet management, atomic money transfers, and ledger-based transaction history.The system is designed with **ACID guarantees**, **concurrency safety**, and **real-world payment system principles** inspired by Paytm / Amazon Pay–style architectures.

---

## 🎯 Purpose of the Project
The purpose of this project is to:
- Simulate a real-world digital wallet system
- Ensure atomic debit-credit transactions
- Prevent double spending under concurrent access
- Maintain a reliable transaction ledger
- Demonstrate backend engineering best practices
- Be **interview-ready and resume-worthy**

---

## 🛠️ Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- JUnit 5 & Mockito
- Spring Security (basic configuration)

---

## 🧩 Core Features
- User & wallet creation
- Add money to wallet
- Transfer money between users
- Atomic debit-credit transactions
- Ledger-based transaction history
- Pagination & sorting
- Concurrency-safe operations
- Global exception handling
- Unit & concurrency tests

---

## 🏗️ System Architecture
Client → Controller → Service → Repository → Database

- **Controller**: REST APIs
- **Service**: Business logic & transactions
- **Repository**: JPA/Hibernate data access
- **Database**: PostgreSQL with ACID compliance

<img width="1024" height="1536" alt="architecture" src="https://github.com/user-attachments/assets/0ae34894-5a0d-45a4-be74-daf0ca5ed438" />
.png)

---

## 📦 Database Tables
- users
- wallets
- transactions

---

## 🔐 Transaction Safety
- `@Transactional` for atomicity
- Pessimistic locking to prevent double spending
- Optimistic locking using versioning
- BigDecimal for monetary calculations
- Ledger-based transaction records

## 🚀 API Endpoints
http
👤 Create User
POST /api/users

Request Body

{
  "name": "Ganesh",
  "email": "ganesh@example.com"
}

💰 Add Money

POST /api/wallets/add-money

Request Body

{
  "userId": 1,
  "amount": 500.00
}

🔁 Transfer Money

POST /api/wallets/transfer

Request Body
{
  "senderUserId": 1,
  "receiverUserId": 2,
  "amount": 200.00
}

📜 Transaction History

GET /api/transactions/{userId}?page=0&size=10



---

## 📸 API Screenshots

The following screenshots demonstrate successful API execution, error handling, and pagination using **Postman**.

---

### 👤 Create User API
<img width="955" height="1012" alt="create-user-api" src="https://github.com/user-attachments/assets/0f51b214-bed2-4537-b1c1-1be9c965eed4" />


### 💰 Add Money API
<img width="953" height="1011" alt="add-money-api" src="https://github.com/user-attachments/assets/d87db601-6e91-4e10-9d0a-baeb39e0d02b" />

---

### 🔁 Transfer Money API (Success)
<img width="952" height="1019" alt="transfer" src="https://github.com/user-attachments/assets/0f1fa066-23ba-4a97-affc-57f1a7f0df52" />


---

### 📜 Transaction History API
<img width="945" height="984" alt="Screenshot 2026-01-18 044818" src="https://github.com/user-attachments/assets/353d8ffa-165f-4521-98ab-067d31baebb8" />


---

### ❌ Transfer Failure – Insufficient Balance
<img width="951" height="1023" alt="insuffiecint" src="https://github.com/user-attachments/assets/a6430311-e9c7-4e12-87cd-aa54eaba8fde" />


---

## 🧪 Test Results (JUnit)

All unit and concurrency tests executed successfully.

<img width="1918" height="1013" alt="test-results" src="https://github.com/user-attachments/assets/877cbe73-f837-4f05-8fd7-cfcba12c255d" />

---

## 🧪 Testing
- Service-layer unit tests using JUnit & Mockito
- Rollback tests for failure scenarios
- Concurrency test to prevent wallet overdraw

---

## 📈 Future Enhancements
- JWT-based authentication
- Redis idempotency keys
- Kafka event publishing
- Microservices architecture
- UPI / Bank gateway integration

---

