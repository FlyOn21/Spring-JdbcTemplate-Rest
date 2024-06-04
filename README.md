### Preparing

**Step 1:** Up MySQL database.
 - Open folder `src/main/resources/`
 - Rename file `.env_example` to `.env`
 - Change value in `.env` file to your MySQL database configuration.
 - Run in command line `docker-compose --env-file .env up -d`
 - Check MySQL database is running by command `docker ps`
 - Connect to MySQL database 
 - Create your database user and attached to your database.
 - Run `src/main/resources/schema.sql` to create database schema.

**Step 2:** Configure db.properties file.
 - Open folder `src/main/resources/db/`
 - Rename file `db.properties_example` to `db.properties`
 - Change value in `db.properties` file.

**Step 3:** Run application.
 - Create folder `Catalina` in root project directory.
 - Configure Tomcat server in your IDE.
 - Run Tomcat server.

### Get All Customers

**Endpoint:** GET `http://<your_host>:<your_port>/api/v1/customers/all`

**Description:**
This endpoint retrieves all customers from the system.

**Parameters:**
None

**Response:**
- `200 OK`: Successfully retrieved all customers.
- `500 Internal Server Error`: Failed to retrieve data due to an internal server error.

### Get One Customer

**Endpoint:** GET `http://<your_host>:<your_port>/api/v1/customers/specific?id=<customer_id>`

**Description:**
This endpoint retrieves one customer with specific id from the system.

**Parameters(Query):**
`id=<customer_id>` required, number. Customer id.

**Response:**
- `200 OK`: Successfully retrieved all customer.
- `400 Bad Request`: Failed to retrieve data due to bad request.
- `404 Not Found`: Failed to retrieve data due to not found.
- `500 Internal Server Error`: Failed to retrieve customer data due to an internal server error.

### Create Customer

**Endpoint:** POST `http://<your_host>:<your_port>/api/v1/customers/create`

**Description:**
This endpoint creates a new customer in the system.

**Body:**
```json
{
    "firstName": "required, string. Customer first name",
    "lastName": "required, string. Customer last name",
    "email": "required, string. Customer email",
    "phoneNumber": "required, string. Customer phone number. E.164 format"
}
```

**Response:**
- `201 Create`: Successfully create customer.
- `409 Conflict`: Failed to create customer due to conflict.
- `500 Internal Server Error`: Failed to create customer due to an internal server error.

### Update Customer

**Endpoint:** PUT `http://<your_host>:<your_port>/api/v1/customers/specific?id=<customer_id>`

**Description:**
This endpoint update exists user in the system.

**Parameters(Query):**
`id=<customer_id>` required, number. Customer id.

**Body:**
```json
{
    "firstName": "optional, string. Customer first name",
    "lastName": "optional, string. Customer last name",
    "email": "optional, string. Customer email",
    "phoneNumber": "optional, string. Customer phone number. E.164 format"
}
```

**Response:**
- `202 Accepted`: Successfully update customer.
- `400 Bad Request`: Failed to update customer due to bad request.
- `409 Conflict`: Failed to update customer due to conflict.
- `404 Not Found`: Failed to update customer due to not found.
- `500 Internal Server Error`: Failed to update due to an internal server error.

### Delete Customer

**Endpoint:** DELETE `http://<your_host>:<your_port>/api/v1/customers/specific?id=<customer_id>`

**Description:**
This endpoint delete exists customer in the system.

**Parameters(Query):**
`id=<customer_id>` required, number. Customer id.

**Response:**
- `200 OK`: Successfully delete user.
- `400 Bad Request`: Failed to delete customer due to bad request.
- `409 Conflict`: Failed to delete customer due to conflict.
- `404 Not Found`: Failed to delete customer due to not found.
- `500 Internal Server Error`: Failed to delete due to an internal server error.