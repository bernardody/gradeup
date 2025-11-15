import "./Login.css";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    // Aqui depois tu vai validar o login com a API
    navigate("/HomeStudent"); 
  };


  // e so pra eu ficar testando dps a gente remove ok
  const validacaoTESTE = (e: React.FormEvent) => {
  e.preventDefault(); 

  const tipoInput = document.getElementById("tipo") as HTMLInputElement;
  const tipo = tipoInput?.value;

  if (tipo === "aluno") {
    navigate("/HomeStudent");
  } else if (tipo === "professor") {
    navigate("/HomeTeacher");
  } else {
    alert("Tipo de usuário inválido. Use 'aluno' ou 'professor'.");
  };
  }

  return (
    <div className="login-page">
      <div className="login-box">
        <h1>Bem-Vindo ao GradeUP</h1>

        <form onSubmit={validacaoTESTE}> 
          <label>Usuário</label>
          <input id="tipo" className="login-input" type="text" placeholder="Digite seu nome" required />

          <label>Senha</label>
          <input className="login-input"  type="password" placeholder="Digite sua senha" required />

          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
}
