import { useEffect, useState } from 'react';
import './Alerts.css';
import { useNavigate } from 'react-router-dom';

interface Warning {
  id: number;
  title: string;
  content: string;
  createdAt: string;
}

interface AlertsProps {
  classId?: number;
  maxVisible?: number;
}

export default function Alerts({ classId, maxVisible = 5 }: AlertsProps) {
  const [warnings, setWarnings] = useState<Warning[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    if (!classId) return;

    const token = localStorage.getItem('token');

    fetch(`http://localhost:8080/warnings/by-student?classId=${classId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    })
      .then(response => {
        if (!response.ok) throw new Error('Erro ao buscar avisos');
        return response.json();
      })
      .then((data: Warning[]) => {
        setWarnings(data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Erro ao buscar avisos:', error);
        setLoading(false);
      });
  }, [classId]);

  if (loading) {
    return (
      <div className="alerts">
        <p className="loading-text">Carregando avisos...</p>
      </div>
    );
  }

  if (warnings.length === 0) {
    return (
      <div className="alerts">
        <p className="no-alerts-text">Nenhum aviso disponível no momento.</p>
      </div>
    );
  }

  const visibleWarnings = warnings.slice(0, maxVisible);

  return (
    <div className="alerts">
      {visibleWarnings.map((warning) => (
        <div key={warning.id} className="alert">
          <span className="alert-title">{warning.title}</span>
          <p className="alert-content">{warning.content}</p>
        </div>
      ))}
      {warnings.length > maxVisible && (
        <button
          className="see-more-btn"
          onClick={() => navigate("/warnings")} // MUDAR QUANDO FAZER TELA DE AVISOS & implementar botao de dar um "ok" no aviso
        >
          Ver mais ({warnings.length - maxVisible} avisos)
        </button>
      )}
    </div>
  );
}