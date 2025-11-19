import "./Login.css";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState(""); 

  const handleLogin = async (e: React.FormEvent) => {
  e.preventDefault();

  try {
    const response = await fetch("http://localhost:8080/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email,
        password,
      }),
    });

    if (!response.ok) {
      alert("Erro no login");
      return;
    }

    const data = await response.json();
    
    if (data.accessToken) {
      localStorage.removeItem('token');
      localStorage.setItem('token', data.accessToken);
    }

    if (data.type === "STUDENT") {
      navigate("/HomeStudent", { state: data });
    } else if (data.type === "TEACHER") {
      navigate("/HomeTeacher", { state: data });
    } else if (data.type === "ADMIN") {
      navigate("/HomeAdmin", { state: data });
    }

  } catch (error) {
    console.error("ERRO CATCH:", error);
    alert("Erro no servidor.");
  }
};

  return (
    <div className="login-page">
      <div className="login-box">
        <h1>Bem-Vindo ao GradeUP</h1>

        <form onSubmit={handleLogin}> 
          <label>Email</label>
          <input 
            className="login-input" 
            type="email" 
            placeholder="Digite seu email" 
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required 
          />

          <label>Senha</label>
          <input 
            className="login-input"  
            type="password" 
            placeholder="Digite sua senha" 
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required 
          />

          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
}
