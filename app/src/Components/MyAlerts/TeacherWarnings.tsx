import React, { useState, useEffect } from 'react';
import './TeacherWarnings.css';

interface Teacher {
  id: number;
  name: string;
  email: string;
  type: string;
}

interface Subject {
  id: number;
  name: string;
}

interface Trimester {
  id: number;
  name: string;
  maxPoints: number;
  minPoints: number;
}

interface ClassEntity {
  id: number;
  name: string;
  year: number;
}

interface ExamEntity {
  id: number;
  classEntity: ClassEntity;
  subject: Subject;
  teacher: Teacher;
  trimester: Trimester;
  name: string;
  maxScore: number;
  examDate: string;
}

interface Warning {
  id: number;
  examEntity: ExamEntity;
  title: string;
  content: string;
  createdAt: string;
}

interface TeacherWarningsProps {
  classId?: number;
  maxWarnings?: number;
  showViewAllButton?: boolean; 
}

const TeacherWarnings: React.FC<TeacherWarningsProps> = ({ 
  classId, 
  maxWarnings = 3, // Valor padrão: 3 avisos
  showViewAllButton = false // Valor padrão: não mostrar botão
}) => {
  const [warnings, setWarnings] = useState<Warning[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showAll, setShowAll] = useState<boolean>(false); // Estado para controlar se mostra todos

  useEffect(() => {
    if (classId) {
      fetchWarnings();
    }
  }, [classId]);

  const fetchWarnings = async () => {
    try {
      setLoading(true);
      const token = localStorage.getItem('token');
      
      const response = await fetch(
        `http://localhost:8080/warnings/by-student?classId=${classId}`,
        {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }
      );

      if (!response.ok) {
        throw new Error('Erro ao carregar avisos');
      }

      const data: Warning[] = await response.json();
      // Ordenar por data (mais recentes primeiro)
      const sortedData = data.sort((a, b) => 
        new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
      );
      setWarnings(sortedData);
      setLoading(false);
    } catch (err) {
      setError('Erro ao carregar os avisos');
      setLoading(false);
      console.error(err);
    }
  };

  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  };

  const formatDateTime = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  // Determinar quais avisos mostrar com base no estado
  const warningsToShow = showAll ? warnings : warnings.slice(0, maxWarnings);
  const hasMoreWarnings = warnings.length > maxWarnings;

  if (loading) {
    return (
      <div className="warnings-container">
        <div className="warnings-loading">
          <div className="spinner"></div>
          <p>Carregando avisos...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="warnings-container">
        <div className="warnings-error">
          <p>❌ {error}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="warnings-container">
      <h2 className="warnings-title">📢 Avisos dos Professores</h2>
      
      {warnings.length === 0 ? (
        <div className="warnings-empty">
          <p>Nenhum aviso no momento</p>
        </div>
      ) : (
        <>
          <div className="warnings-list">
            {warningsToShow.map((warning) => (
              <div key={warning.id} className="warning-card">
                <div className="warning-header">
                  <h3 className="warning-card-title">{warning.title}</h3>
                  <span className="warning-subject">{warning.examEntity.subject.name}</span>
                </div>
                
                <div className="warning-content">
                  <p>{warning.content}</p>
                </div>

                <div className="warning-details">
                  <div className="warning-detail-item">
                    <span className="detail-label">Professor:</span>
                    <span className="detail-value">{warning.examEntity.teacher.name}</span>
                  </div>
                  
                  <div className="warning-detail-item">
                    <span className="detail-label">Prova:</span>
                    <span className="detail-value">{warning.examEntity.name}</span>
                  </div>
                  
                  <div className="warning-detail-item">
                    <span className="detail-label">Data da Prova:</span>
                    <span className="detail-value">{formatDate(warning.examEntity.examDate)}</span>
                  </div>
                  
                  <div className="warning-detail-item">
                    <span className="detail-label">Pontuação:</span>
                    <span className="detail-value">{warning.examEntity.maxScore} pontos</span>
                  </div>
                </div>

                <div className="warning-footer">
                  <span className="warning-trimester">{warning.examEntity.trimester.name}</span>
                  <span className="warning-date">Publicado: {formatDateTime(warning.createdAt)}</span>
                </div>
              </div>
            ))}
          </div>
          

          {/* Botão para mostrar mais/menos avisos */}
          {showViewAllButton && hasMoreWarnings && (
            <div className="warnings-controls">
              <button 
                className="view-all-button"
                onClick={() => setShowAll(!showAll)}
              >
                {showAll ? 'Mostrar menos' : `Ver todos (${warnings.length})`}
              </button>
              
              {!showAll && warnings.length > 0 && (
                <div className="warnings-counter">
                  <small>Mostrando {Math.min(maxWarnings, warnings.length)} de {warnings.length} avisos</small>
                </div>
              )}
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default TeacherWarnings;