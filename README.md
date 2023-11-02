# Price Domain

This service exposes a REST interface to calculate the final price of a product, taking into account any discounts.
The endpoint can be reached at http://localhost:8080/v1/price/{productId};
the last part of the url contains the identifier of the product whose price you want to know.

In case the order contains multiple copies of the product, you can indicate the quantity using the query parameter
`quantity`: http://localhost:8080/v1/price/{productId}?quantity=2.
If not specified, the default quantity is 1.

Two types of discounts can be configured: by value or by percentage. 
Discounts by value apply a unit discount to the final price (example: 10€ discount), while discounts by percentage apply
a proportional discount to the final price (example: 10% discount).
In both cases, it is possible to configure a promotion by assigning it to a specific product, or to the entire catalog. 
In the first case, by specifying the product identifier in the policy, the discount is applied only to a specific product,
while for all others such a discount will not be available

The configuration of discounts by value can be done via a CRUD API on the url http://localhost:8080/v1/value-discount.
While the configuration of discounts by percent can be done via the url http://localhost:8080/v1/percentage-discount.

Full details on API use can be accessed via the Swagger interface at the url http://localhost:8080/swagger-ui.html.

## Products available

Below are references of the products, and their price, that you can use to test the APIs.

| UUID                                 | Name              | Price  |
|--------------------------------------|-------------------|--------|
| a670eb1d-4cbd-4a9f-aecc-42e5ab42ce46 | iPhone 15         | 799 €  |
| 4fc02e92-f59b-4a43-8a30-3410f83149cc | iPhone 15 Plus    | 899 €  |
| faf44e6f-eaa5-40e3-860a-eff149188040 | iPhone 15 Pro     | 999 €  |
| 11b73079-9da9-48ff-bd1c-77949daac3f1 | iPhone 15 Pro Max | 1199 € |
| 173a1602-b9d7-47a4-bb56-dd9e1d3d88a2 | iPhone 14         | 699 €  |
| b617852c-dc98-4a15-a5a7-59bd6d501366 | iPhone 14 Plus    | 799 €  |
| a42caa5c-bb33-434b-b7fd-08d831b3883c | iPhone 13         | 599 €  |
| 5a1928cd-87f2-423d-8c5b-aa9c5b8fe947 | iPhone SE         | 429 €  |

## Built-in policies

Below are some pre-configured policies you can use to test the APIs.

| UUID                                 | Target product | Min quantity | Discount |
|--------------------------------------|----------------|--------------|----------|
| 114c6e32-9dbe-454e-b6e9-7db11da5b685 | iPhone SE      | 1            | 10 €     |
| 82497aad-032f-4c23-aa2c-409fab8e9f94 | iPhone SE      | 2            | 25 €     |
| 3f5459fa-2488-4c4c-bce2-7dcb50291010 | iPhone SE      | 3            | 50 €     |
| 395e3c49-713a-4330-ac0d-5d6718cb3b72 | _all_          | 2            | 2%       |

## Run the application

This is a containerized application. You can run it through the docker file with this command

```shell
docker-compose up -d
```

the spring-boot application is exposed on port 8080 and will be accessible at the url http://localhost:8080,
while the related Postgres DB is exposed on port 5432.

## Build the application

To build the application, simply run the command

```shell
./mvnw clean package
```

To launch the spring-boot application from your local environment, run the command

```shell
./mvnw spring-boot:run
```

> a postgres DB is required. You can use the db service defined in the docker compose with the command
> ```shell
> docker-compose up db -d
> ```

To configure access to the DB you need to set these env variables

```properties
DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<db>
DATASOURCE_USERNAME=<username>
DATASOURCE_PASSWORD=<password>
```

