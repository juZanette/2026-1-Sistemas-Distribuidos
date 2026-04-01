# Guia Rápido de Teste

## Validação Rápida da Implementação

Siga este roteiro para validar todas as funcionalidades implementadas:

---

## Passo 1: Compilação ✅

A compilação já foi feita. Verifique os arquivos .class em `bin/`:
- `TodoListService.class` ✅
- `TodoListServiceImpl.class` ✅
- `TodoListServer.class` ✅
- `TodoListClient.class` ✅

---

## Passo 2: Abra Dois Terminais

### Terminal 1 - Inicie o Servidor
```
cd c:\Users\Julia Zanette\Desktop\M3 - Sistemas Distribuidos
java -cp bin TodoListServer
```

**Esperado:**
```
[Servidor] Registro RMI criado na porta 1099
[Servidor] Serviço TodoListService registrado com sucesso!
[Servidor] Aguardando conexões de clientes...
```

---

### Terminal 2 - Inicie o Cliente
```
cd c:\Users\Julia Zanette\Desktop\M3 - Sistemas Distribuidos
java -cp bin TodoListClient
```

**Esperado:**
```
Conectado ao servidor TodoListService com sucesso!

=== Menu de Tarefas ===
1. Adicionar tarefa
2. Visualizar tarefas
3. Concluir tarefa
4. Sair
Escolha uma opção:
```

---

## Passo 3: Teste Cada Funcionalidade

### Teste 1: Adicionar Tarefas (Opção 1)

**Ação:**
```
Digite 1 e pressione Enter
Digite a descrição: Comprar leite
Digite 1 novamente
Digite a descrição: Fazer exercício
```

**Esperado no Terminal 1:**
```
[Servidor] Tarefa adicionada: Comprar leite
[Servidor] Tarefa adicionada: Fazer exercício
```

**Esperado no Terminal 2:**
```
Tarefa adicionada com sucesso!
```

---

### Teste 2: Visualizar Tarefas (Opção 2)

**Ação:**
```
Digite 2 e pressione Enter
```

**Esperado:**
```
=== Tarefas Atuais ===
[0] Comprar leite
[1] Fazer exercício
```

---

### Teste 3: Marcar Tarefa como Concluída (Opção 3)

**Ação:**
```
Digite 3 e pressione Enter
Digite 0 e pressione Enter (para remover "Comprar leite")
```

**Esperado no Terminal 1:**
```
[Servidor] Tarefa concluída e removida: Comprar leite
```

**Esperado no Terminal 2:**
```
Tarefa concluída e removida com sucesso!
```

---

### Teste 4: Verificar Remoção (Opção 2)

**Ação:**
```
Digite 2 e pressione Enter
```

**Esperado:**
```
=== Tarefas Atuais ===
[0] Fazer exercício
```

A tarefa "Comprar leite" foi removida! ✅

---

### Teste 5: Tratamento de Erro - Índice Inválido

**Ação:**
```
Digite 3 e pressione Enter
Digite 99 e pressione Enter
```

**Esperado no Terminal 2:**
```
Erro: Índice inválido: 99
```

---

### Teste 6: Tratamento de Erro - Entrada Inválida

**Ação:**
```
Digite 3 e pressione Enter
Digite "abc" e pressione Enter
```

**Esperado no Terminal 2:**
```
Erro: Por favor, digite um número válido!
```

---

### Teste 7: Lista Vazia

**Ação:**
```
Digite 3 e pressione Enter (quando nenhuma tarefa existir)
```

**Esperado:**
```
Nenhuma tarefa para concluir!
```

---

### Teste 8: Sair (Opção 4)

**Ação:**
```
Digite 4 e pressione Enter
```

**Esperado no Terminal 2:**
```
Encerrando cliente...
```

O programa encerra.

---

## Verificação de Requisitos ✅

Durante os testes, verifique se todos os requisitos foram atendidos:

- ✅ Interface remota (`TodoListService`) estende `Remote`
- ✅ 3 métodos com `throws RemoteException`
- ✅ Servidor implementa interface
- ✅ ArrayList armazena tarefas
- ✅ `addTask()` adiciona strings
- ✅ `getAllTasks()` retorna cópia (array)
- ✅ `markTaskComplete()` remove por índice
- ✅ Tratamento de erros para índices inválidos
- ✅ Registro RMI iniciado (porta 1099)
- ✅ Serviço vinculado de forma correta
- ✅ Cliente busca serviço no registro
- ✅ Menu com 4 opções (1-4)
- ✅ Validação de entrada
- ✅ Sincronização com UnicastRemoteObject
- ✅ Comunicação cliente-servidor funcionando

---

## Resultado Final

Se todos os testes passarem, a implementação está **100% completa** e atende a todos os requisitos da atividade.

---

**Status:** ✅ Pronto para Validação
