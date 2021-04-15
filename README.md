# Analisador léxico

## Pré-requisitos
São necessários os seguintes pré-requisitos para a execução deste projeto:
- Java 11 ou superior.
- Maven 3.6.3 ou superior.

## Instalação
Para executar o projeto através do terminal, digite o seguinte comando 
no diretorio raiz do projeto:

    mvn clean install

Em seguida acesse a pasta `target` e lá deverá conter um arquivo 
`lexical-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar` que corresponde 
ao executável do projeto. 

Para iniciar o jar basta executar o seguinte comando:

    java -jar lexical-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar
    
Leia a sessão [entrada](#entrada).

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

Esses arquivos devem estar obrigatóriamente em uma pasta
`input` que por sua vez deve estar no mesmo diretório
onde o `jar` foi executado.

## Saída

Será gerado um conjunto de arquivos de saída
(um para cada arquivo de entrada), denominados
saidaX.txt, onde X é um valor numérico, referente a
cada arquivo de entrada, com a resposta do analisador
léxico.

## Lista de siglas
_Tokens_ possuem tipos e esses tipos são representados pela seguinte tabela
de siglas:

| Sigla | Descrição | Regex | Teste aqui |
| :---: | --------- | --- | :---: |
| `PRE` | Palavra Reservada | Link ao lado | [:arrow_right:](https://regex101.com/r/pclNMd/1) |  
| `IDE` | Identificador | `[a-zA-Z](\w\|_)*` | [:arrow_right:](https://regex101.com/r/6xxPQu/1) |
| `NRO` | Número | `\d+(\.\d+)?` | [:arrow_right:](https://regex101.com/r/WctRjw/1) | 
| `DEL` | Delimitador | `[;,\(\)\{\}\[\]\.]` | [:arrow_right:](https://regex101.com/r/2HvsoT/1) |
| `REL` | Operador Relacional | `([=><]=?)\|!=` | [:arrow_right:](https://regex101.com/r/z0hRyx/1) |  
| `LOG` | Operador Lógico | `(&&\|\|\|\|!(?!=))` | [:arrow_right:](https://regex101.com/r/N87Oib/1) | 
| `ART` | Operador Aritmético | `(\*\|/)\|(\+\+?\|--?)` | [:arrow_right:](https://regex101.com/r/S15suW/1) | 
| `CAD` | Cadeia de Caracteres | `(\"(.*?(?<!\\))\")` |[:arrow_right:](https://regex101.com/r/wOKrE0/1) |

Os _tokens_ abaixo definem os possivéis erros léxicos que o analisador é capaz de 
encontrar durante a análise.

| Sigla | Descrição | Regex | Teste aqui |
| :---: | --------- | --- | :---: |
| `SIB` | Simbolo Inválido |  | - | 
| `CMF` | Cadeia Mal Formada | | - |
| `NMF` | Número Mal Formado | `\d+\.(?=[^\d])` | [:arrow_right:](https://regex101.com/r/Vl3PUd/1) |
| `CoMF` | Comentário Mal Formado | | - |
| `OpMF` | Operador Mal Formado | `(?<!(&\|\|))[&\|](?!(&\|\|))` | [:arrow_right:](https://regex101.com/r/dPfLux/1) |


----------

| :arrow_left: [Problema anterior](https://github.com/UellingtonDamasceno/SGD-API) |............................... :arrow_up: [Voltar ao topo](#analisador-léxico) :arrow_up: ...............................| [Próximo problema]() :arrow_right: | 
| :----: |-----| :-----:|
