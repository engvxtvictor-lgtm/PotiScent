# Decisões de Modelagem — Essência do Poti

## 1. Organização do sistema

O sistema foi desenvolvido em Java, com separação das responsabilidades em pacotes:

- `produtos`: produtos, perfumes e fórmulas olfativas.
- `producao`: matérias-primas, lotes, estoque e produção.
- `pessoas`: profissionais envolvidos no sistema.

## 2. Fórmula olfativa — RN02

A classe `FormulaOlfativa` representa a composição olfativa de um perfume por meio de três camadas:

- Saída;
- Coração;
- Fundo.

As porcentagens das três camadas devem totalizar exatamente 100%. Cada camada também valida seus ingredientes, exigindo percentuais válidos e uma composição total de 100%.

As validações rejeitam camadas com percentual zero ou negativo e camadas sem ingredientes.

## 3. Assinatura de fórmulas — RN09

A classe `Perfumista` representa o profissional responsável pela assinatura.

A classe `FormulaOlfativa` verifica se o profissional pode assinar a fórmula do produto. Perfumistas júnior não podem assinar fórmulas de produtos da classe `Parfum`; profissionais de nível superior podem.

A validação impede a operação inválida por meio de `IllegalArgumentException`.

## 4. Testes da simulação

A classe `Simulacao` executa testes de aceitação e rejeição para as regras implementadas.

Os testes atuais verificam:

- Fórmula olfativa válida;
- Soma incorreta das porcentagens das camadas;
- Soma incorreta dos ingredientes;
- Percentuais zero, negativos ou acima de 100%;
- Camadas sem ingredientes;
- Bloqueio da assinatura de Parfum por perfumista júnior;
- Permissão da assinatura por perfumista sênior.

## 5. Decisões de implementação

- `BigDecimal` é utilizado para representar valores decimais, evitando problemas comuns de precisão de `double`.
- Exceções são utilizadas para rejeitar dados ou operações inválidas.
- A simulação permite verificar o comportamento das regras sem depender de uma interface gráfica.

## 6. Estado da documentação

Este documento registra as decisões relacionadas às regras RN02 e RN09 e à estrutura observada no código. As demais regras de negócio devem ser documentadas após a conferência de suas implementações.
