import "./Login.css";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    // Aqui depois tu vai validar o login com a API
    navigate("/home");
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h1>Bem-Vindo ao GradeUP</h1>

        <form onSubmit={handleLogin}>
          <label>Usuário</label>
          <input className="login-input" type="text" placeholder="Digite seu nome" required />

          <label>Senha</label>
          <input className="login-input"  type="password" placeholder="Digite sua senha" required />

          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
}
