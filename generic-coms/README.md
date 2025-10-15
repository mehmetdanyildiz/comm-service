# Generic Communication Service

HTTP / RabbitMQ / WebSocket mesajlarını tek bir servis üzerinden gönderen **generic** bir Spring Boot uygulaması.
- Swagger UI: `/swagger-ui.html`
- Dashboard: `/`

## Docker ile Çalıştırma (IDE gerektirmez)
```
docker compose up --build
```
Bu komut hem uygulamayı hem de RabbitMQ'yu ayağa kaldırır.
- Uygulama: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- RabbitMQ Yönetim Paneli: http://localhost:15672 (guest/guest)

## REST
`POST /api/messages` gövde örnekleri Swagger UI'da mevcuttur.
- HTTP: `httpUrl`, `httpHeaders` (JSON map), `httpBody`
- RABBIT: `rabbitExchange` (opsiyonel), `rabbitRoutingKey` (varsayılan: generic.queue), `rabbitMessage`
- WS: `wsDestination` (varsayılan: /topic/updates), `wsMessage`

Sayaçlar: `GET /api/stats`

## Gelişmiş
- JDK 17, Spring Boot 3.3, springdoc-openapi, spring-amqp, websocket
- CORS açıktır, dashboard ve Swagger'dan test edebilirsiniz.
