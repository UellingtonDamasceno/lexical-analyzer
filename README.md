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
| Indentificadores | letra ( letra \| dígito \| _ )* |
| Números | Dígito+(.Dígito+)? |
| Dígito | [0-9] |
| Operadores aritiméticos | `+` `-` `*` `/` `++`  `--` |
| Operadores relacionais | `==` `!=` `>` `>=` `<` `<=` `=` |
| Operadores lógicos | `&&` `||` `!` |
| Delimitadores de comentário | `\\` Isto é um comentário de linha `/*` Isto é um comentário de bloco `*/` |
| Cadeia de caracteres | "( letra \| dígito \| símbolo \| " )* " |
| Símbolo | ASCII de 32 a 126 (exceto ASCII 34) |

## Entrada

## Saída
