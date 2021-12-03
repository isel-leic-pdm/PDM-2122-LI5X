## Script da aula 
1. Identificar as mudanças feitas à base de código na preparação da aula
   1. Conjunto de utilitários para operações assíncronas (asyncUtils.kt)
   2. Modificação de HistoryActivityViewModel para usar os utilitários modificados
   3. Modificação de QuoteOfDayApplication para usar os utilitários modificados
   4. Modificação do layout de cada item do histórico (inclui agora uma drawable shape)
   5. Animação para sinalizar a selecção de um elemento da lista
2. Acrescentar suporte para apresentação da quote existente em histórico
   1. Criar history.QuoteActivity
   2. Acrescentar suporte para navegação entre a lista e a nova activity
3. Concluir implementação de suporte para armazenamento persistente do histórico
   1. Acrescentar timestamp à QuoteEntity (do tipo Date)
   2. Acrescentar TypeConverter para mapeamento entre Date e Long
4. Alterar o ecrã principal (MainActivity) para que:
   1. A quote seja obtida a partir da BD local, caso lá esteja
   2. Caso contrário é obtida a partir da API remota e armazenada na BD local
   3. Para o efeito criar common.QuoteOfDayRepository, que irá conter as operações assíncronas 
   para acesso às duas fontes de dados
5. Por fim, tornar a BD persistente
6. Se sobrar tempo, apresentar a API WorkManager
