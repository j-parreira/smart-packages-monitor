# DAE Entrega intermédia - API #

  Diogo Abegão - 2222184,

  João Parreira - 2221985, 
  
  Marcelo Oliveira - 2222120, 
  
  Pedro Barbeiro - 2221986

---


**EP01**. Um *manager* consulta a lista de todos os clientes utilizando o protocolo HTTP, verbo **GET**, para o sítio:
/customers

A resposta devolvida por este recurso segue o formato JSON:

~~~json
[
    {
        "id": 1,
        "name": "Customer 1",
        "email": "carlos@ipleiria.pt",
        "orders": [],
        "address": "Rua do Ouro, 1000-001 Lisboa"
    },
    {
        "id": 2,
        "name": "Customer 2",
        "email": "antonio@ipleiria.pt",
        "orders": [],
        "address": "Rua da Prata, 1000-002 Lisboa"
    },
    ...
]
~~~

---

**EP02**. Um *manager* consulta um cliente específico e todos os pedidos que realizou utilizando o protocolo HTTP, verbo **GET**, para o sítio: /customers/{id}

A resposta devolvida por este recurso segue o formato JSON:

~~~json
{
    "id": 1,
    "name": "Customer 1",
    "email": "carlos@ipleiria.pt",
    "orders": [
    {
        "id": 1,
        "customer_id": 1,
        "volumes": [],
        "products": [],
        "status": "Delivered",
        "created_at": "2021-01-01T00:00:00Z",
        "payment_type": "MBWAY",
        "address": "Rua do Ouro, 1000-001 Lisboa"
    },
    {
        "id": 2,
        "customer_id": 1,
        "volumes": [],
        "products": [],
        "status": "Processing",
        "created_at": "2024-11-01T21:00:00Z",
        "payment_type": "PAYPAL",
        "address": "Rua do Ouro, 1000-001 Lisboa"
    }
    ],
    "address": "Rua do Ouro, 1000-001 Lisboa"
}
~~~

---

**EP03**. Um *customer* consulta a lista de todos os produtos disponíveis utilizando o protocolo HTTP, verbo **GET**, para o sítio: /products

A resposta devolvida por este recurso segue o formato JSON:

~~~json
[
    {
        "id": 1,
        "name": "Product 1",
        "warehouse_id": 1,
        "type": "Electronics",
        "order_id": null,
        "volume_id": null
    },
    {
        "id": 2,
        "name": "Product 2",
        "warehouse_id": 2,
        "type": "Frozen_food",
        "order_id": null,
        "volume_id": null
    },
    ...
]
~~~

---

**EP04**. Um *customer* consulta um produto específico utilizando o protocolo HTTP, verbo **GET**, para o sítio: /products/{id}

A resposta devolvida por este recurso segue o formato JSON:

~~~json
{
    "id": 1,
    "name": "Product 1",
    "warehouse_id": 1,
    "type": "Electronics",
    "order_id": null,
    "volume_id": null
}
~~~

---

**EP05**. Um *customer* compra um produto específico, ou um empregado do armazém associa um produto específico a um volume utilizando o protocolo HTTP, verbo **PATCH**, para o sítio: /products/{id}

O corpo do pedido recebido por este recurso segue o formato JSON:

~~~json
{
    "order_id": 1,
    "volume_id": 1
}
~~~
A resposta devolvida por este recurso segue o formato JSON:

~~~json
{
    "id": 1,
    "name": "Product 1",
    "warehouse_id": 1,
    "type": "Electronics",
    "order_id": 1,
    "volume_id": 1
}
~~~

---

**EP06**. Um *manager* consulta a lista de todos os pedidos utilizando o protocolo HTTP, verbo **GET**, para o sítio: /orders

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
[
  {
      "id": 1,
      "customer_id": 1,
      "volumes": [],
      "products": [],
      "status": "Processing",
      "created_at": "2021-01-01T00:00:00Z",
      "payment_type": "MBWAY",
      "address": "Rua do Ouro, 1000-001 Lisboa"
    },
    {
      "id": 2,
      "customer_id": 2,
      "volumes": [],
      "products": [],
      "status": "Processing",
      "created_at": "2021-01-01T00:00:00Z",
      "payment_type": "MBWAY",
      "address":"Rua de Santa Catarina, 4000-001 Porto"
    },
    ...
]
~~~

---

**EP07**. Um *customer* ou *employee* consulta um pedido específico e todos os volumes e produtos do pedido utilizando o protocolo HTTP, verbo **GET**, para o sítio: /orders/{id}

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
[
  {
      "id": 1,
      "customer_id": 1,
      "volumes": [
        {
          "id": 1,
          "type": "cardboard box",
          "volume_number": 1,
          "products": [],
          "sensors": [],
          "employee": 1,
          "status": "dispatched",
          "order_id": 1
        },
        ...
      ],
      "products": [
        {
          "id": 1,
          "name": "Product 1",
          "warehouse_id": 1,
          "type": "Electronics",
          "order_id": 1,
          "volume_id": 1
        },
        ...
      ],
      "status": "Processing",
      "created_at": "2021-01-01T00:00:00Z",
      "payment_type": "MBWAY",
      "address": "Rua do Ouro, 1000-001 Lisboa"
  },
  ...
]  

~~~

---

**EP08**. Um *customer*, cria um pedido utilizando o protocolo HTTP, verbo **POST**, para o sítio: /orders

O corpo do pedido recebido por este recurso segue o formato JSON:
~~~JSON
{
  "customer_id": 1,
  "volumes": [],
  "products": [
    {
      "id": 1,
      "name": "Product 1",
      "warehouse_id": 1,
      "type": "Electronics",
      "order_id": null,
      "volume_id": null
    },
    ...
  ],
  "status": null,
  "created_at": "2021-01-01T00:00:00Z",
  "payment_type": "MBWAY",
  "address": "Rua do Ouro, 1000-001 Lisboa"
}

~~~

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
{
  "customer_id": 1,
  "volumes": [],
  "products": [
    {
      "id": 1,
      "name": "Product 1",
      "warehouse_id": 1,
      "type": "Electronics",
      "order_id": 1,
      "volume_id": null
    },
    ...
  ],
  "status": "Processing",
  "created_at": "2021-01-01T00:00:00Z",
  "payment_type": "MBWAY",
  "address": "Rua do Ouro, 1000-001 Lisboa"
}

~~~

---

**EP09**. Um *employee* atualiza o status de um pedido utilizando o protocolo HTTP, verbo **PATCH**, para o sítio: /orders/{id}

O corpo do pedido recebido por este recurso segue o formato JSON:

~~~JSON
{
  "status": "Delivered"
}
~~~

A resposta devolvida por este recurso segue o formato JSON:
~~~JSON
{
  "id": 1,
  "customer_id": 1,
  "volumes": [],
  "products": [],
  "status": "Delivered",
  "created_at": "2021-01-01T00:00:00Z",
  "payment_type": "MBWAY",
  "address": "Rua do Ouro, 1000-001 Lisboa"
}
~~~

---

**EP10**. Um *customer*/*employee*/*manager* consulta todos os volumes de um pedido utilizando o protocolo HTTP, verbo **GET**, para o sítio: /orders/{id}/volumes

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
[
  {
    "id": 1,
    "volume_type": "Cardboard Box",
    "volume_number": 1,
    "products": [
      {
        "id": 1,
        "name": "Product 1",
        "warehouse_id": 1,
        "type": "Electronics",
        "order_id": 1,
        "volume_id": 1
      },
      ...
    ],
    "sensors": [
      {
        "id": 1,
        "type": "temperature",
        "is-active": true,
        "threshold": 25,
        "readings": []
      },
      ...
    ],
    "employee": 1,
    "status": "dispatched",
    "order_id": 1
  },
  ...
]
~~~

---

**EP11**. Um *employee* cria um volume num pedido utilizando o protocolo HTTP, verbo **POST**, para o sítio: /orders/{id}/volumes

O corpo do pedido recebido por este recurso segue o formato JSON:

~~~JSON
{
  "id": 1,
  "type": "cardboard box",
  "volume_number": null,
  "products": [
    {
      "id": 1,
      "name": "Product 1",
      "warehouse_id": 1,
      "type": "Electronics",
      "order_id": 1,
      "volume_id": 1
    },
    ...
  ],
  "sensors": [
    {
      "id": 1,
      "type": "temperature",
      "is-active": true,
      "threshold": 25,
      "readings": []
    },
    ...
  ],
  "employee": 1,
  "status": "dispatched",
  "order_id": 1
}
~~~

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
{
  "id": 1,
  "type": "cardboard box",
  "volume_number": 1,
  "products": [
    {
      "id": 1,
      "name": "Product 1",
      "warehouse_id": 1,
      "type": "Electronics",
      "order_id": 1,
      "volume_id": 1
    },
    ...
  ],
  "sensors": [
    {
      "id": 1,
      "type": "temperature",
      "is-active": true,
      "threshold": 25,
      "readings": []
    },
    ...
  ],
  "employee": 1,
  "status": "dispatched",
  "order_id": 1
}
~~~

---

**EP12**. Um *customer* consulta um volume específico utilizando o protocolo HTTP, verbo **GET**, para o sítio: /volumes/{id}

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
{
    "id": 1,
    "type": "cardboard box",
    "volume_number": 1,
    "products": [],
    "sensors": [],
    "employee": 1,
    "status": "dispatched",
    "order_id": 1
}
~~~

---

**EP13**. Um *employee* altera o estado de um volume específico utilizando o protocolo HTTP, verbo **PATCH**, para o sítio: /volumes/{id}

O corpo do pedido recebido por este recurso segue o formato JSON:

~~~JSON
{
  "status": "in transit"
}
~~~
A resposta devolvida por este recurso segue o formato JSON:
~~~JSON
{
    "id": 1,
    "type": "cardboard box",
    "volume_number": 1,
    "products": [],
    "sensors": [],
    "employee": 1,
    "status": "in transit",
    "order_id": 1
}
~~~

---

**EP14**. Um *customer*/*employee* consulta todos os sensores de um volume e a última leitura utilizando o protocolo HTTP, verbo **GET**, para o sítio: /volumes/{id}/sensors

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
[
  {
    "id": 1,
    "type": "temperature",
    "is_active": true,
    "threshold": 25,
    "readings": [
      {
        "id": 101,
        "sensor_id": 2,
        "value": 25, 
        "timestamp": "2024-11-01T11:00:00Z"
      }
    ]
  },
  {
    "id": 2,
    "type": "acceleration",
    "is_active": true,
    "threshold": 9.8,
    "readings": [
      {
        "id": 102,
        "sensor_id": 1,
        "value": "9.9",
        "timestamp": "2024-11-01T11:00:00Z"
      }
    ]
  },
  ...
]
~~~

---

**EP15**. Um *customer*/*employee* consulta todos os produtos de um volume utilizando o protocolo HTTP, verbo **GET**, para o sítio: /volumes/{id}/products

A resposta devolvida por este recurso segue o formato JSON:

~~~JSON
[
  {
    "id": 1,
    "name": "Product 1",
    "warehouse_id": 1,
    "type": "Electronics",
    "order_id": 1,
    "volume_id": 1
  },
  {
    "id": 2,
    "name": "Product 2",
    "warehouse_id": 1,
    "type": "Frozen Food",
    "order_id": 1,
    "volume_id": 1
  }
]
~~~

---

**EP16**. Um *manager* consulta a lista de todos os sensores ativos utilizando o protocolo HTTP, verbo **GET**, para o sítio: /sensors

A resposta devolvida por este recurso segue o formato JSON:

~~~json
[
  {
    "id": 1,
    "type": "temperature",
    "is-active": true,
    "threshold": 25,
    "readings": []
  },
  {
    "id": 2,
    "type": "temperature",
    "is-active": true,
    "threshold": 12,
    "readings": []
  },
  ...
]
~~~

---

**EP17**. Um *manager* consulta um sensor específico utilizando o protocolo HTTP, verbo **GET**, para o sítio:/sensors/{id}

A resposta devolvida por este recurso segue o formato JSON:

~~~json
{
  "id": 1,
  "type": "temperature",
  "is-active": true,
  "threshold": 25,
  "readings": []
}
~~~

---

**EP18**. Um *manager* consulta o histórico das leituras de um sensor específico utilizando o protocolo HTTP, verbo **GET**,para o sítio:/sensors/{id}/readings

A resposta devolvida por este recurso segue o formato JSON:

~~~json
[
  {
    "id": 1,
    "sensor_id": 1,
    "value": 19,
    "timestamp": "2021-01-01T00:00:00Z"
  },
  {
    "id": 2,
    "sensor_id": 1,
    "value": 20,
    "timestamp": "2022-01-01T00:01:00Z"
  },
  ...
]
~~~

---

**EP19**. Um *manager* consulta uma leitura específica de um sensor específico utilizando o protocolo HTTP, verbo **GET**, para o sítio:/sensors/{id}/readings/{id}

A resposta devolvida por este recurso segue o formato JSON:
~~~json
{
  "id": 1,
  "sensor_id": 1,
  "value": 19,
  "timestamp": "2021-01-01T00:00:00Z"
}
~~~
