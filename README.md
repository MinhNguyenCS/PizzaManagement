# 🍕 Akatsuki Pizza Management

Welcome to the **Akatsuki Pizza Management System**! This project helps pizza store owners and chefs efficiently manage toppings and pizzas. Built with a modern **Java Spring Boot** backend and a **HTML, CSS, JavaScript** frontend, it provides an intuitive interface for handling pizza creations.

🌍 Website: [akatsukipizza.com](https://minhnguyencs.github.io/PizzaManagement/frontend/login.html)
---

## 🚀 Technologies Used

### **Frontend:**

- 🎨 **HTML, CSS, JavaScript** - For building the user interface
- 🖼 **Bootstrap** - For a responsive and beautiful UI

### **Backend:**

- ☕ **Java Spring Boot** - RESTful API development
- 🐘 **MySQL** - Relational database for storing pizzas and toppings
- 🐳 **Docker** - Containerized database setup
- 🛠 **Spring Data JPA** - For easy database interactions

### **Other Tools:**

- 🏗 **Maven** - Dependency management
- 🔗 **Git** - Version control

---

## 📜 Features

### **Topping Management** (Owner)

✅ View a list of available toppings\
✅ Add new toppings\
✅ Delete existing toppings\
✅ Update existing toppings\
❌ Prevent duplicate toppings

### **Pizza Management** (Chef)

✅ View existing pizzas and their toppings\
✅ Create new pizzas and assign toppings\
✅ Delete pizzas\
✅ Update pizza toppings\
✅ Modify toppings on an existing pizza\
❌ Prevent duplicate pizza names

---

## 🛠 Setup & Installation

### **1️⃣ Clone the Repository**

```bash
  git clone https://github.com/MinhNguyenCS/PizzaManagement.git
  cd PizzaManagement
```

### **2️⃣ Install Java & Maven **
Ensure you have Java 17 and Maven installed.
Check your Java and Maven version:

```bash
java -version
mvn -version

```

### **3️⃣   Backend Setup **
####  Database Setup (Docker)
> If you prefer setting up the database manually, use the provided SQL script above.
> Ensure Docker is running for MySQL.

```bash
docker run --name pizza-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=root -p 3306:3306 -d mysql:latest
```

####  Run Spingboot
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### **4️⃣ Frontend Setup**
> Update the api calls in js files to https://localhost:9111/pizza/api/
```bash
cd frontend
open login.html
```

---

## 🧪 Running Tests

### **Backend Tests (Spring Boot JUnit)**

```bash
cd backend
mvn test
```

---

## 📌 API Endpoints
### https://akatsukipizza.uk/pizza/api
### **Toppings API**

| Method | Endpoint         | Description       |
| ------ | ---------------- | ----------------- |
| GET    | `/topping`       | Get all toppings  |
| POST   | `/topping/adding`| Add a new topping |
| POST    | `/topping/update` | Update a topping  |
| POST   | `/topping/delete/{id}`| Delete a topping  |

### **Pizzas API**

| Method | Endpoint       | Description     |
| ------ | -------------- | --------------- |
| GET    | `/chef`      | Get all pizzas  |
| POST   | `/chef/adding`      | Add a new pizza |
| POST    | `/chef/update` | Update a pizza  |
| POST | `/chef/delete/{id}` | Delete a pizza  |

---

## 🌟 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you’d like to change.

---

## 📄 License

MIT License. See `LICENSE` file for details.

---

## 💌 Contact

📧 Email: [mknguyen.dev@gmail.com](mailto\:mknguyen.dev@gmail.com)\
🐙 GitHub: [MinhNguyenCS](https://github.com/yourusername)\
🌍 Website: [pizzamanagement.com](https://minhnguyencs.github.io/PizzaManagement/frontend/login.html)


Beta
0 / 10
used queries
1
