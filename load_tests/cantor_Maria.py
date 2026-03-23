from locust import HttpUser, task, between

API_KEY = "sk-9c9fe670-e155-4586-ad3e-c7795e0834ab"

class CantorUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.headers = {
            "X-API-Key": API_KEY,
            "Content-Type": "application/json"
        }

    @task(2)
    def listar_cantores(self):
        self.client.get("/cantores", headers=self.headers)

    @task(1)
    def buscar_cantor(self):
        self.client.get("/cantores/1", headers=self.headers)

    @task(1)
    def criar_cantor(self):
        payload = {
            "nome": "Maria Teste",
            "nacionalidade": "Brasil"
        }
        self.client.post("/cantores/novo", json=payload, headers=self.headers)