Currency Converter & Calculator

This service calculates the customer's bill, based on the items, their quantity and category.
It then applies discounts based on various conditions and provides the total bill amount in the required currency.
The following currency rate converter is used for this service : https://open.er-api.com/v6/latest/

Sample Request :

curl --location 'http://localhost:8080/api/calculate' \
--header 'X-API-KEY: test' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=3E31B3FEE7B9ECC4F924EE57943DD55D' \
--data '{
    "customerTenure":2,
    "userType":"CUSTOMER",
    "originalCurrency":"INR",
    "targetCurrency":"INR",
    "items":[
        {
            "name":"Fruits",
            "category":"GROCERY",
             "price":"10.0",
             "quantity":"10"
        },
        {
            "name":"Mobile",
            "category":"OTHERS",
             "price":"10",
             "quantity":"9"
        }
    ]
    
}
