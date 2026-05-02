#  Inkly — Scalable Digital Library Platform

A **production-grade, event-driven digital library platform** built using **Spring Boot microservices, Kafka, OpenSearch, and Docker**.

Inkly enables users to become authors, create and publish content, and allows readers to efficiently discover books through a **highly scalable search system** (elastic search).

---

#  System Architecture

## 🔹 High-Level Architecture

```
Client (Web - Angular)
        │
        ▼
   API Gateway
        │
        ▼
---------------------------------------------------------
|                Microservices Layer                    |
---------------------------------------------------------
| user-service        author-service                    |
| authoring-service   catalog-service                   |
| order-service       payment-service                   |
| notification-service search-service                   |
---------------------------------------------------------
        │
        ▼
---------------------------------------------------------
| Infrastructure                                        |
---------------------------------------------------------
| Kafka (Event Bus - AVRO events)                       |
| PostgreSQL (Database per service)                     |
| OpenSearch (Search Engine)                            |
| Keycloak (Authentication & Authorization)             |
| Stripe (Payment Gateway)                              |
| Flyway (DB Migrations)                                |
---------------------------------------------------------
```

---

# 🔄 Core Workflows

## 👤 1. User → Author Upgrade

```
User → API Gateway → User Service
        │
        ▼
Author Service (upgrade role)
```

* Only users can become authors
* Role-based access using Keycloak (`ROLE_USER → ROLE_AUTHOR`)

---

##  2. Draft Creation (Authoring System)

```
Author → Authoring Service
        │
        ├── Create Book Draft
        └── Create Chapter Draft
```

* Drafts are:

  * Private
  * Only accessible by the author
* Stored in:
   `authoring-service`

---

##  3. Publishing Flow (Critical Flow 🚀)

```
Author → Publish Draft
        │
        ▼
Authoring Service
        │
        ▼
OpenFeign Call
        │
        ▼
Catalog Service (create API)
```

* Draft → Published content
* Catalog Service becomes:
   **Source of truth for published data**

---

##  4. Event-Driven Flow (Kafka + AVRO)

```
Catalog Service
   │
   ▼
Kafka → BookEvents (AVRO schema)
   │
   ▼
Search Service (consumer)
```

* Events contain:

  * book_id
  * chapter_id
  * author
  * genre
  * metadata

👉 AVRO ensures:

* Schema evolution
* Strongly typed communication
* Backward compatibility

---

## 🔍 5. Search Flow (OpenSearch)

```
Kafka → Search Service → OpenSearch Index
```

Users can search by:

* Author name
* Genre
* Title
* Keywords

---

##  6. Content Access Flow

```
User → Search Query
        │
        ▼
OpenSearch → Returns book_id / chapter_id
        │
        ▼
Catalog Service (GET API)
        │
        ▼
Fetch Content
```

---

##  7. Purchase Flow (Saga Pattern)

```
User → Buy Book
        │
        ▼
Order Service (Saga Orchestrator)
        │
        ▼
Payment Service (Stripe)
        │
        ▼
Kafka → Payment Success Event
        │
        ▼
Catalog Service (grant access)
        │
        ▼
Notification Service
```

---

#  Design Principles & Patterns

* **Microservices Architecture**
* **Event-Driven Communication (Kafka)**
* **Saga Pattern (Distributed Transactions)**
* **Database per Service**
* **API Gateway Pattern**
* **Idempotent Payment Handling**
* **Role-Based Access Control (RBAC)**

---

#  AVRO Event Design (book-events)

* Centralized schema module:

```
book-events/
```

* Used for:

  * Kafka message contracts
  * Schema evolution
  * Type-safe communication

---

# Tech Stack

| Layer            | Technology            |
| ---------------- | --------------------- |
| Backend          | Spring Boot (Java)    |
| Auth             | Keycloak (OAuth2/JWT) |
| Messaging        | Kafka + AVRO          |
| Database         | PostgreSQL            |
| Search           | OpenSearch            |
| Payments         | Stripe                |
| Build            | Maven (Multi-module)  |
| Containerization | Docker                |
| Frontend         | Angular               |

---

#  Docker Setup

##  Prerequisites

* Docker
* Docker Compose

---

## ▶️ Run the System

```
docker-compose up --build
```

---

## 🛑 Stop the System

```
docker-compose down
```

---

##  Run in Detached Mode

```
docker-compose up --build -d
```

---

## 🌐 Services (Example Ports)

| Service     | Port |
| ----------- | ---- |
| API Gateway | 8080 |
| Keycloak    | 8081 |
| Kafka       | 9092 |
| OpenSearch  | 9200 |
| PostgreSQL  | 5432 |

---

#  Project Structure

```
backend/
├── api-gateway
├── user-service
├── author-service
├── authoring-service
├── catalog-service
├── order-service
├── payment-service
├── notification-service
├── search-service
├── book-events
├── docker
└── keycloak-themes

frontend/
└── angular
```

---

#  Supporting Infrastructure

* 🧾 Flyway (DB migrations)
* 🔄 Kafka (event streaming)
* 🧩 Saga orchestration (Order Service)
* 💳 Idempotency (Payment Service)
* 📦 Separate DB per service

---

#  Future Improvements

* Redis (Caching + Rate Limiting)
* Kafka DLQ & Retry
* Observability (Prometheus + Grafana + ELK)
* Circuit Breakers (Resilience4j)
* Kubernetes deployment

---

#  Why Inkly?

Inkly is not just a CRUD project — it demonstrates:

* Real-world **distributed system design**
* Event-driven architecture at scale
* Content lifecycle management (Draft → Publish → Search)
* Strong consistency using Saga
* Scalable search using OpenSearch

---

# 👨‍💻 Author

**Himanshu Kushwaha**
Backend Developer | Java | Microservices | System Design

---

# ⭐ Support

If you like this project, give it a ⭐ on GitHub!
