### 🚀 **Cómo probar el código con Docker**

1. **Ubicación:**
   Pararse en el root del repositorio (el archivo `.env` ya está comiteado y listo para usar).

2. **Build del proyecto:**

   ```bash
   ./mvnw clean package -DskipTests
   ```

3. **Levantá todos los servicios con Docker Compose:**

   ```bash
   docker compose -f docker-compose.yml up --build
   ```

---

### 🧪 **JSON de prueba para endpoint de creación batch**

Podés usar el siguiente payload para probar la creación masiva de solicitudes (por ejemplo, con Postman o curl o desde el mismo swagger):

> **Nota:**
> Si el DTO es válido pero ocurre un error al comunicarse con la API de Pagos360, la solicitud será guardada con estado `ERROR`.

```json
[
  {
    "description": "Prueba 1",
    "firstDueDate": "15-07-2025",
    "firstTotal": "100",
    "payerName": "Mariano"
  },
  {
    "description": "Prueba 2",
    "firstDueDate": "16-07-2025",
    "firstTotal": "200",
    "payerName": "Mariano"
  },
  {
    "description": "Prueba 3",
    "firstDueDate": "16-07-2025",
    "firstTotal": "300",
    "payerName": "Mariano"
  },
  {
    "description": "Prueba 4",
    "firstDueDate": "16-07-2025",
    "firstTotal": "400",
    "payerName": "Mariano"
  },
  {
    "description": "Prueba 5",
    "firstDueDate": "16-07-2025",
    "firstTotal": "500",
    "payerName": "Mariano"
  },
  {
    "description": "Prueba 6",
    "firstDueDate": "16-07-2025",
    "firstTotal": "600",
    "payerName": "Mariano"
  }
]
```

---

### 📚 **Endpoints principales**

* **API de Solicitudes de Pago**
  [Swagger UI](http://localhost:8080/webjars/swagger-ui/index.html)

* **API para obtener un token (solo para pruebas, incluye biblioteca adicional):**
  [Swagger UI - Token Service](http://localhost:8083/webjars/swagger-ui/index.html)

