ArtWalk\

ArtWalk é um projeto desenvolvido como parte de uma disciplina universitária e tem como objetivo promover exposições culturais por meio de uma aplicação mobile. O aplicativo permite que os usuários explorem exposições e obras de arte, com funcionalidades como leitura de QR Codes e acompanhamento do progresso visual das exposições.\

📱 Aplicação Mobile\

Tecnologias utilizadas:\

Android Studio
Firebase: para gerenciamento do banco de dados, autenticação e sincronização em tempo real.
ZXing: integração para leitura de QR Codes.\

Funcionalidades:\

Listagem de exposições.
Visualização de detalhes das obras via QR Code.
Registro de progresso na visualização das exposições.
Indicação de conclusão da exposição com uma estrela, ao visualizar todas as obras.\

Estrutura do Banco de Dados no Firebase:\

Coleção usuarios: contém os dados dos usuários. As obras visualizadas estão armazenadas em /usuarios/{idUsuario}/Obra.
Coleção Obra: armazena os dados de todas as obras disponíveis no sistema.
Coleção Exposições: armazena os dados de todas as exposições disponíveis no sistema.\

👨‍💻 Desenvolvimento\

Este projeto foi desenvolvido por Julia Lino e Alan Regis como parte de um projeto extensionista, sem custos financeiros envolvidos.\

Como executar a aplicação:
Clone o repositório.
Abra o projeto no Android Studio.
Configure o arquivo google-services.json com as credenciais do seu Firebase.
Execute o app no emulador ou dispositivo físico.
