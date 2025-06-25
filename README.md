# personalised-recommendation-system

Java Spring Boot mini project to simulate product recommendations using JSON data.

## Features

-> View product recommendations by entering user ID (1–10)
-> Search for a specific product by name
-> View all available products
-> Filter products by category
-> Simple frontend interface with static HTML pages

## Tech Stack

-> Java
-> Spring Boot
-> Maven
-> REST APIs
-> CSV (for user-product interactions)
-> JSON (for product data: name, category, image)
-> HTML / CSS / JavaScript (Frontend)

## Project Structure

📁 ecommerce-recommendation  
┣ 📂 controllers → Handles HTTP requests (RecommendationController.java)  
┣ 📂 services → Business logic (RecommendationService.java)  
┣ 📂 repositories → Simulated repositories for User, Product, Interaction  
┣ 📂 models → Java classes: User, Product, UserProductInteraction  
┣ 📂 utils → DataLoader for loading CSV & JSON files  
┣ 📂 static → HTML frontend (product pages, filters, etc.)  
┣ 📄 user_product_interaction_dataset.csv → user-product interaction data  
┣ 📄 products.json → product metadata  
┣ 📄 pom.xml → Maven project configuration  

## How to Run This Project

1. Clone the repository  
2. Open in IntelliJ IDEA or VS Code  
3. Build with Maven  
4. Run the main class:  
   `EcommerceRecommendationApplication.java`  
5. Open your browser and go to:  
   `http://localhost:8082/` (my app runs on port 8082)

## Configuration Note

This project uses Spring Data JPA with a MySQL database.

To run the project locally:
-> Create a MySQL database named `Ecommerce`
-> Create a file named `application.properties` in `src/main/resources/`
-> Add your database configuration in the file as shown below:

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
server.port=8082

> Note: The actual `application.properties` file is excluded from this repository for security reasons.


## Team Contribution

This was a two-person academic project:
- I (Suhani Singh) developed the complete backend code, including logic, structure, endpoints, and frontend integration.
- My teammate contributed to documentation, report, and presentation materials.

## About Me

I’m Suhani Singh, currently a Computer Science (AI) student. This is one of my first backend projects, and I really enjoyed working with Spring Boot for this.
- [GitHub](https://github.com/SuhaniSingh)
