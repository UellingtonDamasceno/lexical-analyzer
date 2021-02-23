# Analisador léxico

## Sumário
- [Descrição](#descrição)
- [Estrutura Léxica](#estrutura-léxica)
- [Entrada](#entrada)
- [Saída](#saída)

## Descrição

## Estrutura Léxica

| Descrição | Token |
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
