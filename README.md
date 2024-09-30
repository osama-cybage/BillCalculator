# **Bill Calculator**

This service calculates the customer's bill, based on the items, their quantity and category.
It then applies discounts based on various conditions and provides the total bill amount in the required currency.

#### **Tech Stack Used :**

* Java 17
* Spring Boot 3.3.4
* Jacoco Plugin 0.0.8
* Mockito 5.11.0

#### **Sample Request :**

curl --location 'http://localhost:8080/api/calculate' \
--header 'API-Key: valid-api-key' \
--header 'API-Secret: valid-api-secret' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=816753487BB8ED76E89C9BC34C38ADC3' \
--data '
{
    "customerTenure":1,
    "userType":"CUSTOMER",
    "originalCurrency":"INR",
    "targetCurrency":"USD",
    "items":[
        {
        "name":"Fruits",
        "category":"GROCERY",
        "price":"10.0",
        "quantity":"4"
        },
        {
        "name":"Mobile",
        "category":"OTHERS",
        "price":"10.0",
        "quantity":"9"
        }
    ]
}
