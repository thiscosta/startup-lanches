# startup-lanches
<br>
Para a execução do projeto, é necessário que o ambiente tenha as seguintes ferramentas instaladas:<br/><br/>

<ul>
    <li>Git</li>
    <li>Java</li>
    <li>Node</li>
    <li>NPM</li>
</ul>

<br><br>
<b>Siga os seguintes passos para executar a aplicação:</b><br /><br/>

<ol>
    <li>
        Abra o terminal/prompt e execute:<br />
        <code>git clone https://github.com/thiscosta/startup-lanches.git</code><br /><br />
    </li>
    <li>
        Após isso, execute a aplicação Java com o seguinte comando:<br />
        <code>java -jar startup-lanches/startup-lanches-api/target/startuplanches-0.0.1-SNAPSHOT.jar</code><br /><br/>
        Caso a porta 8080 esteja em uso, abra um prompt de comando como administrador e execute o comando<br />

<code>netstat -a -n -o | findstr 8080</code><br /><br />

Após isso, será exibido o processo que está utilizando a porta 8080, e na 5ª coluna o seu PID  (geralmente composto de 4 numeros).<br /><br />

Execute a seguinte instrução, trocando "pid" pelo PID do processo que foi listado:

<code>Taskkill /PID "pid" /F</code><br /><br>

Após o processo ser finalizado, execute novamente o passo 2.
    </li>
    <li>Entre no diretório do front-end
        <code>cd startup-lanches/startup-lanches-ui</code>       
    </li>
    <li>Baixe as dependências utilizando
    <code>npm install</code></li>
    <li>Após as dependências serem instaladas, execute o comando abaixo para abrir a aplicação front-end em seu navegador:
    <code>npm start</code></li>
</ol>

<br/><br />
<b>Obs: O servidor estará disponível na porta 8080.</b>
