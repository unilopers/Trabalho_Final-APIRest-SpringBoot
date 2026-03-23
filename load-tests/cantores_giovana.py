from locust import HttpUser, task, between
import random

class CantorGiovanaTest(HttpUser):
    wait_time = between(1, 2)

    @task
    def fluxo_completo(self):
        nome_aleatorio = f"Cantor {random.randint(1, 10000)}"

        self.client.post("/cantores/novo", json={
            "nome": nome_aleatorio,
            "genero": "Pop"
        })

        self.client.get("/cantores")