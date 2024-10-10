# **Currency Converter & Calculator**

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

#### **About the Project code**

The Request contains the information about the items purchased, the source currency, target currency and the type of user.
Based on type of user, the following criteria for discount are applied :

1. If the user is an Employee, he gets 30% discount.
2. If the user is an Affiliate, he gets 10% discount.
3. If the user is a customer, associated with for more than 2 years, then he is entitled for 5% discount.

A user can only get one of the above discount. However, the discounts are only applicable to non-grocery items.

On top of this, for each 100$ of the bill, 5% is also deducted.

The final bill is calculated and converted to the specified target currency, rounded up to 2 decimal places.

The endpoints are also made secure using Spring security. To access the endpoint, one needs to pass the below values in Header :

_**X-API-KEY : test**_

This service is integrated with a Third Party API https://www.exchangerate-api.com/ to get currency values in real time.
The service caches the recently accessed data and avoids calling the Exchange Rate API everytime.
It also uses Circuit Breaker pattern and provides a default response in case the Exchange Rate API isn't responding.

Project Structure :

![Project Structure.png](src%2Fmain%2Fresources%2FProject%20Structure.png)├

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

Project UML diagram :

![UML class diagram.png](src%2Fmain%2Fresources%2FUML%20class%20diagram.png)

Jacoco Test Report :

![Jacoco Test Report.png](src%2Fmain%2Fresources%2FJacoco%20Test%20Report.png)

Use case :

This service can be used at shopping centres, billing counters, to calculate and convert the bill using real time value of currency by calling the Third party API.
