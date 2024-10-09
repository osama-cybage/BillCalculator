# **Bill Calculator**

This service calculates the customer's bill, based on the items, their quantity and category.
It then applies discounts based on various conditions and provides the total bill amount in the required currency.

#### **Tech Stack Used :**

* Java 17
* Spring Boot 3.3.4
* Jacoco Plugin 0.0.8
* Log4j 1.2.5.RELEASE
* Mockito 5.11.0

#### **Setting up Locally**

To setup the service locally, execute the below commands in order :

- git clone https://github.com/osama-cybage/BillCalculator
- git checkout -b {your-branch-name}

#### **Building and Running the service**

Just execute the command _mvn clean install_ for build and click on 'Run -> _Run as SpringBootApplication'.

#### **Sample Request :**

curl --location 'http://localhost:8080/api/calculate' \
--header 'X-API-KEY: test' \
--header 'Content-Type: application/json' \
--data '{
    "user": {
        "userType": "EMPLOYEE",
        "customerTenure": "1"
    },
    "originalCurrency": "USD",
    "targetCurrency": "USD",
    "items": [
        {
            "name": "Fruits",
            "category": "GROCERY",
            "price": "10.0",
            "quantity": "10"
        },
        {
            "name": "Mobile",
            "category": "OTHERS",
            "price": "10",
            "quantity": "9"
        }
    ]
}'
