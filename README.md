# E commerce project  (documentation)

## Database
![database-schema](https://github.com/gurkangokmen/ecommerce-app/assets/122023578/03526253-65b2-48d7-b980-4245001734f4)

## Api

| Api | Http Method | Description |
|------------------|------------------|------------------|
| http://localhost:8080/api/customers | POST | Add Customer |
|http://localhost:8080/api/products/ `{id}`| GET |Get Product|
|http://localhost:8080/api/products| POST | Add Product |
|http://localhost:8080/api/products/ `{id}`| DELETE |Delete Product|
|http://localhost:8080/api/products| PUT | Update Product |
|http://localhost:8080/api/carts/ `{customer_id}`| GET |Get Cart|
|http://localhost:8080/api/carts/ `{customer_id}`| DELETE | Empty Cart|
|http://localhost:8080/api/carts/| POST |Add Product To Cart|
|http://localhost:8080/api/carts?customerId= `{customer_id}`&productId= `{product_id}`| DELETE |Remove Product From Cart|
|http://localhost:8080/api/carts| PUT| Update Cart|
|http://localhost:8080/api/orders?customerId=`{customer_id}`| POST | PlaceOrder|
|http://localhost:8080/api/orders/ `{id}`|GET|Get Order For Code|
|http://localhost:8080/api/orders??customerId=`{customer_id}` |GET|Get All Orders For Customer |
