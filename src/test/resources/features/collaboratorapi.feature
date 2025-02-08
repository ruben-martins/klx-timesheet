# language: pt
Funcionalidade: Listar Colaboradores

  Como um cliente da API
  Quero obter uma lista de colaboradores
  Para visualizar todos os colaboradores cadastrados no sistema

  Cenário: Listar todos os colaboradores com sucesso
    Dado que existem colaboradores cadastrados no sistema:
      | name  | lastName | email                      | hireDate   | position   |
      | Alice | Silva    | alice.silva@example.com    | 2025-01-01 | Secretary |
      | Bruno | Costa    | bruno.costa@example.com    | 2025-01-01 | Director  |
      | Carla | Ferreira | carla.ferreira@example.com | 2025-01-01 | Professor |
    Quando eu faço uma requisição GET para obter colaboradores
    Então a resposta deve ter o status code 200
    E a resposta deve conter uma lista de colaboradores
    E a resposta deve conter os dados que foram cadastrados previamente