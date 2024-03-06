# Tic-Bricks
Test java developer

## Descripción

Esta es una api Rest desarrollada en Java 17 con Spring Boot 3.2.3 que proporciona una serie de endPoints para gestionar productos. La documentación de la API se encuentra disponible en [Swagger UI](http://localhost:8080/api/swagger-ui/index.html).

## Configuración

La aplicación utiliza una base de datos  H2 en memoria con las siguientes credenciales:
Conexion a la consola [H2](http://localhost:8080/api/h2-console)
- **URL:** jdbc:h2:mem:testdb
- **Usuario:** ticbrick
- **Contraseña:** ticbrick1234


## Requisitos

Para poder utilizar esta api, se necesitan los siguientes requisitos:

- Java 17
- Gradle

## Instrucciones de Uso

1. Clona el repositorio.
2. Navega al directorio del proyecto.
3. Ejecute el comando que desee para iniciar, compilar o testear la api Segun su necesidad.
    ```bash
    ./gradlew bootRun
    ./gradlew clean build
    ./gradlew test
    ```

4. Una vez iniciado el proyecto, la API estará disponible en [http://localhost:8080/api](http://localhost:8080/api). junto a una serie de endPoints:

## Endpoints

La API proporciona los siguientes endpoints para gestionar productos:

- **GET /products/**: Obtiene todos los Productos.
- **GET /products/{id}**: Obtiene una producto por su ID.
- **GET /products?name={name}**: Obtiene una lista de productos por nombre.
- **GET /products/stock?stock={stock}**: Obtiene una lista de productos por stock.
- **GET /products/price?price={price}**: Obtiene una lista de productos por price.
- **GET /products/pageable?page=1&size=2'**: Obtiene una lista de productos con paginación.
- **GET /products/category/**: Obtiene todas las categorias utilizando cache, detalles mas abajo!!.
- **GET /products/category?category={category}**: Obtiene una lista de productos por category (Nombre de Categoria).
- **GET /products/filter?name={name}&price={price}&stock={stock}&category={category}**: Obtiene una lista de productos ((filtro por name, price, stock y category)).
- **PUT /products/{id}**: Actualiza un Producto existente.
- **POST /products/**: Crea un Producto.
- **DELETE /products/{id}**: Elimina un Producto por su ID.


Cada endpoint tiene su descripción y los posibles resultados.

En la ruta siguiente dentro del proyecto encontraras la coleccion de postman:
- **src/main/resources/Tick-Brick-OpenAPI definition.postman_collection.json**

## Contacto

Si tienes alguna pregunta o problema, no dudes en contactarme [correo electrónico](mailto:julioflores781@gmail.com).
