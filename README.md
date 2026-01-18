# ♟️ Jogo de Xadrez em Java (Console)

## 📌 Descrição do Projeto

Este projeto consiste no desenvolvimento de um **jogo de xadrez completo em Java**, executado via **console**, aplicando conceitos fundamentais de **Programação Orientada a Objetos (POO)**, como herança, polimorfismo, encapsulamento e tratamento de exceções.

O sistema implementa corretamente as **regras oficiais do xadrez**, incluindo movimentação das peças, capturas, roque, xeque e controle de turnos entre jogadores.

---

## 🎯 Objetivos

- Praticar **POO na linguagem Java**
- Modelar um jogo real respeitando regras e exceções
- Trabalhar com **matrizes**, **enumerações** e **exceções personalizadas**
- Criar uma base sólida para futuros projetos mais complexos

---

## 🧠 Conceitos Aplicados

- Programação Orientada a Objetos (POO)
  - Herança
  - Polimorfismo
  - Encapsulamento
  - Abstração
- Matrizes bidimensionais
- Enum (`enum`)
- Exceções personalizadas (`RuntimeException`)
- Organização em pacotes
- Lógica de jogos
- Boas práticas de código

---

## 🗂️ Estrutura do Projeto

```text
src/
├── aplicacao
│   └── Programa.java
│
├── jogodetabuleiro
│   ├── ExcecoesTabuleiro.java
│   ├── Posicao.java
│   ├── Peca.java
│   └── Tabuleiro.java
│
├── xadrez
│   ├── Cor.java
│   ├── ExcecoesXadrez.java
│   ├── PartidaDeXadrez.java
│   ├── PecaDeXadrez.java
│   └── PosicaoXadrez.java
│
└── xadrez/pecas
    ├── Rei.java
    ├── Rainha.java
    ├── Torre.java
    ├── Cavalo.java
    ├── Bispo.java
    └── Peao.java

♜ Peças Implementadas

Cada peça possui sua própria lógica de movimentação, respeitando as regras oficiais do xadrez:

Peça	Representação
Rei	    K
Rainha	R
Torre	T
Bispo	B
Cavalo	C
Peão	P
```

---

⚙️ Funcionalidades

✅ Movimentação válida de todas as peças

✅ Captura de peças adversárias

✅ Controle de turnos (Brancas e Pretas)

✅ Sistema de Xeque

✅ Implementação do Roque pequeno e grande

✅ Validação de movimentos ilegais

✅ Tratamento de exceções específicas do jogo

---

🚨 Tratamento de Exceções

O projeto utiliza exceções personalizadas para garantir robustez:

ExcecoesTabuleiro
→ Erros relacionados ao tabuleiro (posições inválidas, etc.)

ExcecoesXadrez
→ Erros específicos das regras do xadrez (movimentos inválidos, jogadas ilegais)

---

▶️ Como Executar o Projeto
Pré-requisitos

Java JDK 8 ou superior

IDE Java (Eclipse, IntelliJ ou VS Code)

Passos

Clone o repositório:

git clone https://github.com/seu-usuario/jogo-xadrez-java.git


Abra o projeto na IDE

Execute a classe:

aplicacao.Programa


Jogue diretamente pelo console, seguindo as instruções exibidas.

🧪 Exemplo de Execução (Console)
8 T C B R K B C T
7 P P P P P P P P
6 - - - - - - - -
5 - - - - - - - -
4 - - - - - - - -
3 - - - - - - - -
2 P P P P P P P P
1 T C B R K B C T
  a b c d e f g h

Turno: BRANCO
Origem: e2
Destino: e4

---

👨‍💻 Autor

João Neto

🎓 Estudante de Sistemas de Informação

Telefone: 3499683-2626
email: neto31510@gmail.com
