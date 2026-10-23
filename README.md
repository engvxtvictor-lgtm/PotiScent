# Essência do Poti

Sistema acadêmico desenvolvido em Java para representar uma perfumaria artesanal, incluindo perfumes, fórmulas olfativas, matérias-primas, produção, maceração, envase, estoque, vendas e encomendas personalizadas.

## Tecnologias

- Java
- Programação Orientada a Objetos (POO)
- Git e GitHub
- Draw.io para o diagrama UML

## Integrantes

| Integrante | Usuário do GitHub | Módulo |
|---|---|---|
| Vítor Rufino | `@dev.vxt` | Produtos e fórmulas olfativas |
| Antônio Vitor Matos Cruz | `@antoniovitordematos` | Produção, matérias-primas, lotes e estoque |

## Organização do projeto

- `produtos`: produtos, perfumes, fórmulas, camadas olfativas e interfaces.
- `producao`: matérias-primas, lotes, frascos, produção e estoque.
- `pessoas`: funcionários e especializações de perfumistas.
- `Simulacao.java`: demonstração das regras de negócio e dos conceitos de POO.
- `docs`: documentação e diagrama de classes UML.

## Conceitos de POO e decisões de modelagem

- **Encapsulamento:** atributos privados protegem os dados e permitem controlar as alterações por meio de validações.
- **Herança:** permite representar especializações de produtos e funcionários, reduzindo a repetição de código.
- **Polimorfismo:** permite trabalhar com objetos de diferentes classes por meio de tipos comuns, respeitando seus comportamentos específicos.
- **Abstração:** classes abstratas representam conceitos gerais que não devem ser instanciados diretamente.
- **Interfaces:** definem contratos que podem ser implementados por classes diferentes.
- **Sobrescrita:** permite redefinir métodos herdados utilizando `@Override`.
- **Construtores:** inicializam objetos e validam dados. A sobrecarga permite diferentes formas de criação, enquanto `this(...)` e `super(...)` reaproveitam inicializações.
- **Controle de acesso:** os modificadores de acesso restringem a utilização dos atributos e métodos conforme a necessidade. O uso de `protected` deve ser justificado quando houver necessidade de acesso pelas subclasses.
- **Imutabilidade:** campos que não podem ser alterados após a criação, como o código do lote e o volume do frasco, não devem possuir métodos de alteração.

## Regras de negócio

A simulação contempla as regras RN01 a RN11, envolvendo concentração dos perfumes, composição das fórmulas olfativas, volumes dos frascos, tempo de maceração, identificação dos lotes, preço mínimo de venda, controle de estoque, validade das matérias-primas, assinatura de fórmulas, produtos aromáticos e encomendas personalizadas.

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
- `docs/diagrama-de-classes.drawio`: diagrama de classes UML editável no Draw.io.
