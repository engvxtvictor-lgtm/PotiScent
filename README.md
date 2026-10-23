# Essência do Poti

Sistema acadêmico desenvolvido em Java para representar uma perfumaria artesanal, incluindo perfumes, fórmulas olfativas, matérias-primas, produção, maceração, envase, estoque, vendas e encomendas personalizadas.

## Tecnologias

- Java
- Programação Orientada a Objetos (POO)
- Git e GitHub

## Integrantes

| Integrante | Responsabilidade |
|---|---|
| Vítor Rufino (`@dev.vxt`) | Produtos e fórmulas olfativas |
| Antônio Vitor Matos Cruz (`@antoniovitordematos`) | Produção, matérias-primas, lotes e estoque |

## Organização do projeto

- `produtos`: produtos, perfumes, fórmulas, camadas olfativas e interfaces.
- `producao`: matérias-primas, lotes, frascos, produção e estoque.
- `pessoas`: funcionários e especializações de perfumistas.
- `Simulacao.java`: demonstração das regras de negócio e dos conceitos de POO.
- `docs`: documentação e diagrama de classes UML.

## Conceitos de POO

- **Encapsulamento:** proteção dos atributos e controle de acesso aos dados.
- **Herança:** reutilização e especialização de classes.
- **Polimorfismo:** utilização de objetos de diferentes especializações por meio de tipos comuns.
- **Abstração:** representação de conceitos gerais por classes abstratas.
- **Interfaces:** definição de contratos para classes.
- **Sobrescrita:** redefinição de métodos herdados utilizando `@Override`.
- **Construtores:** inicialização dos objetos e validação dos dados.

## Regras de negócio

A simulação demonstra as regras RN01 a RN11, abrangendo concentração dos perfumes, composição das fórmulas olfativas, volumes dos frascos, tempo de maceração, identificação dos lotes, preço mínimo de venda, controle de estoque, validade das matérias-primas, assinatura de fórmulas, produtos aromáticos e encomendas personalizadas.

## Como executar

É necessário ter o JDK instalado e configurado no `PATH`.

Na raiz do projeto, execute os comandos no PowerShell:

```powershell
New-Item -ItemType Directory -Force .\build | Out-Null

javac -encoding UTF-8 -d .\build (Get-ChildItem .\src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

java -cp .\build br.edu.unifsa.perfumaria.Simulacao
```

## Documentação adicional

- `docs/DECISOES_MODELAGEM.md`: decisões e justificativas de modelagem.
- `docs/diagrama-de-classes..drawio`: diagrama de classes UML editável no Draw.io.
