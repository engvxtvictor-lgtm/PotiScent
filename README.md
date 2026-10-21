\# Essência do Poti



Sistema acadêmico desenvolvido em Java para representar uma perfumaria artesanal, incluindo perfumes, fórmulas olfativas, matérias-primas, produção, maceração, envase, estoque, vendas e encomendas personalizadas.



\## Tecnologias



\- Java

\- Programação Orientada a Objetos (POO)

\- Git e GitHub



\## Integrantes



| Integrante | Responsabilidade |

|---|---|

| Victor Rufino (`@dev.vxt`) | Produtos e fórmulas olfativas |

| Antônio Vitor | Produção, matérias-primas, lotes e estoque |



\## Organização do projeto



\- `produtos`: produtos, perfumes, fórmulas, camadas olfativas e interfaces.

\- `producao`: matérias-primas, lotes, frascos, produção e estoque.

\- `pessoas`: funcionários e especializações de perfumistas.

\- `Simulacao`: testes das regras de negócio.

\- `docs`: decisões de modelagem e diagramas.



\## Conceitos de POO



\- Abstração e classes abstratas.

\- Encapsulamento.

\- Herança.

\- Polimorfismo.

\- Interfaces.

\- Sobrescrita de métodos com `@Override`.



\## Regras de negócio



A simulação verifica as regras RN01 a RN11, incluindo concentração dos perfumes, composição olfativa, frascos, maceração, lotes, preço mínimo, estoque, validade das matérias-primas, assinatura de fórmulas, produtos aromáticos e encomendas personalizadas.



\## Como executar



É necessário ter um JDK instalado e disponível no `PATH`.



Execute na raiz do projeto, pelo PowerShell:



```powershell

New-Item -ItemType Directory -Force out

javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter \*.java | ForEach-Object { $\_.FullName })

java -cp out br.edu.unifsa.perfumaria.Simulacao

```



\## Documentação adicional



\- `docs/DECISOES\_MODELAGEM.md`: decisões de modelagem.

\- `docs/diagramas/`: diagramas do sistema.



