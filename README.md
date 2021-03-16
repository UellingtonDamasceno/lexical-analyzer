# Analisador léxico

## Sumário
- [Descrição](#descrição)
- [Estrutura Léxica](#estrutura-léxica)
- [Entrada](#entrada)
- [Saída](#saída)
- [Lista de Siglas](#lista-de-siglas)


## Descrição

"Depois de um ano conturbado por conta da pandemia os tutores do MI -
Processadores e Linguagem de Programação estão voltando “com tudo” e querem
saber se os alunos também estão animados. Para isso estão lançando uma série
de desafios para testarem os conhecimentos de seus pupilos.

O primeiro desafio é desenvolver um analisador léxico para uma linguagem que
será apresentada em etapas. Para esta etapa foi disponibilizada uma tabela
contendo a estrutura léxica da linguagem."

## Estrutura Léxica

| Descrição | Expressões |
| ----------| ----- |
| Palavras resevadas| `var` `const` `typedef` `struct` `extends` `procedure` `function` `start` `return` `if` `else` `then` `while` `read` `print` `int` `real` `boolean` `string` `true` `false` `global` `local` |
| Indentificadores | letra ( letra \| dígito \| `_` )* |
| Números | Dígito+(.Dígito+)? |
| Dígito | [0-9] |
| Letra | [a-z] \| [A-Z] |
| Operadores aritiméticos | `+` `-` `*` `/` `++`  `--` |
| Operadores relacionais | `==` `!=` `>` `>=` `<` `<=` `=` |
| Operadores lógicos | `&&` `\|\|` `!` |
| Delimitadores de comentário | `//` Isto é um comentário de linha </br> `/*` Isto é um comentário </br> de bloco `*/` |
| Delimitadores | `;` `,` `( )` `{ }` `[ ]` `.`|
| Cadeia de caracteres | "( letra \| dígito \| símbolo \| `\"` )* " |
| Símbolo | ASCII de 32 a 126 (exceto ASCII 34) |

## Entrada

A entrada para este analisador é um conjunto de
arquivos texto com os nomes entradaX.txt, onde X é um
valor numérico que identifica cada um dos arquivos de
entrada. 

## Saída

Será gerado um conjunto de arquivos de saída
(um para cada arquivo de entrada), denominados
saidaX.txt, onde X é um valor numérico, referente a
cada arquivo de entrada, com a resposta do analisador
léxico.

## Lista de siglas
_Tokens_ possuem tipos e esses tipos são representados pela seguinte tabela
de siglas:

| Sigla | Descrição | - | Sigla | Descrição |
| :---: | --------- | :-: | :---: | --------- |
| `PRE` | Palavra Reservada |  | `IDE` | Identificador |
| `NRO` | Número | | `DEL` | Delimitador |
| `REL` | Operador Relacional |  | `LOG` | Operador Lógico |
| `ART` | Operador Aritmético |  | `SIB` | Simbolo Inválido |
| `CMF` | Cadeia Mal Formada |  | `NMF` | Número Mal Formado |
| `CoMF` | Comentário Mal Formado |  | `OpMF` | Operador Mal Formado |
| `CAD` | Cadeia de Caracteres |  |  |  |

----------

| :arrow_left: [Problema anterior](https://github.com/UellingtonDamasceno/SGD-API) |............................... :arrow_up: [Voltar ao topo](#analisador-léxico) :arrow_up: ...............................| [Próximo problema]() :arrow_right: | 
| :----: |-----| :-----:|   
