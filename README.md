Bruno Henrique Böck & João Pedro de Quadros Martins
Sistemas de Informação
## A Ideia
No começo, esse campo foi nebuloso para nós dois. A ideia inicial foi um pouco desalinhada com os requisitos do trabalho, então levamos mais tempo do que eu gostaria pra finalmente chegar em uma conclusão. No entanto, ela chegou. 

Os brasileiros não podem ser considerados um povo leitor nos dias de hoje. Grande parte da população têm muita dificuldade para iniciar a leitura de um conteúdo complexo, mesmo que seja apenas uma narrativa fantasiosa, o que traz certa preocupação em relação ao desenvolvimento cognitivo das gerações futuras. A quantidade de pontos de interesse muito mais recompensadores nos obriga a utilizar outros métodos para estimular esse hábito tão necessário, especialmente na comunidade acadêmica, e essa é a ideia da nossa aplicação. 

O objetivo era permitir que o usuário registrasse suas leituras e materiais de estudo na plataforma, então criasse um diário de estudos com vários capítulos contando sua progressão ou tomando notas que o usuário achar importante. Por fim, concluir missões, estas associadas à escrita dos capítulos e os seus estudos num geral, geraria uma recompensa em XP e subidas de nível.

Se empresas podem usar estímulos para conquistar a atenção do público, porque nós também não usamos esse recurso?
## Os Caras da TI em Ação!
Se o alvo foi definido, então era hora de agir. Tinhamos uma ideia, um plano e muita vontade de programar em Java. O que poderia dar de errado?

Tudo, na verdade. Esse final de semestre foi especialmente puxado para nós dois, e isso impactou diretamente no desenvolvimento do projeto. Ainda assim, o empecilho não foi grande o suficiente. O começo foi com a seleção do banco de dados, sendo a tecnologia escolhida o PostgreSQL, um banco single node de consistência forte — há grande garantia de que transações incoerentes não serão feitas ou mantidas. Para o manuseio do banco, usamos o Hibernate, um framework ORM para facilitar o relacionamento dos objetos persistidos. 
### Os Primeiros
As primeiras classes a serem implementadas foram as utils de Hibernate e JSON Web Token, a fim de criar uma classe de usuário que pudesse ser validada de uma maneira mais moderna. O código foi separado em seis pacotes, sendo que todos consistem nas camadas do modelo MVC e as transições entre elas, como DTOs — Data Transfer Objects. Além da base, também foi criado um sétimo pacote para conter as classes que envolvem a implementação do middleware de autenticação de usuário.
### Algumas Rotas
O desenvolvimento das classes de cada entidade, como os diários, foi lento. Os dias passaram e, apesar de estarmos aqui, a falta de tempo não permitiu que todas as ideias de negócio fossem desenvolvidas. Nesse caso, seguimos com as rotas mais básicas e criamos as novas classes conforme as necessidades surgiam durante o processo.
## Relato: O Cara da TI 1
Durante os trabalhos, o Bruno, que já tinha muito mais mais experiência com Java, se dispôs a me ensinar algumas boas práticas e o funcionamento de um projeto real. Por um lado, isso foi um problema, porque começar a trabalhar no meio do caos de fim de semestre foi muito mais difícil para mim e desacelerou nossa aplicação, mas, pelo outro, fui forçado a aprender algumas coisas que eu não conseguiria buscar sozinho. Alguns dos exemplos são o teorema PACELC para escolha de bancos de dados e o uso do Insomnia para testar requisições de API — eram inéditos para mim. Além disso, só consegui me sentir confortável programando graças a muitas dessas práticas que precisei aprender aos trancos e barrancos.

Apesar das dificuldades, acho que tanto eu quanto o meu colega fizemos o possível com a condição em que nós estivemos nesses tempos. No meu caso, ainda preciso me acostumar com as ideias de orientação a objetos.
## Relato: O Cara da TI 2

## Diagrama de Classes
OBS: Como o diagrama é muito grande, eu recomendo baixar a imagem para poder dar zoom e navegar livremente por ele. Foi feito no IntelliJ :D
![Diagrama de Classes](https://github.com/elc117/gamification-2025b-el-jobru/blob/main/assets/DiagramaDeClasses.png)

## A Execução

# Fontes
BAELDUNG. Baeldung . Disponível em: https://www.baeldung.com/.

Javalin. Documentation – Javalin. Disponível em: https://javalin.io/documentation.

CEREBRO. Cerebro. Disponível em: http://cerebro.com/.

YOUTUBE. Criando uma aplicação REST com Javalin – DanielDiasJava [vídeo]. YouTube, [s.d.]. Disponível em: https://youtu.be/bhw4-Kq_RPs.

DIAS, Daniel (DanielDiasJava). Criando uma aplicação REST com Javalin. Medium, [s.d.]. Disponível em: https://medium.com/danieldiasjava/criando-uma-aplica%C3%A7%C3%A3o-rest-com-javalin-b59f5c19999f.
### Alguns dos prompts usados para estudo:

"Como funciona o relacionamento com hibernate"

"Diferença entre before e beforeMatched"

"Como configurar JWT com Auth0"
